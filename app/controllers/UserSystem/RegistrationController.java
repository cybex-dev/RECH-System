package controllers.UserSystem;

import controllers.NotificationSystem.Notifier;
import dao.NMU.EntityDepartment;
import dao.NMU.EntityFaculty;
import dao.UserSystem.EntityPerson;
import helpers.Mailer;
import models.UserSystem.UserRegistrationForm;
import models.UserSystem.UserType;
import org.mindrot.jbcrypt.BCrypt;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

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
        return ok(views.html.UserSystem.Register.render(registrationForm, EntityDepartment.getAllDepartmentNames(), EntityFaculty.getAllFacultyNames()));
    }

    /**
     * Receive data for the user to register on the system
     * @return
     */
    public Result submitRegistration(){
        Form<UserRegistrationForm> userRegistrationFormForm = formFactory.form(UserRegistrationForm.class).bindFromRequest();
        if (userRegistrationFormForm.hasErrors()) {
            flash("error", "Check input fields");
            return badRequest(views.html.UserSystem.Register.render(userRegistrationFormForm, EntityDepartment.getAllDepartmentNames(), EntityFaculty.getAllFacultyNames()));
        }

        UserRegistrationForm userRegistrationForm = userRegistrationFormForm.get();

        if (EntityPerson.getPersonById(userRegistrationForm.getEmail()) != null) {
            flash("error", "Account with email " + userRegistrationForm.getEmail() + " already exists!");
            return badRequest(views.html.UserSystem.Register.render(userRegistrationFormForm, EntityDepartment.getAllDepartmentNames(), EntityFaculty.getAllFacultyNames()));
        }

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
        person.setDepartmentName(userRegistrationForm.getDepartment());
        person.setFacultyName(userRegistrationForm.getFaculty());
        person.insert();

        // Send welcome email
        Mailer.SendWelcome(person.getUserFirstname(), person.getUserEmail()).thenApply(aBoolean -> {
            flash("message", "Please login to proceed");
            if (!aBoolean) {
                flash("error", "Unable to send welcome message");
            }
            return null;
        });
        return redirect(routes.LoginController.login());
    }
}
