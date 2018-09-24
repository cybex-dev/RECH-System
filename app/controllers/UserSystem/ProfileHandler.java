package controllers.UserSystem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.APIController;
import controllers.NotificationSystem.Notifier;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.UserSystem.EntityPerson;
import helpers.CookieTags;
import helpers.FileScanner;
import models.UserSystem.Application;
import models.UserSystem.UserType;
import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;
import org.mindrot.jbcrypt.BCrypt;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.routing.JavaScriptReverseRouter;

import javax.inject.Inject;
import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Security.Authenticated(Secured.class)
public class ProfileHandler extends Controller {

    static Map<UserType, String> enrolmentMap = new HashMap<>();

    @Inject
    private FormFactory formFactory;

    public ProfileHandler() {
    }

    public ProfileHandler(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Gets an overview of applications dependant on user type
     *
     * <ul>
     * <li>PI: all application statuses</li>
     * <li>PRP : all own applications and related PI's applications</li>
     * <li>Reviewer|Liaison|HOD|RTI : all own applications, related PI applications and applications to review</li>
     * <li>No applications shown. Shown data such as meeting dates, quanity applications to be reviewed with title, etc</li>
     * </ul>
     *
     * @return
     */
    public Result overview() {
        EntityPerson person = EntityPerson.getPersonById(session().get(CookieTags.user_id));
        List<EntityEthicsApplication> entity_newApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.RCD);
        List<EntityEthicsApplication> entity_ownApplications = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.PrimaryInvestigator);
        List<EntityEthicsApplication> entity_approveApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.PrimaryResponsiblePerson);
        List<EntityEthicsApplication> entity_reviewApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.Reviewer);
        List<EntityEthicsApplication> entity_liaiseApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.Liaison);
        List<EntityEthicsApplication> entity_facultyApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.FacultyRTI);

        return ok(views.html.UserSystem.Dashboard.render(
                entity_newApps.stream().map(app -> Application.create(app, person.userType())).collect(Collectors.toList()),
                entity_ownApplications.stream().map(app -> Application.create(app, person.userType())).collect(Collectors.toList()),
                entity_approveApps.stream().map(app -> Application.create(app, person.userType())).collect(Collectors.toList()),
                entity_reviewApps.stream().map(app -> Application.create(app, person.userType())).collect(Collectors.toList()),
                entity_liaiseApps.stream().map(app -> Application.create(app, person.userType())).collect(Collectors.toList()),
                entity_facultyApps.stream().map(app -> Application.create(app, person.userType())).collect(Collectors.toList()))
        );
    }

    /**
     * Retrieves all profile information about an individual
     *
     * @return
     */
    public Result settings() {
        EntityPerson personById = EntityPerson.getPersonById(session(CookieTags.user_id));
        if (personById == null) {
            flash("info", "Please login again");
            return redirect(routes.LoginController.logout());
        }
        return ok(views.html.UserSystem.Settings.render(personById));
    }

    /**
     * @return
     */
    @RequireCSRFCheck
    public Result updateBasicInfo() {
        JsonNode json = request().body().asJson();
        String userId = json.findPath("user_id").textValue();
        if (userId == null || userId.isEmpty()) {
            flash("info", "User not found, please login again");
            return badRequest();
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("info", "User not found, please login again");
            return notFound();
        }

        String title = json.findPath("title").textValue();
        String firstname = json.findPath("firstname").textValue();
        String lastname = json.findPath("lastname").textValue();
        String mobile = json.findPath("mobile").textValue();
        String degree = json.findPath("degree").textValue();
        String faculty = json.findPath("faculty").textValue();
        String department = json.findPath("department").textValue();

        personById.setUserTitle(title);
        personById.setUserFirstname(firstname);
        personById.setUserLastname(lastname);
        personById.setContactNumberMobile(mobile);
        personById.setCurrentDegreeLevel(degree);
        personById.setFacultyName(faculty);
        personById.setDepartmentName(department);
        personById.update();

        flash("success", "Basic information updated");
        return ok();
    }

    @RequireCSRFCheck
    public Result updateAcademicInfo() {
        JsonNode json = request().body().asJson();
        String userId = json.findPath("user_id").textValue();
        if (userId == null || userId.isEmpty()) {
            flash("info", "User not found, please login again");
            return badRequest();
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("info", "User not found, please login again");
            return notFound();
        }

        String telephone = json.findPath("telephone").textValue();
        String address = json.findPath("address").textValue();
        String campus = json.findPath("campus").textValue();

        personById.setOfficeCampus(campus);
        personById.setContactOfficeTelephone(telephone);
        personById.setOfficeAddress(address);
        personById.update();

        flash("success", "Academic information updated");
        return ok();
    }

    @RequireCSRFCheck
    public Result updatePassword() {
        JsonNode json = request().body().asJson();
        String userId = json.findPath("user_id").textValue();
        if (userId == null || userId.isEmpty())
            return badRequest();
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null)
            return notFound();

        String old_password = json.findPath("old_password").textValue();
        if (!personById.authenticate(old_password)) {
            flash("danger", "Incorrect existing password");
            return notFound();
        }

        String newPassword = json.findPath("new_password").textValue();
        String confirmPassword = json.findPath("confirm_password").textValue();
        if (!newPassword.equals(confirmPassword)) {
            flash("danger", "Passwords mismatch, try again");
            return badRequest();
        }

        Notifier.changedPassword(personById);
        flash("success", "Password changed");
        return ok();
    }

    public Result sendForgotPassword(){
        JsonNode node = request().body().asJson();
        node.findPath("email");

        flash("success", "Password reset sent");
        return ok();
    }

    public Result changePassword(String token){
        session("change", token);
        return ok(views.html.UserSystem.ResetPassword.render());
    }

    @RequireCSRFCheck
    public Result doChangePassword(){
        if (session().get("change").isEmpty()){
            flash("danger", "Invalid session, please try again!");
            return badRequest();
        }

        JsonNode node = request().body().asJson();
        String password = node.findPath("password").textValue();
        String confirmPassword = node.findPath("confirm_password").textValue();

        if (!confirmPassword.equals(password)) {
            flash("danger", "Passwords do not match, please try again");
            return badRequest();
        }

        String token = session().get("token");

        List<EntityPerson> collect = EntityPerson.find.all()
                .stream()
                .filter(person -> person.getUserPasswordHash().substring(12).equals(token))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            flash("danger", "No user found, please register again");
            return notFound();
        }

        if (collect.size() > 2) {
            flash("danger", "An error occurred, please register again!");
            return badRequest();
        }

        EntityPerson person = collect.get(0);
        String newPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        person.setUserPasswordHash(newPassword);

        flash("succcess", "New password updated, please login in.");
        return redirect(routes.LoginController.login());
    }

    /**
     * Generates controller javascript routes
     *
     * @return
     */
    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("userRoutes",
                        routes.javascript.ProfileHandler.overview(),
                        routes.javascript.ProfileHandler.settings(),

                        routes.javascript.ProfileHandler.updatePassword(),
                        routes.javascript.ProfileHandler.updateAcademicInfo(),
                        routes.javascript.ProfileHandler.updateBasicInfo(),
                        routes.javascript.ProfileHandler.doEnrol()
                )
        ).as("text/javascript");
    }

    @RequireCSRFCheck
    public Result doEnrol() {
        JsonNode json = request().body().asJson();
        String userId = json.findPath("user_id").textValue();
        if (userId == null || userId.isEmpty())
            return badRequest();
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null)
            return notFound();

        String type = json.findPath("position").textValue();
        String code = json.findPath("code").textValue();
        if (type == null || type.isEmpty() ||
                code == null || code.isEmpty()) {
            flash("info", "User not found, please login again");
            return badRequest();
        }

        UserType userType = UserType.parse(type);

        if (enrolmentMap.isEmpty()) {
            try {
                Element keysXml = XMLTools.parseDocument(new File(APIController.class.getResource("enrolment.xml").toURI()));
                XMLTools.flatten(keysXml, false).forEach(s -> {
                    Element lookup = XMLTools.lookup(keysXml, s);
                    UserType t = UserType.parse(s);
                    enrolmentMap.put(t, lookup.getValue().toString());
                });

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        Map.Entry<UserType, String> enrol_code = enrolmentMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(userType) && entry.getValue().equals(code))
                .findFirst()
                .orElse(null);
        if (enrol_code == null) {
            flash("danger", "Invalid enrolment key");
            return notFound();
        }

        if (personById.userType().equals(enrol_code.getKey())) {
            flash("danger", "You are already a " + userType.getDescription());
            return badRequest();
        }

        personById.setPersonType(enrol_code.getKey().toString());
        personById.update();
        session().remove(CookieTags.user_type);
        session(CookieTags.user_type, enrol_code.getKey().toString());

        Notifier.enrolledUser(enrol_code.getKey(), personById.getUserEmail());

        flash("success", "You are now a " + enrol_code.getKey().getDescription());
        return ok();
    }

    public static List<String> getEnrolmentPositions(){
        return enrolmentMap.entrySet().stream().map(entry -> entry.getKey().toString()).collect(Collectors.toList());
    }
}
