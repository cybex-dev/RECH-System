package controllers.ReviewSystem;

import engine.RECEngine;
import play.mvc.Result;

import static play.mvc.Results.TODO;

public class ReviewHandler {
    /**
     * Provides the latest application and feedback, allows edits only if {@link RECEngine} allows it.
     * @param applicationID
     * @return
     */
    public Result reviewApplication(Integer applicationID) {
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
