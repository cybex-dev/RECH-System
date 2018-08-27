package controllers.UserSystem;

import dao.ApplicationSystem.EntityEthicsApplication;
import dao.UserSystem.EntityPerson;
import helpers.CookieTags;
import helpers.FileScanner;
import models.UserSystem.Application;
import models.UserSystem.UserType;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.routing.JavaScriptReverseRouter;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Security.Authenticated(Secured.class)
public class ProfileHandler extends Controller {

    static Map<UserType, String> enrolmentKeys = new HashMap<>();

    @Inject
    private FormFactory formFactory;

    public ProfileHandler(){}

    public ProfileHandler(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Gets an overview of applications dependant on user type
     *
     * <ul>
     *     <li>PI: all application statuses</li>
     *     <li>PRP : all own applications and related PI's applications</li>
     *     <li>Reviewer|Liaison|HOD|RTI : all own applications, related PI applications and applications to review</li>
     *     <li>No applications shown. Shown data such as meeting dates, quanity applications to be reviewed with title, etc</li>
     * </ul>
     * @return
     */
    public Result overview(){
        EntityPerson person = EntityPerson.getPersonById(session().get(CookieTags.user_id));
        List<EntityEthicsApplication> applicationsByPerson = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), person.userType());
        List<Application> allApplications = applicationsByPerson.stream().map(Application::create).collect(Collectors.toList());
        return ok(views.html.UserSystem.Dashboard.render(allApplications, new ArrayList<Application>(), new ArrayList<Application>(), new ArrayList<Application>(), new ArrayList<Application>(), new ArrayList<Application>()));
    }

    /**
     * Retrieves all profile information about an individual
     * @return
     */
    public Result settings(){
        return TODO;
    }

    /**
     * Modifies information provided
     * @return
     */
    public Result modifySettings(){
        return TODO;
    }

    /**
     * Generates controller javascript routes
     * @return
     */
    public Result javascriptRoutes(){
        return ok(
                JavaScriptReverseRouter.create("userRoutes",
                        routes.javascript.ProfileHandler.overview(),
                        routes.javascript.ProfileHandler.modifySettings(),
                        routes.javascript.ProfileHandler.settings()
                )
        ).as("text/javascript");
    }

    public Result enrol(){
        EntityPerson p = EntityPerson.getPersonById(session(CookieTags.user_id));
        if (p == null){
            return redirect(routes.LoginController.logout());
        }
        return ok(views.html.UserSystem.Enrol.render(p.getDepartmentName(), p.getFacultyName()));
    }

    public Result doEnrol(){
        DynamicForm form = formFactory.form().bindFromRequest();

        EntityPerson p = EntityPerson.getPersonById(session(CookieTags.user_id));
        if (p == null){
            return redirect(routes.LoginController.logout());
        }

        if (form.hasGlobalErrors()) {
            flash("error", "Invalid enrolment key");
            return badRequest(views.html.UserSystem.Enrol.render(p.getDepartmentName(), p.getFacultyName()));
        }

        if (enrolmentKeys.size() == 0) {
            try {
                List<File> resourceFiles = new FileScanner().getResourceFiles("/enrolment/");
                if (resourceFiles.size() > 0) {
                    File enrolmentFile = resourceFiles.get(0);
                    if (enrolmentFile.exists()) {
                        Scanner s = new Scanner(enrolmentFile);

                        String line = "";
                        while (s.hasNext()) {
                            line = s.nextLine();
                            String[] split = line.split("=");
                            enrolmentKeys.put(UserType.parse(split[0]), split[1]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map.Entry<UserType, String> enrol_code = enrolmentKeys.entrySet().stream().filter(userTypeStringEntry -> userTypeStringEntry.getValue().equals(form.get("enrol_code"))).findFirst().orElse(null);
        if (enrol_code == null) {
            flash("error", "Invalid enrolment key");
            return badRequest(views.html.UserSystem.Enrol.render(p.getDepartmentName(), p.getFacultyName()));
        }

        flash("message", "You are now a " + enrol_code.getKey());
        return redirect(routes.ProfileHandler.overview());
    }
}
