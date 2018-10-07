package controllers.ApplicationSystem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import controllers.UserSystem.Secured;
import dao.ApplicationSystem.EntityComponent;
import dao.ApplicationSystem.EntityComponentVersion;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.NMU.EntityDepartment;
import dao.UserSystem.EntityPerson;
import engine.Permission;
import engine.RECEngine;
import helpers.CookieTags;
import helpers.JDBCExecutor;
import models.App;
import models.ApplicationSystem.ApplicationStatus;
import models.ApplicationSystem.EthicsApplication;
import models.ApplicationSystem.EthicsApplication.ApplicationType;

import models.GuiButton;
import models.UserSystem.Application;
import models.UserSystem.UserType;
import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;
import play.api.mvc.Call;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.Json;
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
import java.util.stream.Collectors;

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
            return badRequest("Edit Application: Application could not be found");
        }

        Element element = EthicsApplication.PopulateRootElement(ethicsApplication);
        Map<String, Boolean> editableMap = new HashMap<>();
        XMLTools.flatten(element).forEach(s -> editableMap.put(s, true));

        Map<String, List<String>> latestComponentFeedback = EntityEthicsApplication.GetLatestComponentFeedback(entityEthicsApplicationPK);

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
            return badRequest("Review Application: Application could not be found");

        ApplicationStatus status = ApplicationStatus.parse(ethicsApplication.getInternalStatus());
        if (status.equals(ApplicationStatus.DRAFT) ||
                status.equals(ApplicationStatus.NOT_SUBMITTED) &&
                        session().get(CookieTags.user_id).equals(ethicsApplication.getPiId())) {
            return editApplication(applicationID);
        } else {
            Element element = EthicsApplication.PopulateRootElement(ethicsApplication);
            Map<String, Boolean> editableMap = new HashMap<>();
            XMLTools.flatten(element).forEach(s -> editableMap.put(s, false));
            Map<String, List<String>> latestComponentFeedback = EntityEthicsApplication.GetLatestComponentFeedback(entityEthicsApplicationPK);
            return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Review Application", ethicsApplication.type(), element, editableMap, false, entityEthicsApplicationPK.shortName(), false, true, latestComponentFeedback, GuiButton.negHomeCancel, null, null));
        }
    }

