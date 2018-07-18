package controllers.UserSystem;

import play.mvc.Controller;
import play.mvc.Result;

public class LoginController extends Controller {

    /**
     * Provides login for to login into system
     * @return
     */
    public Result login(){
        return TODO;
    }

    /**
     * Accepts login credentials and assigns token for login session
     * @return
     */
    public Result doLogin(){
        return TODO;
    }

    /**
     * Deletes session and clears cookies, which logs out the user
     * @return
     */
    public Result logout(){
        return ok(views.html.General.About.render());
    }
}
