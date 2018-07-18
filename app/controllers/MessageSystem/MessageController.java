package controllers.MessageSystem;

import play.mvc.Result;

import static play.mvc.Results.TODO;

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
