package controllers;

import controllers.ApplicationSystem.routes;
import models.ApplicationSystem.EthicsApplication;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
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
        return redirect(controllers.routes.HomeController.about());
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

    /**
     * Shows the about committee page
     * @return
     */
    public Result about(){
        return ok(views.html.General.About.render());
    }

    /**
     * Shows the help and support page
     * @return
     */
    public Result helpSupport(){
        return ok(views.html.General.HelpSupport.render());
    }

    /**
     * Generates controller javascript routes
     * @return
     */
    public Result javascriptRoutes(){
        return ok(
                JavaScriptReverseRouter.create("homeRoutes",
                        controllers.routes.javascript.HomeController.about(),
                        controllers.routes.javascript.HomeController.helpSupport(),
                        controllers.routes.javascript.HomeController.index()
                        )
        ).as("text/javascript");
    }

    public Result invalid(String url) {
        flash("danger", "Sorry, the page does not exist");
        return notFound(views.html.General.Invalid.render(" :: Not Found", url));
    }
}
