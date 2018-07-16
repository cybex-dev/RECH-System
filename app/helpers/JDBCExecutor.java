package helpers;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class JDBCExecutor extends CustomExecutionContext {
    @Inject
    public JDBCExecutor(ActorSystem actorSystem) {
        // uses a custom thread pool defined in application.conf
        super(actorSystem, "akka.actor.jdbc-dispatcher");
    }
}