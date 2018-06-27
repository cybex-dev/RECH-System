package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import views.html.nmu_homepage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */

import scala.collection.JavaConverters;

import javax.inject.Inject;

public class HomeController extends Controller {

    @Inject
    FormFactory formFactory;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(nmu_homepage.render(null));
    }
    public Result base(){
        return ok();
    }

    /**
     * Redirects to mandela.ac.za via HTTPS secure
     * Only provide method and query in form, omit leading / <br>
     *
     * <br>DO:
     * <code>method.aspx?v=1</code>
     * <br>
     * <br>DON'T:
     * <code>/method.aspx?v=1</code>
     *
     * @param link
     * @return
     */
    public Result redirectMandelaSecure(String link) {
        return redirect("https://www.mandela.ac.za/" + link);
    }

    /**
     * Redirects to mandela.ac.za via HTTP insecure
     * Only provide method and query in form, omit leading / <br>
     *
     * <br>DO:
     * <code>method.aspx?v=1</code>
     * <br>
     * <br>DON'T:
     * <code>/method.aspx?v=1</code>
     *
     * @param link
     * @return
     */
    public Result redirectMandela(String link) {
        return redirect("http://www.mandela.ac.za/" + link);
    }

    /**
     * Posts to mandela.ac.za via HTTPS secure
     * @return
     */
    public Result postMandelaSecure() {
        DynamicForm nmu_form = formFactory.form().bindFromRequest();
        String searchPhrase = nmu_form.get("p$lt$zoneSearchBox$SearchBox$txtWord");
        if (searchPhrase == null)
            searchPhrase = "";
        return redirectMandelaSecure("Search.aspx?searchtext=" + searchPhrase  + "&searchmode=anyword");
    }

    /**
     * Posts to mandela.ac.za via HTTP insecure
     * @return
     */
    public Result postMandela() {
        Http.Request request = request();
        Http.Session session = session();
        return ok();
    }

}