//    /**
//     * Submit revised application and component data only if {@link RECEngine} allows it
//     *
//     * @return result
//     */
//    @RequireCSRFCheck
//    public Result submitRevision() {
//        return TODO;
//    }

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
            List<EntityEthicsApplication> entity_ownApplications = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.PrimaryInvestigator);
            List<Application> ownApps = entity_ownApplications.stream().map(app -> Application.create(app, person)).collect(Collectors.toList());
            return ok(views.html.ApplicationSystem.AllApplications.render(" :: Applications", ownApps));
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
    public Result saveEdit() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String id = form.get("application_id");
        EntityEthicsApplicationPK applicationPK = EntityEthicsApplicationPK.fromString(id);
        //update all components
        Map<String, Object> cleanData = clean(form, applicationPK);
        // TODO see what is missing here
        fillInApplicationData(applicationPK, cleanData);

        RECEngine.getInstance().nextStep(applicationPK);
        return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
    }

    @RequireCSRFCheck
    public Result submitApplication() {
        DynamicForm formApplication = formFactory.form().bindFromRequest();

        // Get application type as raw field
        String type = formApplication.get("application_type");
        ApplicationType applicationType = ApplicationType.parse(type);

        // Handle form errors
        if (formApplication.hasErrors()) {
            EthicsApplication application_template = EthicsApplication.lookupApplication(applicationType);
            Map<String, Boolean> editableMap = new HashMap<>();
            XMLTools.flatten(application_template.getRootElement()).forEach(s -> editableMap.put(s, true));
            return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", applicationType, application_template.getRootElement(), editableMap, false, "", false, false, new HashMap<>(), GuiButton.negHomeCancel, GuiButton.posSubmitApplication, GuiButton.netSaveApplication));
        }

        EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(applicationType);
        EntityEthicsApplication application = saveApplicationDataReceived(formApplication, ethicsApplication);

        RECEngine.getInstance().nextStep(application.applicationPrimaryKey());

        if (application.getHodId() == null || application.getHodId().isEmpty()){
            flash("warning", "Unable to submit, no HOD is available in the system");
            return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
        }

        if (application.getRtiId() == null || application.getRtiId().isEmpty()){
            flash("warning", "Unable to submit, no Faculty RTI representative is available in the system");
            return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
        }

        if (application.getPrpId() == null || application.getPrpId().isEmpty()){
            flash("warning", "Unable to submit, no Primary Responsible Person has been selected.");
            return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
        }

        if (ApplicationStatus.parse(application.getInternalStatus()) == ApplicationStatus.DRAFT) {
            RECEngine.getInstance().nextStep(application.applicationPrimaryKey());
        }
        if (ApplicationStatus.parse(application.getInternalStatus()) == ApplicationStatus.NOT_SUBMITTED) {
            RECEngine.getInstance().nextStep(application.applicationPrimaryKey());
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
    public CompletionStage<Result> saveApplication() {
        return CompletableFuture.supplyAsync(() -> {
            DynamicForm formApplication = formFactory.form().bindFromRequest();

            // Get application type as raw field
            String type = formApplication.get("application_type");
            ApplicationType applicationType = ApplicationType.parse(type);

            // Handle form errors
            if (formApplication.hasErrors()) {
                EthicsApplication application_template = EthicsApplication.lookupApplication(applicationType);
                Map<String, Boolean> editableMap = new HashMap<>();
                XMLTools.flatten(application_template.getRootElement()).forEach(s -> editableMap.put(s, true));
                return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", applicationType, application_template.getRootElement(), editableMap, false, "", false, false, new HashMap<>(), GuiButton.negHomeCancel, GuiButton.posSubmitApplication, GuiButton.netSaveApplication));
            }

            EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(applicationType);
            EntityEthicsApplication application = saveApplicationDataReceived(formApplication, ethicsApplication);

            RECEngine.getInstance().tryCompleteStageCheck(application.applicationPrimaryKey());

            flash("success", "Your application has been saved");
            return redirect(routes.ApplicationHandler.allApplications());
        }, httpExecutionContext.current());
    }

    private EntityEthicsApplication saveApplicationDataReceived(DynamicForm formApplication, EthicsApplication ethicsApplication) {

        EntityEthicsApplication application = null;

        // Create basic Ethics Application
        if (formApplication.get("application_id") == null ||
                formApplication.get("application_id").toString().isEmpty()) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            application = new EntityEthicsApplication();
            application.setApplicationType(ethicsApplication.getType().name().toLowerCase());
            application.setApplicationYear(year);
            application.setFacultyName(formApplication.get("app_faculty"));
            application.setDepartmentName(formApplication.get("app_department"));
            String level = formApplication.get("application_level");
            if (level == null || level.isEmpty())
                level = "3";
            application.setApplicationLevel(Short.parseShort(level));
            EntityDepartment app_department = EntityDepartment.findDepartmentByName(formApplication.get("app_department"));
            int applicationNumber = EntityEthicsApplication.GetNextApplicationNumber(app_department, ethicsApplication.getType(), year);
            application.setApplicationNumber(applicationNumber);

            // Get pi id from session
            String pi_id = session(CookieTags.user_id);
            // Set hod, rti, prp & pi ids
            application.setPiId(pi_id);

            application.insert();
        } else {
            application = EntityEthicsApplication.GetApplication(EntityEthicsApplicationPK.fromString(formApplication.get("application_id").toString()));
        }

        Map<String, Object> cleanedData = clean(formApplication, application.applicationPrimaryKey());
        fillInApplicationData(application.applicationPrimaryKey(), cleanedData);

        return application;
    }

    /**
     * Filter data, clean data and add documents to map
     * @param formApplication
     * @param applicationId
     * @return
     */
    private Map<String, Object> clean(DynamicForm formApplication, EntityEthicsApplicationPK applicationId) {
        Map<String, Object> filteredData = new HashMap<>();
        formApplication.get().getData()
                .entrySet()
                .stream()
                .forEach(e -> filteredData.put(e.getKey(), e.getValue()));
        request().body().asMultipartFormData().getFiles().stream()
                .filter(o -> !o.getFilename().isEmpty())
                .forEach(e -> filteredData.put(e.getKey(), e));

        return filteredData;
    }

    private void fillInApplicationData(EntityEthicsApplicationPK applicationId, Map<String, Object> data) {
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(applicationId);

        Element rootElement = EthicsApplication.lookupApplication(application.type()).getRootElement();

        if (application.getInternalStatus() == null ||
                application.getInternalStatus() != ApplicationStatus.DRAFT.getStatus() &&
                        application.getInternalStatus() != ApplicationStatus.NOT_SUBMITTED.getStatus()) {
            application.setInternalStatus(ApplicationStatus.DRAFT.getStatus());
        }

        // Get prp id from Elements
        String prpContactEmail = (String) data.get("prp_contact_email");
        if (prpContactEmail != null && !prpContactEmail.isEmpty()) {
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

        // Update application
        application.update();

        // Delete all latest components from database. We can do this only if they were not submitted.
        List<EntityComponentVersion> latestComponents = EntityEthicsApplication.getLatestComponents(applicationId);
        latestComponents.forEach(entityComponentVersion -> {
            if (entityComponentVersion != null){
                if (!entityComponentVersion.getIsSubmitted()){
                    entityComponentVersion.delete();
                }
            } else{
                System.out.println("Not removing EntityComponentVersion - it is null");
            }
        });

        // Get form data and add to root element
        data.entrySet().stream()
                .filter(stringObjectEntry -> !stringObjectEntry.getValue().toString().isEmpty())
                .forEach(entry -> {
                    String key = entry.getKey();
                    if(key.matches("^\\w+[_]\\d+$")) {
                        key = key.substring(0, key.lastIndexOf("_"));
                    }

                    Element componentElement = XMLTools.lookup(rootElement, key);
                    if (componentElement != null) {
                        EntityComponent component = getUpdatableComponent(entry, componentElement, applicationId);

                        String type = componentElement.getType();
                        System.out.println("Type: " + type);
                        if (componentElement.getParent().getType().equals("document")) {
                            type = "document";
                            System.out.println("Document detected. changing EntityComponentVersion to document");
                        }

                        EntityComponentVersion entityComponentVersion = getUpdatableComponentVersion(component, type);
                        // be able to compile a document with description, etc. possibly update each document but split string id  base on "_title _desc and _document" and update documnt component 3 times

                        if (entityComponentVersion != null) {
                            // insert component version value
                            setComponentValue(entityComponentVersion, entry, type);
                        }
                    }
                });
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
                    entityComponentVersion.setTextValue((String) entry.getValue());
                break;
            }

            case "boolean": {
                boolean b = false;
                if (entry.getValue().toString().equals("1") ||
                        entry.getValue().toString().toLowerCase().equals("on") ||
                        entry.getValue().toString().toLowerCase().equals("true")){
                    b = true;
                    System.out.println("Converting " + entry.getValue() + " -> " + b);
                } else if (entry.getValue().toString().equals("0") ||
                        entry.getValue().toString().toLowerCase().equals("off") ||
                        entry.getValue().toString().toLowerCase().equals("false")) {
                    b = false;
                    System.out.println("Converting " + entry.getValue() + " -> " + b);
                } else {
                    System.out.println("Unrecognized boolean value, defaulting to false");
                    b = false;
                    System.out.println("Converting " + entry.getValue() + " -> " + b);
                }
                System.out.println("SET Component Value: (Boolean) [" + entry.getKey() + "]\t\tRAW [" + entry.getValue() + "] \t\tPARSED [" + String.valueOf(b) + "]");
                entityComponentVersion.setBoolValue(b);
                break;
            }

            case "document": {
                if (entry.getKey().contains("_title")) {
                    entityComponentVersion.setDocumentName((String) entry.getValue());
                } else if (entry.getKey().contains("_desc")) {
                    entityComponentVersion.setDocumentDescription((String) entry.getValue());
                } else {
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
                }
                break;
            }

            default: {
                break;
            }
        }
        entityComponentVersion.setDateLastEdited(Timestamp.from(Instant.now()));
        entityComponentVersion.save();
        System.out.println("DB_IN : " + entityComponentVersion.toString());

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
        return ok();
    }

    @RequireCSRFCheck
    public Result download(){
        JsonNode json = request().body().asJson();
        String userId = session().get("user_id");
        if (userId == null || userId.isEmpty()) {
            flash("danger", "Invalid session, please login again!");
            return badRequest("An invalid session is detected, please logout and login again");
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("danger", "An error occurred, please login again!");
            return notFound("Could not find you on the system, logout and  login in again.");
        }

        String componentId = json.findPath("component_id").textValue();
        if (componentId == null || componentId.isEmpty()){
            flash("danger", "File could not be found.");
            return notFound("Could not find the file specified.");
        }

        String applicationId = json.findPath("application_id").textValue();
        if (applicationId == null || applicationId.isEmpty()){
            flash("danger", "Associated application not found.");
            return notFound("Could not find the associated application.");
        }

        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(EntityEthicsApplicationPK.fromString(applicationId));
        if (application == null) {
            flash("danger", "Could not find ethics application on the system.");
            return notFound("Could not find the associated application on the system.");
        }

        Permission permission = RECEngine.checkAuthorized(personById, application);
        if (permission == Permission.NONE){
            flash("danger", "Associated application not found.");
            return unauthorized("You are not authorized to view / download this file.");
        }

        if (!componentId.contains("_document")){
            flash("danger", "Invalid component format");
            return unauthorized("Could not find associated entry in database.");
        }

        componentId = componentId.replace("_document", "");

        EntityComponentVersion entityComponentVersion = EntityComponentVersion.GetLatestComponent(application.applicationPrimaryKey(), componentId);
        if (entityComponentVersion == null) {
            flash("danger", "Entry not found in database.");
            return unauthorized("Could not find associated entry in database.");
        }

        String documentLocationHash = entityComponentVersion.getDocumentLocationHash();
        if (documentLocationHash == null) {
            flash("danger", "File not found in database.");
            return unauthorized("Could not find associated file in database.");
        }

        File newFile = new File(documentLocationHash);
        if (!newFile.exists()) {
            flash("danger", "File does not exist.");
            return unauthorized("File does not exist.");
        }

        String filename = documentLocationHash.substring(documentLocationHash.lastIndexOf("~") + 1);

        response().setContentType("application/x-download");
        response().setHeader("Content-disposition","attachment; filename=" + filename);
        Call call = routes.ApplicationHandler.downloadDocument(applicationId, componentId);
        return ok(call.url());
    }

    public Result downloadDocument(String appId, String componentId) {
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(EntityEthicsApplicationPK.fromString(appId));
        if (application == null) {
            flash("danger", "Could not find ethics application on the system.");
            return notFound("Could not find the associated application on the system.");
        }

        componentId = componentId.replace("_document", "");

        EntityComponentVersion entityComponentVersion = EntityComponentVersion.GetLatestComponent(application.applicationPrimaryKey(), componentId);
        if (entityComponentVersion == null) {
            flash("danger", "Entry not found in database.");
            return unauthorized("Could not find associated entry in database.");
        }

        String documentLocationHash = entityComponentVersion.getDocumentLocationHash();
        if (documentLocationHash == null) {
            flash("danger", "File not found in database.");
            return unauthorized("Could not find associated file in database.");
        }

        File newFile = new File(documentLocationHash);
        if (!newFile.exists()) {
            flash("danger", "File does not exist.");
            return unauthorized("File does not exist.");
        }

        String filename = documentLocationHash.substring(documentLocationHash.lastIndexOf("~") + 1);

        response().setContentType("application/x-download");
        response().setHeader("Content-disposition","attachment; filename=" + filename);
        return ok(newFile);
    }

    @RequireCSRFCheck
    public Result printApplication(){
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        String userId = session().get("user_id");
        if (userId == null || userId.isEmpty()) {
            flash("danger", "Invalid session, please login again!");
            result.put("danger", "Invalid session, please login again!");
            return badRequest();
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("danger", "An error occurred, please login again!");
            result.put("danger", "An error occurred, please login again!");
            return notFound();
        }

        //get printable version

        return ok();
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
                        routes.javascript.ApplicationHandler.newApplication(),
                        routes.javascript.ApplicationHandler.duplicateApplication(),
                        routes.javascript.ApplicationHandler.download(),
                        routes.javascript.ApplicationHandler.downloadDocument(),
                        routes.javascript.ApplicationHandler.printApplication()
                )
        ).as("text/javascript");
    }
}
