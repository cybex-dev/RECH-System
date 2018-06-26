package controllers;

import play.mvc.*;

import views.html.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */

import scala.collection.JavaConverters;

import static scala.collection.JavaConverters.asScalaBuffer;

public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render());
    }

    public Result viewJson() {
        List<String> list = Arrays.asList("See actor system", "home page", "login page");
        return ok(json.render("Welcome to play", 3, asScalaBuffer(list)));
    }

    public Result base(){
        return ok();
    }

}
