package actors;

import akka.actor.AbstractActor;

public class NotificationActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return null;
    }

    // Will notify a client actor if a new message / notification appears, will also query database

    // TODO
    // create props factor
    // https://www.youtube.com/watch?v=r4dryMdDZz0



}
