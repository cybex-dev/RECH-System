package helpers;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class NotifierExecutor extends CustomExecutionContext {
    @Inject
    public NotifierExecutor(ActorSystem actorSystem) {
        // uses a custom thread pool defined in application.conf
        super(actorSystem, "akka.actor.notifier-dispatcher");
    }
}