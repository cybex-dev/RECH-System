package controllers.ReviewSystem;

import controllers.UserSystem.Secured;
import engine.RECEngine;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Results.TODO;

@Security.Authenticated(Secured.class)
public class ReviewHandler {
    /**
     * Provides the latest application and feedback, allows edits only if {@link RECEngine} allows it.
     * @param applicationID
     * @return
     */
    public Result reviewApplication(String applicationID) {
        return TODO;
    }

    /**
     * Submit revised application and component data only if {@link RECEngine} allows it
     * @return
     */
    public Result submitReview() {
        return TODO;
    }
}
