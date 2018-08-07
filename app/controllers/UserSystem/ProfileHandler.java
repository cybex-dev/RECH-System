package controllers.UserSystem;

import dao.ApplicationSystem.EntityEthicsApplication;
import dao.UserSystem.EntityPerson;
import helpers.CookieTags;
import models.UserSystem.Application;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.routing.JavaScriptReverseRouter;

import java.util.List;
import java.util.stream.Collectors;

@Security.Authenticated(Secured.class)
public class ProfileHandler extends Controller {

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
        return ok(views.html.UserSystem.Dashboard.render(allApplications));
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
}
