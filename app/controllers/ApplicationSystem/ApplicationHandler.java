package controllers.ApplicationSystem;

import com.typesafe.config.Config;
import controllers.UserSystem.Secured;
import dao.ApplicationSystem.EntityComponent;
import dao.ApplicationSystem.EntityComponentVersion;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.NMU.EntityDepartment;
import dao.UserSystem.EntityPerson;
import engine.RECEngine;
import helpers.CookieTags;
import helpers.JDBCExecutor;
import models.App;
import models.ApplicationSystem.ApplicationStatus;
import models.ApplicationSystem.EthicsApplication;
import models.ApplicationSystem.EthicsApplication.ApplicationType;

import models.GuiButton;
import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.mvc.Security;
import play.routing.JavaScriptReverseRouter;

@Security.Authenticated(Secured.class)
public class ApplicationHandler extends Controller {

    @Inject
    private FormFactory formFactory;

    public ApplicationHandler(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    @Inject
    private JDBCExecutor jdbcExecutor;

    public ApplicationHandler(JDBCExecutor jdbcExecutor) {
        this.jdbcExecutor = jdbcExecutor;
    }

    @Inject
    private HttpExecutionContext httpExecutionContext;

    public ApplicationHandler(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    private Config config = null;

    @Inject
    public ApplicationHandler(Config config) {
        this.config = config;
    }

    // Uses XML document to create form, based in as Scala Squence type and processed in view
    public ApplicationHandler() {

    }

    /**
     * Allows to edit and application, by loading all data of the latest application.
     * <p>
     * Should only be avialble if the is submitted property is true or {@link RECEngine} allows this.
     *
     * @return result
     */
    @AddCSRFToken
    public Result editApplication(String applicationID) {
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(applicationID);
        EntityEthicsApplication ethicsApplication = EntityEthicsApplication.find.byId(entityEthicsApplicationPK);
        if (ethicsApplication == null) {
            return badRequest();
        }

        Element element = EthicsApplication.PopulateRootElement(ethicsApplication);
        Map<String, Boolean> editableMap = new HashMap<>();
        XMLTools.flatten(element).forEach(s -> editableMap.put(s, true));

        Map<String, List<String>> latestComponentFeedback = EntityEthicsApplication.getLatestComponentFeedback(entityEthicsApplicationPK);

        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Edit Application", ethicsApplication.type(), element, editableMap, false, entityEthicsApplicationPK.shortName(), false, false, latestComponentFeedback, GuiButton.negHomeCancel, GuiButton.posSubmitApplication, GuiButton.netSaveApplication));
    }

    /**
     * Provides the latest application version.
     * Dependant of {@link RECEngine}, the form will be altered by showing either
     * <ul>
     * <li>The next step: Request PRP approval / Submit Application</li>
     * <li>Latest feedback and allowing edits (if an unsubmitted reviewable) dependant on {@link RECEngine}.</li>
     * <li>Latest application as readonly (if accepted)</li>
     * </ul>
     *
     * @param applicationID application short primary key
     * @return result
     */
    @AddCSRFToken
    public Result reviewApplication(String applicationID) {
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(applicationID);
        EntityEthicsApplication ethicsApplication = EntityEthicsApplication.find.byId(entityEthicsApplicationPK);
        if (ethicsApplication == null)
            return badRequest();

        ApplicationStatus status = ApplicationStatus.parse(ethicsApplication.getInternalStatus());
        if (status.equals(ApplicationStatus.DRAFT) ||
                status.equals(ApplicationStatus.NOT_SUBMITTED) &&
                        session().get(CookieTags.user_id).equals(ethicsApplication.getPiId())) {
            return editApplication(applicationID);
        } else {
            Element element = EthicsApplication.PopulateRootElement(ethicsApplication);
            Map<String, Boolean> editableMap = new HashMap<>();
            XMLTools.flatten(element).forEach(s -> editableMap.put(s, false));
            Map<String, List<String>> latestComponentFeedback = EntityEthicsApplication.getLatestComponentFeedback(entityEthicsApplicationPK);
            return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Review Application", ethicsApplication.type(), element, editableMap, false, entityEthicsApplicationPK.shortName(), false, true, latestComponentFeedback, GuiButton.negHomeCancel, GuiButton.posSubmitApplication, GuiButton.netSaveApplication));
        }
    }

    /**
     * Submit revised application and component data only if {@link RECEngine} allows it
     *
     * @return result
     */
    @RequireCSRFCheck
    public Result submitRevision() {
        return TODO;
    }

    //TODO complete all applications

    /**
     * Gets all applications associated with the user in a view
     *
     * @return result
     */
    @AddCSRFToken
    public Result allApplications() {
        try {
            EntityPerson person = EntityPerson.getPersonById(session().get(CookieTags.user_id));
            List<EntityEthicsApplication> applicationsByPerson = EntityEthicsApplication.findSubmittedApplicationsByPerson(person.getUserEmail());
            return ok(views.html.ApplicationSystem.AllApplications.render(" :: Applications", applicationsByPerson));
        } catch (Exception x) {
            x.printStackTrace();
            return badRequest(views.html.ApplicationSystem.AllApplications.render(" :: Applications", new ArrayList<>()));
        }
    }

    /**
     * Provides a form to complete a new application depending on type
     *
     * @param type application type e.g. human
     * @return result
     */
    @AddCSRFToken
    public Result newApplication(String type) {
        ApplicationType applicationType = ApplicationType.parse(type);
        EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(applicationType);
        if (ethicsApplication == null) {
            flash("info", "No animal application form available");
            return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
        }
        Element rootElement = ethicsApplication.getRootElement();
        Map<String, Boolean> editableMap = new HashMap<>();
        XMLTools.flatten(rootElement).forEach(s -> editableMap.put(s, true));
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", applicationType, rootElement, editableMap, true, "", false, false, new HashMap<>(), GuiButton.negHomeCancel, GuiButton.posSubmitApplication, GuiButton.netSaveApplication));
    }

    @RequireCSRFCheck
    public Result submitEdit() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String id = form.get("application_id");
        EntityEthicsApplicationPK applicationPK = EntityEthicsApplicationPK.fromString(id);
        //update all components
        fill(form, applicationPK);
        RECEngine.getInstance().nextStep(applicationPK);
        return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
    }

    @RequireCSRFCheck
    public Result forceSubmitApplication() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String id = form.get("application_id");
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(id);

        RECEngine.getInstance().nextStep(entityEthicsApplicationPK);
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(entityEthicsApplicationPK);
        if (application == null) {
            return badRequest();
        }

        if (ApplicationStatus.parse(application.getInternalStatus()) != ApplicationStatus.AWAITING_PRP_APPROVAL) {
            RECEngine.getInstance().nextStep(entityEthicsApplicationPK);
        }

        flash("success", "Application submitted");
        return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
    }

