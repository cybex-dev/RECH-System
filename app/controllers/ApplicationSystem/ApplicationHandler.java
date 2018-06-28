package controllers.ApplicationSystem;

import akka.stream.javadsl.FileIO;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.*;

import javax.inject.Inject;
import java.util.concurrent.Future;

public class ApplicationHandler extends Controller {

    @Inject
    HttpExecutionContext executionContext;

    @Inject
    FormFactory formFactory;


    // Uses XML document to create form, based in as Scala Squence type and processed in view
    public ApplicationHandler() {

    }
}
