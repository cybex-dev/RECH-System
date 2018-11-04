package controllers.UserSystem;

import controllers.NotificationSystem.Notifier;
import dao.NMU.EntityDepartment;
import dao.NMU.EntityFaculty;
import dao.UserSystem.EntityPerson;
import helpers.CookieTags;
import helpers.Mailer;
import models.UserSystem.BasicRegistrationForm;
import models.UserSystem.DetailedRegistrationForm;
import models.UserSystem.UserRegistrationForm;
import models.UserSystem.UserType;
import org.mindrot.jbcrypt.BCrypt;
import play.api.mvc.Cookie;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Basic;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;


public class RegistrationController extends Controller {

    private FormFactory formFactory;

    @Inject
    public RegistrationController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public RegistrationController() {
    }

    public Result registerInitial(){
        Form<BasicRegistrationForm> form = formFactory.form(BasicRegistrationForm.class);
        return ok(views.html.UserSystem.RegisterBasic.render(form));
    }

    public Result doRegisterInitial(){
        Form<BasicRegistrationForm> form = formFactory.form(BasicRegistrationForm.class).bindFromRequest();

        if (form.hasErrors()) {
            flash("warning", "Please check all fields are filled in!");
            return badRequest(views.html.UserSystem.RegisterBasic.render(form));
        }

        BasicRegistrationForm basicRegistrationForm = form.get();

        if (EntityPerson.getPersonById(basicRegistrationForm.getEmail()) != null) {
            flash("warning", "Account with email " + basicRegistrationForm.getEmail() + " already exists!");
            return badRequest(views.html.UserSystem.RegisterBasic.render(form));
        }

        if (!basicRegistrationForm.getPassword().equals(basicRegistrationForm.getConfirm_password())){
            flash("warning", "Passwords do not match.");
            return badRequest(views.html.UserSystem.RegisterBasic.render(form));
        }

        EntityPerson person = new EntityPerson();
        person.setUserEmail(basicRegistrationForm.getEmail());
        String hashpw = BCrypt.hashpw(basicRegistrationForm.getPassword(), BCrypt.gensalt(12));
        person.setUserPasswordHash(hashpw);
        person.setPersonType(UserType.PrimaryInvestigator.getType());
        person.setUserFirstname("");
        person.setUserLastname("");
        person.insert();

        String code = hashpw.replace("/", "").replace(".", "").substring(12);

        Notifier.sendVerification(person.getUserEmail(), code);
        flash("info", "An verification email has been sent to " + basicRegistrationForm.getEmail() + ". Please check your mail and verify your email address.");
        return redirect(routes.LoginController.login());
    }

    public Result verify(String token){
        List<EntityPerson> collect = EntityPerson.find.all()
                .stream()
                .filter(person -> person.getUserPasswordHash().replace("/", "").replace(".", "").substring(12).equals(token))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            flash("danger", "No user found, please register again");
            return notFound();
        }

        if (collect.size() > 2) {
            flash("danger", "An error occurred, please register again!");
            return badRequest();
        }

        EntityPerson p = collect.get(0);

        if (p.getIsVerified()) {
            flash("info", "Your account has already been verified. Please login in.");
            return redirect(routes.LoginController.login());
        }

        session().put(CookieTags.user_id, p.getUserEmail());
        session().put(CookieTags.token, p.getUserPasswordHash());
        session().put(CookieTags.user_type, p.getPersonType());
        p.setIsVerified(true);
        p.update();

        flash("info", "Please complete your registration");
        return redirect(routes.RegistrationController.registerFinal());
    }

    /**
     * Provides a form to allow a user to register
     * @return
     */
    public Result registerFinal(){
        String userId = session().get(CookieTags.user_id);
        String token = session().get(CookieTags.token);
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (!personById.getUserPasswordHash().equals(token)){
            flash("danger", "An error occurred, please login again.");
            return redirect(routes.LoginController.login());
        }

        Form<DetailedRegistrationForm> form = formFactory.form(DetailedRegistrationForm.class);

        return ok(views.html.UserSystem.RegisterStep2.render(form, EntityDepartment.getAllDepartmentNames(), EntityFaculty.getAllFacultyNames()));
    }

    /**
     * Receive data for the user to register on the system
     * @return
     */
    public Result submitRegistration(){
        Form<DetailedRegistrationForm> userRegistrationFormForm = formFactory.form(DetailedRegistrationForm.class).bindFromRequest();
        if (userRegistrationFormForm.hasErrors()) {
            flash("warning", "Check input fields");
            return badRequest(views.html.UserSystem.RegisterStep2.render(userRegistrationFormForm, EntityDepartment.getAllDepartmentNames(), EntityFaculty.getAllFacultyNames()));
        }

        DetailedRegistrationForm registrationForm = userRegistrationFormForm.get();
        String email = session().get(CookieTags.user_id);

        EntityPerson person = EntityPerson.getPersonById(email);

        person.setUserTitle(registrationForm.getUsertitle());
        person.setUserFirstname(registrationForm.getFirstname());
        person.setUserLastname(registrationForm.getLastname());
        person.setCurrentDegreeLevel(registrationForm.getDegreeLevel());
        person.setPersonType(UserType.PrimaryInvestigator.getType());
        person.setContactNumberMobile(registrationForm.getMobile());
        person.setDepartmentName(registrationForm.getDepartment());
        person.setFacultyName(registrationForm.getFaculty());
        person.update();

        Notifier.newUser(person);
        session().clear();
        session(CookieTags.user_id, person.getUserEmail());
        session(CookieTags.fullname, person.getUserFirstname() + " " + person.getUserLastname());
        session(CookieTags.user_type, person.getPersonType());

        flash("success", "Registration complete");
        return redirect(routes.ProfileHandler.overview());
    }
}
