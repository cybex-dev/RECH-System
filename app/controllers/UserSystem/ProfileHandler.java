package controllers.UserSystem;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.routing.JavaScriptReverseRouter;

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
        return TODO;
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
