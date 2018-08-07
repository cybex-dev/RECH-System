package controllers.MessageSystem;

import controllers.UserSystem.Secured;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Results.TODO;

@Security.Authenticated(Secured.class)
public class MessageController {

    /**
     * Gets all messages for all applications
     * @return
     */
    public Result allMessages(){
        return TODO;
    }

    /**
     *  Submits a message for a specific application, which is seen by PI, PRP, Liaison
     * @param id
     * @return
     */
    public Result applicationMessages(Integer id){
        return TODO;
    }

}
