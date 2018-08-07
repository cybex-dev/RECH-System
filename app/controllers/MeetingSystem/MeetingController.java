package controllers.MeetingSystem;

import controllers.UserSystem.Secured;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class MeetingController extends Controller {

    /**
     * Gets a list of all applications to be reviewed at the meeting
     * @return
     */
    public Result allApplications(){
        return TODO;
    }

    /**
     * Gets a specific application to review at meeting
     * @param applicationId
     * @return
     */
    public Result getApplication(Integer applicationId) {
        return TODO;
    }

    /**
     * Submits resolution feedback and status of an application
     * @return
     */
    public Result submitResolution() {
        return TODO;
    }
}
