package controllers.MeetingSystem;

import play.mvc.Controller;
import play.mvc.Result;

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
