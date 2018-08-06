package controllers.UserSystem;

import dao.UserSystem.EntityPerson;
import models.UserSystem.UserRegistrationForm;
import models.UserSystem.UserType;
import org.mindrot.jbcrypt.BCrypt;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class RegistrationController extends Controller {

    private FormFactory formFactory;

    @Inject
    public RegistrationController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public RegistrationController() {
    }

    /**
     * Provides a form to allow a user to register
     * @return
     */
    public Result register(){
        Form<UserRegistrationForm> registrationForm = formFactory.form(UserRegistrationForm.class);
        return ok(views.html.UserSystem.Register.apply(registrationForm));
    }

    /**
     * Receive data for the user to register on the system
     * @return
     */
    public Result submitRegistration(){
        Form<UserRegistrationForm> userRegistrationFormForm = formFactory.form(UserRegistrationForm.class).bindFromRequest();
        if (userRegistrationFormForm.hasErrors()) {
            flash("error", "Check input fields");
            return badRequest(views.html.UserSystem.Register.apply(userRegistrationFormForm));
        }

        UserRegistrationForm userRegistrationForm = userRegistrationFormForm.get();

        EntityPerson person = new EntityPerson();
        person.setUserEmail(userRegistrationForm.getEmail());
        person.setUserTitle(userRegistrationForm.getTitle());
        person.setUserFirstname(userRegistrationForm.getFirstname());
        person.setUserLastname(userRegistrationForm.getLastname());
        person.setCurrentDegreeLevel(userRegistrationForm.getDegreeLevel());
        person.setPersonType(UserType.PrimaryInvestigator.getType());
        person.setUserGender(userRegistrationForm.getGender());
        person.setUserPasswordHash(BCrypt.hashpw(userRegistrationForm.getPassword(), BCrypt.gensalt(12)));
        person.setContactNumberMobile(userRegistrationForm.getMobile());
        person.insert();

        flash("message", "Please login to proceed");
        return redirect(routes.LoginController.login());
    }
}
