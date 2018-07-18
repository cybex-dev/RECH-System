package controllers.ApplicationSystem;

import DAO.ApplicationSystem.EntityComponent;
import DAO.ApplicationSystem.EntityComponentversion;
import DAO.ApplicationSystem.EntityEthicsApplication;
import DAO.NMU.EntityDepartment;
import DAO.UserSystem.EntityPerson;
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
import play.mvc.Controller;
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

public class ApplicationHandler extends Controller {

    @Inject
    FormFactory formFactory;

    @Inject
    private JDBCExecutor jdbcExecutor;

    @Inject
    public ApplicationHandler(JDBCExecutor jdbcExecutor) {
        this.jdbcExecutor = jdbcExecutor;
    }

    // Uses XML document to create form, based in as Scala Squence type and processed in view
    public ApplicationHandler() {

    }

    //TODO complete all applications
    public Result allApplications() {
        EntityPerson person = EntityPerson.getPersonById(session().get("user_email"));
        List<EntityEthicsApplication> applicationsByPerson = EntityEthicsApplication.findApplicationsByPerson(person);
        return ok(views.html.ApplicationSystem.AllApplications.render(" :: Applications", applicationsByPerson));
    }

    public Result newApplication(String type) {
        ApplicationType applicationType = ApplicationType.valueOf(type);
        DynamicForm form = formFactory.form();
        EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(applicationType);
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", type.toString(), ethicsApplication.getRootElement(), form));
    }


    /**
     * Post application form to server
     */
    //TODO implement draft save and RECEngine association
    public CompletionStage<Result> createApplication() {
        return CompletableFuture.supplyAsync(() -> {
            DynamicForm formApplication = formFactory.form().bindFromRequest();
            ApplicationType application_type;

            // Get application type as raw field
            try {
                String type = formApplication.get("application_type");
                application_type = ApplicationType.valueOf(type);
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
                    return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", application_type.toString(), application_template.getRootElement(), form));
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
            //TODO implement this
            try {
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
                createEntities(application.getApplicationId(), rootElement);
            } catch (UnhandledElementException e) {
                e.printStackTrace();
                return badRequest();
            } catch (IOException e) {
                e.printStackTrace();
                return internalServerError();
            }

            RECEngine.ChangeApplicationStatus(application.getApplicationId(), ApplicationStatus.NOT_SUBMITTED);

            return ok();

        }, jdbcExecutor);
    }

    /**
     * Sets the application status to submitted and all latest components relating to this particlar application are set to submitted in addition to the date of submission being set
     */
    public CompletableFuture<Result> submitApplication(int applicationId) {
        return CompletableFuture.supplyAsync(() -> {


            RECEngine.SubmitApplication(applicationId);

            return ok();
        }, jdbcExecutor);
    }

    /**
     * Recursively creates database entities from the rootElement
     * @param applicationId application Id of the ethics application
     * @param rootElement   root element containing all values
     * @throws UnhandledElementException
     * @throws IOException
     */
    private void createEntities(Integer applicationId, Element rootElement) throws UnhandledElementException, IOException {
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

                        EntityComponentversion componentversion = new EntityComponentversion();
                        componentversion.setResponseType(rootElement.getType().toLowerCase());
                        componentversion.setDateLastEdited(Timestamp.from(new Date().toInstant()));
                        componentversion.setIsSubmitted(false);
                        componentversion.setResponseType(rootElement.getType());

                        // Set document details
                        componentversion.setDocumentName(title.getValue().toString());
                        componentversion.setDocumentDescription(desc.getValue().toString());
                        File doc = (File) file.getValue();
                        Path p = Paths.get(doc.getPath());
                        byte[] bytes = Files.readAllBytes(p);
                        componentversion.setDocumentBlob(bytes);

                        // Create component entity
                        EntityComponent component = new EntityComponent();
                        component.setApplicationId(applicationId);
                        component.setQuestionId(rootElement.getId());
                        component.insert();

                        // Set component Id in component version and add to database
                        componentversion.setComponentId(component.getComponentId());
                        componentversion.insert();
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
                        EntityComponentversion componentversion = new EntityComponentversion();
                        componentversion.setResponseType(childElement.getType().toLowerCase());
                        componentversion.setDateLastEdited(Timestamp.from(new Date().toInstant()));
                        componentversion.setIsSubmitted(false);
                        componentversion.setResponseType(childElement.getType());
                        Boolean b = (Boolean) childElement.getValue();
                        componentversion.setBoolValue(b);

                        // Create component entity
                        EntityComponent component = new EntityComponent();
                        component.setApplicationId(applicationId);
                        component.setQuestionId(childElement.getId());
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
                            EntityComponentversion componentversion = new EntityComponentversion();

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
                            component.setQuestionId(rootElement.getId());
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
}
