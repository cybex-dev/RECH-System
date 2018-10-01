package controllers.NotificationSystem;

import play.mvc.Controller;
import play.mvc.Result;

public class NotificationManager extends Controller {

    enum NotificationType {
        Warning, Attention, Info, Status
    }

    /**
     *
     * @return
     */
    public Result getNotifications(){
        return TODO;
    }
}
