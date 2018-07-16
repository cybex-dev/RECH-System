package controllers;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Templates.Masterpage;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */

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
        return ok(Masterpage.render(null,null));
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
    public Result postNMUSearch() {
        DynamicForm nmu_form = formFactory.form().bindFromRequest();
        String searchPhrase = nmu_form.get("p$lt$zoneSearchBox$SearchBox$txtWord");
        if (searchPhrase == null)
            searchPhrase = "";
        return redirectMandelaSecure("Search.aspx?searchtext=" + searchPhrase  + "&searchmode=anyword");
    }
}
