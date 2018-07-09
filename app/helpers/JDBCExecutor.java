package helpers;

import javax.inject.Inject;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

public class JDBCExecutor extends CustomExecutionContext {
    @Inject
    public JDBCExecutor(ActorSystem actorSystem) {
        // uses a custom thread pool defined in application.conf
        super(actorSystem, "my.dispatcher");
    }
}