    /**
     * Creates a NEW application in the server database
     * <p>
     * If Application for is complete (determined by engine, the PI should be notified of this application on the overview screen and once per week
     */
    @RequireCSRFCheck
    //TODO implement draft save and RECEngine association
    public CompletionStage<Result> createApplication() {
        return CompletableFuture.supplyAsync(() -> {
            DynamicForm formApplication = formFactory.form().bindFromRequest();

            // Get application type as raw field
            String type = formApplication.get("application_type");
            ApplicationType application_type = ApplicationType.parse(type);

            // Handle form errors
            if (formApplication.hasErrors()) {
                EthicsApplication application_template = EthicsApplication.lookupApplication(application_type);
                Map<String, Boolean> editableMap = new HashMap<>();
                XMLTools.flatten(application_template.getRootElement()).forEach(s -> editableMap.put(s, true));
                return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", application_type, application_template.getRootElement(), editableMap, false, "", false, false, new HashMap<>(), GuiButton.negHomeCancel, GuiButton.posSubmitApplication, GuiButton.netSaveApplication));
            }

            EntityEthicsApplication application = null;
//
//            if (formApplication.get("prp_contact_email") == null || formApplication.get("prp_contact_email").toString().isEmpty()) {
//                // Handle form errors
//                EthicsApplication application_template = EthicsApplication.lookupApplication(application_type);
//                Map<String, Boolean> editableMap = new HashMap<>();
//                XMLTools.flatten(application_template.getRootElement()).forEach(s -> editableMap.put(s, true));
//                return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", application_type.toString(), application_template.getRootElement(), ApplicationStatus.DRAFT, application_template.getQuestionList(), editableMap, routes.ApplicationHandler.submitEdit(), false, "", false, false));
//            }

            // Create basic Ethics Application
            if (formApplication.get("application_id") == null ||
                    formApplication.get("application_id").toString().isEmpty()) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                application = new EntityEthicsApplication();
                application.setApplicationType(application_type.name().toLowerCase());
                application.setApplicationYear(year);
                application.setFacultyName(formApplication.get("app_faculty"));
                application.setDepartmentName(formApplication.get("app_department"));
                EntityDepartment app_department = EntityDepartment.findDepartmentByName(formApplication.get("app_department"));
                int applicationNumber = EntityEthicsApplication.GetNextApplicationNumber(app_department, application_type, year);
                application.setApplicationNumber(applicationNumber);

                // Get pi id from session
                String pi_id = session(CookieTags.user_id);
                // Set hod, rti, prp & pi ids
                application.setPiId(pi_id);

                // Get prp id from Elements
                String prpContactEmail = formApplication.get("prp_contact_email");
                if (prpContactEmail != null) {
                    prpContactEmail = prpContactEmail.split("\\[")[1]
                            .replace("]", "");
                    application.setPrpId(prpContactEmail);

                }

                String hodId = EntityPerson.getHod(application.getDepartmentName());
                if (hodId != null) {
                    application.setHodId(hodId);
                }
                String facultyRTI = EntityPerson.getRTI(application.getFacultyName());
                if (facultyRTI != null) {
                    application.setRtiId(facultyRTI);
                }

                application.insert();
            } else {
                application = EntityEthicsApplication.GetApplication(EntityEthicsApplicationPK.fromString(formApplication.get("application_id").toString()));
            }


            boolean filledApplication = fill(formApplication, application.applicationPrimaryKey());
            if (!filledApplication) {
                EthicsApplication application_template = EthicsApplication.lookupApplication(application_type);
                Map<String, Boolean> editableMap = new HashMap<>();
                XMLTools.flatten(application_template.getRootElement()).forEach(s -> editableMap.put(s, true));
                return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", application_type, application_template.getRootElement(), editableMap, false, application.applicationPrimaryKey().shortName(), false, false, new HashMap<>(), GuiButton.negHomeCancel, GuiButton.posSubmitApplication, GuiButton.netSaveApplication));
            }

            RECEngine.getInstance().nextStep(application.applicationPrimaryKey());

            flash("success", "Your application has been saved");
            return redirect(routes.ApplicationHandler.allApplications());
        }, httpExecutionContext.current());
    }

    public boolean fill(DynamicForm formApplication, EntityEthicsApplicationPK applicationId) {
        Map<String, Object> filteredData = new HashMap<>();
        formApplication.get().getData().entrySet()
                .forEach(e -> filteredData.put(e.getKey(), e.getValue()));
        request().body().asMultipartFormData().getFiles().stream()
                .filter(o -> !o.getFilename().isEmpty())
                // containers FilePart which has file name, and key and actual file
                .forEach(e -> filteredData.put(e.getKey(), e));

        return fillInApplicationData(applicationId, filteredData);
    }

    private boolean fillInApplicationData(EntityEthicsApplicationPK applicationId, Map<String, Object> data) {
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(applicationId);

        Element rootElement = EthicsApplication.lookupApplication(application.type()).getRootElement();

        if (application.getInternalStatus() == null ||
                application.getInternalStatus() != ApplicationStatus.DRAFT.getStatus() &&
                        application.getInternalStatus() != ApplicationStatus.NOT_SUBMITTED.getStatus()) {
            application.setInternalStatus(ApplicationStatus.DRAFT.getStatus());
        }

        // Update application
        application.update();

        // Get form data and add to root element
        data.entrySet().stream()
                .filter(stringObjectEntry -> !stringObjectEntry.getValue().toString().isEmpty())
                .forEach(entry -> {
                    Element componentElement = XMLTools.lookup(rootElement, entry.getKey());
                    if (componentElement != null) {
                        EntityComponent component = getUpdatableComponent(entry, componentElement, applicationId);

                        String type = componentElement.getType();
                        System.out.println("Type: " + type);
                        EntityComponentVersion entityComponentVersion = getUpdatableComponentVersion(component, type);
                        // be able to compile a document with description, etc. possibly update each document but split string id  base on "_title _desc and _document" and update documnt component 3 times

                        if (entityComponentVersion != null) {
                            // insert component version value
                            setComponentValue(entityComponentVersion, entry, type);
//                            data.remove(entry.getKey());
                        }

                        // if == null, means that the the component was submitted and is not meant to be changed, but it is being changed. This change will not be saved.
                    }
                });
        return true;
    }

    private void setComponentValue(EntityComponentVersion entityComponentVersion, Map.Entry<String, Object> entry, String responseType) {
        switch (responseType) {

            case "number": {
                entityComponentVersion.setTextValue(entry.getValue().toString());
                break;
            }

            case "date": {
                String time = entry.getValue().toString() + " " + Timestamp.from(Instant.now()).toString().split(" ")[1];
                Timestamp ts = Timestamp.valueOf(time);
                entityComponentVersion.setTextValue(ts.toString());
                break;
            }

            case "long_text":
            case "text": {
                if (entry.getKey().contains("doc_")) {
                    if (entry.getKey().contains("_title")) {
                        entityComponentVersion.setDocumentName((String) entry.getValue());
                    } else {
                        entityComponentVersion.setDocumentDescription((String) entry.getValue());
                    }
                } else {
                    entityComponentVersion.setTextValue((String) entry.getValue());
                }
                break;
            }

            case "boolean": {
                boolean b = Boolean.parseBoolean((String) entry.getValue());
                entityComponentVersion.setBoolValue(b);
                break;
            }

            case "document": {
                // Check saving directory exists
                String docDirectory = App.getInstance().getDocumentDirectory();
                try {
                    File dir = new File(docDirectory);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }

                // Save file
                try {
                    FilePart filePart = (FilePart) entry.getValue();
                    File file = (File) filePart.getFile();
                    File newFile = new File(docDirectory.concat(entityComponentVersion.applicationPrimaryKey().shortName()).concat("~" + entityComponentVersion.getVersion() + "~").concat(filePart.getFilename()));
                    file.renameTo(newFile);
                    entityComponentVersion.setDocumentLocationHash(newFile.getPath());
                } catch (Exception x) {
                    x.printStackTrace();
                }
                break;
            }

            default: {
                break;
            }
        }
        entityComponentVersion.setDateLastEdited(Timestamp.from(Instant.now()));
        entityComponentVersion.save();

    }

    /**
     * Gest the EntityComponent for a specific application. Creates the component if needed and returns the instance to be used by the component version.
     *
     * @param entry            object entry from result
     * @param componentElement related component element
     * @param applicationId    applcation primary key
     * @return EntityComponent to be updated
     */
    private EntityComponent getUpdatableComponent(Map.Entry<String, Object> entry, Element componentElement, EntityEthicsApplicationPK applicationId) {
        String componentName = entry.getKey();
        if (componentName.contains("doc_")) {
            if (componentName.contains("_title")) {
                componentName = componentName.replace("_title", "");
            } else if (componentName.contains("_desc")) {
                componentName = componentName.replace("_desc", "");
            } else {
                componentName = componentName.replace("_document", "");
            }
        }

        String finalComponentName = componentName;
        System.out.print("Searching for [ " + finalComponentName + " ]...");
        EntityComponent component = EntityComponent.GetAllApplicationCompontents(applicationId).stream().filter(entityComponent -> entityComponent.getComponentId().equals(finalComponentName)).findAny().orElse(null);
        if (component == null) {
            System.out.print("not Found!\nCreating [ " + finalComponentName + " ] as " + componentElement.getType());
            component = new EntityComponent();
            component.setApplicationId(applicationId);
            component.setComponentId(finalComponentName);

            Element question = componentElement.getChildren().stream()
                    .filter(element -> element.getTag().toLowerCase().equals("question"))
                    .findFirst()
                    .orElse(null);
            if (question != null) {
                component.setQuestion(question.getValue().toString());
            } else {
                System.out.println("Application: " + applicationId.shortName() + "\nCould not find question value for content:\n\"" + entry.getValue().toString() + "\"");
            }
            component.insert();
            // create new component version
        } else {
            System.out.print("Found!");
        }
        return component;
    }

    private EntityComponentVersion getUpdatableComponentVersion(EntityComponent component, String responseType) {
        EntityComponentVersion entityComponentVersion = EntityComponentVersion.GetLatestComponent(component.componentPrimaryKey());

        //check if component version exists
        short previousVersion = (entityComponentVersion == null) ? -1 : (entityComponentVersion.getVersion());

        if (entityComponentVersion != null) {
            //  if it exists, check if it has been submitted

            if (entityComponentVersion.getIsSubmitted()) {
                if (entityComponentVersion.wasFeedbackGiven()) {
                    // if component version has been submitted, create new component
                    entityComponentVersion = new EntityComponentVersion();
                    entityComponentVersion.setComponentPrimaryKey(component.componentPrimaryKey());
                    entityComponentVersion.setVersion(++previousVersion);
                    entityComponentVersion.setResponseType(responseType);
                    entityComponentVersion.setIsSubmitted(false);
                } else {
                    // Means no feedback given i.e. no change required.
                    return null;
                }
            }

        } else {
            // if it doesnt exist, create new component version
            entityComponentVersion = new EntityComponentVersion();
            entityComponentVersion.setComponentPrimaryKey(component.componentPrimaryKey());
            entityComponentVersion.setVersion(++previousVersion);
            entityComponentVersion.setResponseType(responseType);
            entityComponentVersion.setIsSubmitted(false);
        }

        return entityComponentVersion;
    }

    /**
     * Duplicates a given application for a user.
     * This creates a new application, with all components having version 1 with the latest value associated with the original document
     *
     * @param applicationId application short primary key
     * @return result
     */
    @RequireCSRFCheck
    public Result duplicateApplication(String applicationId) {
        return TODO;
    }

    /**
     * Generates controller javascript routes
     *
     * @return result
     */
    @AddCSRFToken
    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("applicationRoutes",
                        routes.javascript.ApplicationHandler.createApplication()
                )
        ).as("text/javascript");
    }
}
