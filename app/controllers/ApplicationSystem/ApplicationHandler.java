package controllers.ApplicationSystem;

import controllers.UserSystem.ProfileHandler;
import controllers.UserSystem.Secured;
import dao.ApplicationSystem.EntityComponent;
import dao.ApplicationSystem.EntityComponentVersion;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.NMU.EntityDepartment;
import dao.UserSystem.EntityPerson;
import engine.RECEngine;
import exceptions.InvalidFieldException;
import exceptions.UnhandledElementException;
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
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
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

//    @Inject
////    private JDBCExecutor jdbcExecutor;

//    @Inject
//    public ApplicationHandler(JDBCExecutor jdbcExecutor) {
//        this.jdbcExecutor = jdbcExecutor;
//    }

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public ApplicationHandler(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    // Uses XML document to create form, based in as Scala Squence type and processed in view
    public ApplicationHandler() {

    }

    /**
     * Allows to edit and application, by loading all data of the latest application.
     *
     * Should only be avialble if the is submitted property is true or {@link RECEngine} allows this.
     * @return
     */
    @AddCSRFToken
    public Result editApplication(Integer applicationID) {
        return TODO;
    }

    /**
     * Provides the latest application version.
     * Dependant of {@link RECEngine}, the form will be altered by showing either
     * <ul>
     *      <li>The next step: Request PRP approval / Submit Application</li>
     *      <li>Latest feedback and allowing edits (if an unsubmitted review) dependant on {@link RECEngine}.</li>
     *      <li>Latest application as readonly (if accepted)</li>
     * </ul>
     * @param applicationID
     * @return
     */
    @AddCSRFToken
    public Result reviewApplication(String applicationID) {
        return TODO;
    }

    /**
     * Submit revised application and component data only if {@link RECEngine} allows it
     * @return
     */
    @RequireCSRFCheck
    public Result submitRevision() {
        return TODO;
    }

    //TODO complete all applications

    /**
     * Gets all applications associated with the user in a view
     * @return
     */
    @AddCSRFToken
    public Result allApplications() {
        try {
            EntityPerson person = EntityPerson.getPersonById(session().get("user_email"));
            List<EntityEthicsApplication> applicationsByPerson = EntityEthicsApplication.findApplicationsByPerson(person);
            return ok(views.html.ApplicationSystem.AllApplications.render(" :: Applications", applicationsByPerson));
        } catch (Exception x) {
            return badRequest(views.html.ApplicationSystem.AllApplications.render(" :: Applications", new ArrayList<>()));
        }
    }

    /**
     * Provides a form to complete a new application depending on type
     * @param type
     * @return
     */
    @AddCSRFToken
    public Result newApplication(String type) {
        ApplicationType applicationType = ApplicationType.parse(type);
        DynamicForm form = formFactory.form();
        EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(applicationType);
        if (ethicsApplication == null) {
            flash("message", "No animal application form available");
            return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
        }
        Element rootElement = ethicsApplication.getRootElement();
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", type.toString(), rootElement, form, ApplicationStatus.DRAFT, ethicsApplication.getQuestionList()));
    }

    @RequireCSRFCheck
    public Result processApplication(){
        DynamicForm form = formFactory.form();
        String id = form.get("application_id");
        EntityEthicsApplicationPK applicationPK = EntityEthicsApplicationPK.fromString(session(CookieTags.user_id), id);
        new RECEngine().nextStep(applicationPK);
        return ok();
    }

    /**
     * Creates a NEW application in the server database
     *
     * If Application for is complete (determined by engine, the PI should be notified of this application on the overview screen and once per week
     */
    @RequireCSRFCheck
    //TODO implement draft save and RECEngine association
    public CompletionStage<Result> createApplication() {
        return CompletableFuture.supplyAsync(() -> {
            DynamicForm formApplication = formFactory.form().bindFromRequest();
            ApplicationType application_type;

            // Get application type as raw field
            try {
//                String type = formApplication.get("application_type");;
//                application_type = ApplicationType.valueOf(type);
                application_type = ApplicationType.Human;
            } catch (Exception x) {
                x.printStackTrace();
                return badRequest(x.toString());
            }


            // Handle form errors
            if (formApplication.hasErrors()) {
                try {
                    // TODO check if data is mapped
                    EthicsApplication application_template = EthicsApplication.lookupApplication(application_type);
                    DynamicForm form = formFactory.form();
                    form.bind(formApplication.rawData());
                    return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", application_type.toString(), application_template.getRootElement(), form, ApplicationStatus.DRAFT, application_template.getQuestionList()));
                } catch (Exception x) {
                    return internalServerError();
                }
            }

            // Create basic Ethics Application
            EntityEthicsApplication application = new EntityEthicsApplication();
            application.setApplicationType(application_type.name().toLowerCase());
            application.setApplicationYear(Calendar.getInstance().get(Calendar.YEAR));
            application.insert();

            // Get copy of application form
            EthicsApplication application_template = EthicsApplication.lookupApplication(application_type);

            // Get root element to add values to
            Element rootElement = application_template.getRootElement();

            // Get form data and add to root element
            Map<String, Object> data = formApplication.get().getData();

            data.forEach((key, value) -> {

                // Gets the application level -> committee or facutly level
                if (key.equals("application_level"))
                    return;

                int index = -1;

                // Check if key is in form 'some_name[_$index]'
                if (key.matches("^(.)+([_](\\d)+)$")){
                    // implies a list item
                    int last = key.lastIndexOf("_");
                    index = Integer.parseInt(key.substring(last) + 1);
                    key = key.substring(0, key.length() - (last-1));
                }

                // Get element by id
                Element element = XMLTools.lookup(rootElement, key);

                //if Element found, then
                if (element != null) {

                    if (element.getParent().getTag().equals("list")){

                        // If element is a list, requiring special processing, create list if list not made, then add values to list
                        ArrayList<Object> list;
                        Object o = element.getValue();
                        if (o instanceof ArrayList) {
                            //TODO fix this ???
                            //noinspection unchecked
                            list = (ArrayList<Object>) o;
                        } else {
                            list = new ArrayList<>();
                        }
                        list.add(index, value);
                        element.setValue(list);
                    } else {
                        //else assign the value
                        element.setValue(value);
                    }
                }
            });

            // Create Entities from Root Element
            try {
                // Set application level
                /*
                 * 0 - None (should not appear)
                 * 1 - Faculty review
                 * 2 - Committee review
                 */

                short appLevel = Short.parseShort(formApplication.get("application_level"));
                application.setApplicationLevel(appLevel);

                // Get pi id from session
                String pi_id = session(CookieTags.user_id);
                application.setPiId(pi_id);

                // Get prp id from Elements
                String prp_contact_email = XMLTools.lookup(rootElement, "prp_contact_email").getValue().toString();
                application.setPrpId(prp_contact_email);

                // Get application department and faculty
                String dept_name = XMLTools.lookup(rootElement, "app_department").getValue().toString();
                String faculty_name = EntityDepartment.findFacultyByDepartment(dept_name);

                // Set application data
                application.setDepartmentName(dept_name);
                application.setFacultyName(faculty_name);

                // Update application
                application.update();

            } catch (InvalidFieldException e) {
                e.printStackTrace();
                return notFound();
            }

            try {
                // Create entities from Root Element
                createEntities(application.applicationPrimaryKey(), rootElement);
            } catch (UnhandledElementException e) {
                e.printStackTrace();
                return badRequest();
            } catch (IOException e) {
                e.printStackTrace();
                return internalServerError();
            }

            new RECEngine().nextStep(application.applicationPrimaryKey());

            return ok();

        }, httpExecutionContext.current() );
        /*jdbcExecutor*/
    }

    /**
     * Duplicates a given application for a user.
     * This creates a new application, with all components having version 1 with the latest value associated with the original document
     * @param applicationId
     * @return
     */
    @RequireCSRFCheck
    public Result duplicateApplication(Integer applicationId){
        return TODO;
    }

    /**
     * Recursively creates database entities from the rootElement
     * @param applicationId application Id of the ethics application
     * @param rootElement   root element containing all values
     * @throws UnhandledElementException
     * @throws IOException
     */
    private void createEntities(EntityEthicsApplicationPK applicationId, Element rootElement) throws UnhandledElementException, IOException {
        if (rootElement != null) {
            switch (rootElement.getTag()) {
                case "value":
                case "question":
                case "list":
                case "section":
                case "application": {
                    //do nothing, just continue
                    for (Element e : rootElement.getChildren()) {
                        createEntities(applicationId, e);
                    }
                    break;
                }

                case "group": {
                    //creates new group, do something only if group type = document
                    if (rootElement.getType().equals("document")){
                        // Title
                        Element title = rootElement.getChildren().pop();
                        Element desc = rootElement.getChildren().pop();
                        Element file = rootElement.getChildren().pop();

                        EntityComponentVersion componentversion = new EntityComponentVersion();
                        componentversion.setResponseType(rootElement.getType().toLowerCase());
                        componentversion.setDateLastEdited(Timestamp.from(new Date().toInstant()));
                        componentversion.setResponseType(rootElement.getType());

                        // Set document details
                        componentversion.setDocumentName(title.getValue().toString());
                        componentversion.setDocumentDescription(desc.getValue().toString());
                        File doc = (File) file.getValue();
                        String hashCode = String.valueOf(doc.hashCode());
                        componentversion.setDocumentLocationHash(hashCode);

                        // Create component entity
                        EntityComponent component = new EntityComponent();
                        component.setApplicationId(applicationId);
                        component.setComponentId(rootElement.getId());
                        component.insert();

                        // Set component Id in component version and add to database
                        componentversion.setComponentId(component.getComponentId());
                        componentversion.insert();

                        //TODO upload file
//                        saveDocumentToDirectory(rootElement.getId(), doc);
                    } else {
                        for (Element e : rootElement.getChildren()) {
                            createEntities(applicationId, e);
                        }
                    }
                    break;
                }

                case "extension": {
                    //creates new component combination, depending on boolean value, adds a new component
                    LinkedList<Element> children = rootElement.getChildren();
                    Element childElement = children.pop();
                    if (childElement.getValue() instanceof Boolean) {

                        // Get boolean value associated with the element.
                        EntityComponentVersion componentversion = new EntityComponentVersion();
                        componentversion.setResponseType(childElement.getType().toLowerCase());
                        componentversion.setDateLastEdited(Timestamp.from(new Date().toInstant()));
                        componentversion.setIsSubmitted(false);
                        componentversion.setResponseType(childElement.getType());
                        Boolean b = (Boolean) childElement.getValue();
                        componentversion.setBoolValue(b);

                        // Create component entity
                        EntityComponent component = new EntityComponent();
                        component.setApplicationId(applicationId);
                        component.setComponentId(childElement.getId());
                        component.insert();

                        // Set component Id in component version and add to database
                        componentversion.setComponentId(component.getComponentId());
                        componentversion.insert();

                        if (b) {
                            for (Element e : children) {
                                createEntities(applicationId, e);
                            }
                        }
                    }
                    break;
                }
                case "component": {
                    //creates new basic component from children: question and value

                    if (rootElement.getValue() instanceof ArrayList) {
                        ArrayList list = (ArrayList) rootElement.getValue();
                        int size = list.size();

                        // loop through all children $size amount of times
                        for (int i = 0; i < size; i++) {
                            // Create version storing data
                            EntityComponentVersion componentversion = new EntityComponentVersion();

                            // Get value from list
                            Object v = list.get(i);

                            // Switch statement early in processing to handle exceptions early before transactions have been done
                            switch (rootElement.getType().toLowerCase()) {
                                case "boolean": {
                                    componentversion.setBoolValue(Boolean.parseBoolean(v.toString()));
                                    break;
                                }

                                case "text":
                                case "date":
                                case "number":
                                case "long_text": {
                                    componentversion.setTextValue(v.toString());
                                    break;
                                }

                                case "document": {
                                    // This should never be triggered
                                    throw new UnhandledElementException("Document " + rootElement.getId() + "(i=" + String.valueOf(i) + ") processed in case \"Document\" statement");
                                }

                                default:{
                                    throw new UnhandledElementException(rootElement.toString());
                                }
                            }
                            componentversion.setResponseType(rootElement.getType().toLowerCase());
                            componentversion.setDateLastEdited(Timestamp.from(new Date().toInstant()));
                            componentversion.setIsSubmitted(false);

                            // Create component entity
                            EntityComponent component = new EntityComponent();
                            component.setApplicationId(applicationId);
                            component.setQuestion(rootElement.getId());
                            component.insert();

                            // Set component Id in component version and add to database
                            componentversion.setComponentId(component.getComponentId());
                            componentversion.insert();
                        }

                    } else {
                        // not a list or an empty list, do nothing
                        return;
                    }
                    break;
                }

                default:{
                    throw new UnhandledElementException(rootElement.toString());
                }
            }
        }
    }

    @RequireCSRFCheck
    public Result upload(String id) {
        File file = request().body().asRaw().asFile();
        String hashcode = String.valueOf(file.hashCode());
        return ok();
    }

    /**
     * Generates controller javascript routes
     * @return
     */
    @AddCSRFToken
    public Result javascriptRoutes(){
        return ok(
                JavaScriptReverseRouter.create("applicationRoutes",
                        routes.javascript.ApplicationHandler.createApplication()
                )
        ).as("text/javascript");
    }
}
