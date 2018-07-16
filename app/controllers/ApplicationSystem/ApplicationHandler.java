package controllers.ApplicationSystem;

import DAO.ApplicationSystem.EntityEthicsApplication;
import DAO.UserSystem.EntityPerson;
import helpers.JDBCExecutor;
import models.ApplicationSystem.EthicsApplication;
import models.ApplicationSystem.EthicsApplication.ApplicationType;
import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.*;

import javax.inject.Inject;
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
    //TODO
    public CompletionStage<Result> submitApplication() {
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

            // Get copy of application form
            EthicsApplication application_template = EthicsApplication.lookupApplication(application_type);

            // Handle form errors
            if (formApplication.hasErrors()) {
                try {
                    // TODO check if data is mapped
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

            return ok();
        }, jdbcExecutor);
    }
}
