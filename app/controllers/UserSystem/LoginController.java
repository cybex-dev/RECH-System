package controllers.UserSystem;

import controllers.NotificationSystem.Notifier;
import dao.NMU.EntityDepartment;
import dao.NMU.EntityFaculty;
import dao.UserSystem.EntityPerson;
import helpers.CookieTags;
import models.UserSystem.BasicRegistrationForm;
import models.UserSystem.UserLoginForm;
import models.UserSystem.UserRegistrationForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.UserSystem.Login;

import javax.inject.Inject;

public class LoginController extends Controller{

    private FormFactory formFactory;

    public LoginController() {
    }

    @Inject
    public LoginController(final FormFactory formFactory){
        this.formFactory = formFactory;
    }

    /**
     * Provides login for to login into system
     *
     * Usecase: B0002
     * @return
     */
    public Result login(){
        if (isLoggedIn()) {
            return redirect(routes.ProfileHandler.overview());
        }
        Form<UserLoginForm> loginFormForm = formFactory.form(UserLoginForm.class);
        return ok(Login.render(loginFormForm));
    }

    /**
     * Accepts login credentials and assigns token for login session
     *
     * Usecase: B0002
     * @return
     */
    public Result doLogin(){
        Form<UserLoginForm> loginFormForm = formFactory.form(UserLoginForm.class).bindFromRequest();

        // Check for errors
        if (loginFormForm.hasErrors()){
            flash("warning", "Incomplete details, check fields.");
            return badRequest(Login.render(loginFormForm));
        }

        UserLoginForm loginData = loginFormForm.get();
        if (!EntityPerson.authenticate(loginData.getEmail(), loginData.getPassword())) {
            flash("danger", "Incorrect username or password, please try again.");
            return badRequest(Login.render(loginFormForm));
        }

        EntityPerson person = EntityPerson.getPersonById(loginData.getEmail());
        if (!person.getIsVerified()) {
            flash("warning", "Please verify your account before logging in. New verification email sent to " + person.getUserEmail());
            String code = person.getUserPasswordHash().replace("/", "").replace(".", "").substring(12);
            Notifier.sendVerification(person, code);
            return unauthorized(Login.render(loginFormForm));
        }

        session().clear();
        session(CookieTags.user_id, loginData.getEmail());
        session(CookieTags.fullname, person.getUserFirstname() + " " + person.getUserLastname());
        session(CookieTags.user_type, person.getPersonType());

        return redirect(routes.ProfileHandler.overview());
    }

    /**
     * Deletes session and clears cookies, which logs out the user
     * @return
     */
    @Security.Authenticated(Secured.class)
    public Result logout(){
        session().clear();
        return ok(views.html.General.About.render());
    }

    /**
     * Renders page for user to register, is user is logged in, redirects to overview page
     *
     * Usecase B0003
     * @return
     */
    public Result register() {
        Form<BasicRegistrationForm> form = formFactory.form(BasicRegistrationForm.class);
        if (!isLoggedIn()) {
            flash("info", "You are already logged in");
            return ok(views.html.UserSystem.RegisterBasic.render(form));
        } else {
            return redirect(routes.ProfileHandler.overview());
        }
    }

    private boolean isLoggedIn() {
        return Secured.isLoggedIn(ctx());
    }

    public Result forgotPassword() {
        return TODO;
    }

    public Result enrol(){
        return TODO;
    }

    public Result doEnrol(){
        flash("info", "No implemented yet");
        return TODO;
    }
}
