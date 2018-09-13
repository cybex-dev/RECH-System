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
import models.ApplicationSystem.ApplicationStatus;
import models.ApplicationSystem.EthicsApplication;
import models.ApplicationSystem.EthicsApplication.ApplicationType;

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
     * @return
     */
    @AddCSRFToken
    public Result editApplication(String applicationID) {
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(applicationID);
        EntityEthicsApplication ethicsApplication = EntityEthicsApplication.find.byId(entityEthicsApplicationPK);
        if (ethicsApplication == null) {
            return badRequest();
        }

        Element element = PopulateRootElement(ethicsApplication);
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Edit Application", ethicsApplication.getApplicationType(), element, ApplicationStatus.parse(ethicsApplication.getInternalStatus()), null, true, routes.ApplicationHandler.submitEdit(), false));
    }

    /**
     * Provides the latest application version.
     * Dependant of {@link RECEngine}, the form will be altered by showing either
     * <ul>
     * <li>The next step: Request PRP approval / Submit Application</li>
     * <li>Latest feedback and allowing edits (if an unsubmitted review) dependant on {@link RECEngine}.</li>
     * <li>Latest application as readonly (if accepted)</li>
     * </ul>
     *
     * @param applicationID
     * @return
     */
    @AddCSRFToken
    public Result reviewApplication(String applicationID) {
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(applicationID);
        EntityEthicsApplication ethicsApplication = EntityEthicsApplication.find.byId(entityEthicsApplicationPK);

        if (ApplicationStatus.parse(ethicsApplication.getInternalStatus()) == ApplicationStatus.DRAFT) {
            return editApplication(applicationID);
        } else {
            Element element = PopulateRootElement(ethicsApplication);
            return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Review Application", ethicsApplication.getApplicationType(), element, ApplicationStatus.parse(ethicsApplication.getInternalStatus()), null, false, routes.ApplicationHandler.submitRevision(), false));
        }
    }

    public static Element PopulateRootElement(EntityEthicsApplication application) {
        EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(application.type());
        List<EntityComponentVersion> latestComponents = EntityEthicsApplication.getLatestComponents(application.applicationPrimaryKey());
        Map<String, Object> entryMap = new HashMap<>();
        latestComponents.forEach(entityComponentVersion -> {
            if (entityComponentVersion != null) {
                switch (entityComponentVersion.getResponseType()) {
                    case "boolean": {
                        entryMap.put(entityComponentVersion.getComponentId(), entityComponentVersion.getBoolValue());
                        break;
                    }

                    case "text": {
                        entryMap.put(entityComponentVersion.getComponentId(), entityComponentVersion.getTextValue());
                        break;
                    }

                    case "document": {
                        String component = entityComponentVersion.getComponentId();
                        String compString = component.substring(4, component.length());
                        entryMap.put(compString + "_title", entityComponentVersion.getDocumentName());
                        entryMap.put(compString + "_desc", entityComponentVersion.getDocumentName());
                        entryMap.put(compString + "_document", entityComponentVersion.getDocumentName());
                        break;
                    }
                }
            }

        });
        return EthicsApplication.addValuesToRootElement(ethicsApplication.getRootElement(), entryMap);
    }

    /**
     * Submit revised application and component data only if {@link RECEngine} allows it
     *
     * @return
     */
    @RequireCSRFCheck
    public Result submitRevision() {
        return TODO;
    }

    //TODO complete all applications

    /**
     * Gets all applications associated with the user in a view
     *
     * @return
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
     * @param type
     * @return
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
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", type.toString(), rootElement, ApplicationStatus.DRAFT, ethicsApplication.getQuestionList(), true, routes.ApplicationHandler.createApplication(), true));
    }

    @RequireCSRFCheck
    public Result submitEdit() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String id = form.get("application_id");
        EntityEthicsApplicationPK applicationPK = EntityEthicsApplicationPK.fromString(id);
        new RECEngine().nextStep(applicationPK);
        return ok();
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
                return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", application_type.toString(), application_template.getRootElement(), ApplicationStatus.DRAFT, application_template.getQuestionList(), true, routes.ApplicationHandler.submitEdit(), false));

            }

            // Create basic Ethics Application
            int year = Calendar.getInstance().get(Calendar.YEAR);
            EntityEthicsApplication application = new EntityEthicsApplication();
            application.setApplicationType(application_type.name().toLowerCase());
            application.setApplicationYear(year);
            application.setFacultyName(formApplication.get("app_faculty"));
            application.setDepartmentName(formApplication.get("app_department"));
            EntityDepartment app_department = EntityDepartment.findDepartmentByName(formApplication.get("app_department"));
            int applicationNumber = EntityEthicsApplication.GetNextApplicationNumber(app_department, application_type, year);
            application.setApplicationNumber(applicationNumber);

            // Get pi id from session
            String pi_id = session(CookieTags.user_id);
            application.setPiId(pi_id);
            application.insert();


            Map<String, Object> filteredData = new HashMap<>();
            formApplication.get().getData().entrySet().stream().filter(e -> e.getValue() != null && !e.getValue().toString().isEmpty()).forEach(e -> filteredData.put(e.getKey(), e.getValue()));
            request().body().asMultipartFormData().getFiles().stream()
                    .filter(o -> !o.getFilename().isEmpty())
                    // containers FilePart which has file name, and key and actual file
                    .forEach(e -> filteredData.put(e.getKey(), e));

            fillinApplicationData(application.applicationPrimaryKey(), filteredData);

            flash("success", "Your application has been saved");
            return redirect(routes.ApplicationHandler.allApplications());
        }, httpExecutionContext.current());
    }

    private void fillinApplicationData(EntityEthicsApplicationPK applicationId, Map<String, Object> data) {
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(applicationId);

        Element rootElement = EthicsApplication.lookupApplication(application.type()).getRootElement();

        // Get prp id from Elements
        if (data.get("prp_contact_email") != null) {
            String prpContactEmail = data.get("prp_contact_email").toString();
            if (!prpContactEmail.isEmpty())
                try {
                    application.setPrpId(prpContactEmail);
                    application.update();
                } catch (Exception x){
                    flash("danger", "Primary Responsible Person's email address was not found. Please use lookup feature or ensure that the email address is correct.");
                    x.printStackTrace();
                }
        }

        if (application.getInternalStatus() == null ||
                application.getInternalStatus() != ApplicationStatus.DRAFT.getStatus() ||
                application.getInternalStatus() != ApplicationStatus.NOT_SUBMITTED.getStatus()) {
            application.setInternalStatus(ApplicationStatus.DRAFT.getStatus());
        }

        // Update application
        application.update();

        List<EntityComponent> entityComponents = EntityComponent.GetAllApplicationCompontents(applicationId);

        // Get form data and add to root element
        data.entrySet().stream()
                .filter(stringObjectEntry -> !stringObjectEntry.getValue().toString().isEmpty())
                .forEach(entry -> {
                    Element componentElement = XMLTools.lookup(rootElement, entry.getKey());
                    if (componentElement != null) {
                        EntityComponent component = getUpdatableComponent(entry, componentElement, applicationId, entityComponents);

                        String type = componentElement.getType();
                        System.out.println("Type: " + type);
                        EntityComponentVersion entityComponentVersion = getUpdatableComponentVersion(component, type);
                        // be able to compile a document with description, etc. possibly update each document but split string id  base on "_title _desc and _document" and update documnt component 3 times

                        if (entityComponentVersion != null) {
                            // insert component version value
                            setComponentValue(entityComponentVersion, entry, type);
                        }

                        // if == null, means that the the component was submitted and is not meant to be changed, but it is being changed. This change will not be saved.
                    }
                });
    }

    private void setComponentValue(EntityComponentVersion entityComponentVersion, Map.Entry<String, Object> entry, String responseType) {
        switch (responseType){

            case "number": {
                entityComponentVersion.setTextValue(String.valueOf((int) entry.getValue()));
                break;
            }

            case "date": {
                Timestamp ts = Timestamp.valueOf((String) entry.getValue());
                entityComponentVersion.setTextValue(ts.toString());
                break;
            }

            case "long_text":
            case "text": {
                if (entry.getKey().contains("doc_")){
                    if (entry.getKey().contains("_title")){
                        entityComponentVersion.setDocumentName((String) entry.getValue());
                    } else {
                        entityComponentVersion.setDocumentDescription((String) entry.getValue());
                    }
                } else {
                    entityComponentVersion.setTextValue((String) entry.getValue());
                }
                break;
            }

            case "boolean":{
                boolean b = Boolean.parseBoolean((String) entry.getValue());
                entityComponentVersion.setBoolValue(b);
                break;
            }

            case "document":{
                // Check saving directory exists
                String docDirectory = config.getString("documentLocation");
                try {
                    File dir = new File(docDirectory);
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                } catch (Exception x){
                    x.printStackTrace();
                }

                // Save file
                try {
                    FilePart filePart = (FilePart) entry.getValue();
                    File file = (File) filePart.getFile();
                    File newFile = new File(docDirectory.concat(entityComponentVersion.applicationPrimaryKey().shortName()).concat("~" + entityComponentVersion.getVersion() + "~").concat(filePart.getFilename()));
                    file.renameTo(newFile);
                    entityComponentVersion.setDocumentLocationHash(newFile.getPath());
                } catch (Exception x){
                    x.printStackTrace();
                }
                break;
            }

            default:{
                break;
            }
        }
        entityComponentVersion.setDateLastEdited(Timestamp.from(Instant.now()));
        entityComponentVersion.save();

    }

    /**
     * Gest the EntityComponent for a specific application. Creates the component if needed and returns the instance to be used by the component version.
     *
     * @param entry
     * @param componentElement
     * @param applicationId
     * @param entityComponents
     * @return
     */
    private EntityComponent getUpdatableComponent(Map.Entry<String, Object> entry, Element componentElement, EntityEthicsApplicationPK applicationId, List<EntityComponent> entityComponents) {
        String componentName = entry.getKey();
        if (componentName.contains("doc_")){
            if (componentName.contains("_title")){
                componentName = componentName.replace("_title", "");
            } else if (componentName.contains("_desc")){
                componentName = componentName.replace("_desc", "");
            } else {
                componentName = componentName.replace("_document", "");
            }
        }

        String finalComponentName = componentName;
        EntityComponent component = entityComponents.stream().filter(entityComponent -> entityComponent.getComponentId().equals(finalComponentName)).findAny().orElse(null);
        if (component == null) {
            component = new EntityComponent();
            component.setApplicationId(applicationId);
            component.setComponentId(entry.getKey());

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
                if (entityComponentVersion.wasFeedbackGiven()){
                    // if component version has been submitted, create new component
                    entityComponentVersion = new EntityComponentVersion();
                    entityComponentVersion.setComponentPrimaryKey(component.componentPrimaryKey());
                    entityComponentVersion.setVersion(++previousVersion);
                    entityComponentVersion.setResponseType(responseType);
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
        }

        return entityComponentVersion;
    }

    /**
     * Duplicates a given application for a user.
     * This creates a new application, with all components having version 1 with the latest value associated with the original document
     *
     * @param applicationId
     * @return
     */
    @RequireCSRFCheck
    public Result duplicateApplication(Integer applicationId) {
        return TODO;
    }

    /**
     * Generates controller javascript routes
     *
     * @return
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
