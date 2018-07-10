package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Index extends Controller {

    public Result ampOOO() {
        return ok(views.html.ampOOO.render());
    }
}
