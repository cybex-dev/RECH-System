package controllers.UserSystem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import play.libs.Json;
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

    private void loadEnrolmentKeys() {
        if (enrolmentMap.isEmpty()) {
            try {
                List<File> resourceFiles = new FileScanner().getResourceFiles("/enrolment/");
                if (resourceFiles.size() == 1) {
                    File file = resourceFiles.get(0);
                    Element keysXml = XMLTools.parseDocument(file);
                    XMLTools.flatten(keysXml, false).forEach(s -> {
                        Element lookup = XMLTools.lookup(keysXml, s);
                        UserType t = UserType.parse(s);
                        enrolmentMap.put(t, lookup.getValue().toString());
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
     * <li>Reviewer|Liaison|HOD|RTI : all own applications, related PI applications and applications to reviewable</li>
     * <li>No applications shown. Shown data such as meeting dates, quanity applications to be reviewed with title, etc</li>
     * </ul>
     *
     * @return
     */
    public Result overview() {
        EntityPerson person = EntityPerson.getPersonById(session().get(CookieTags.user_id));
        if (person == null) {
            flash("danger", "An error occurred, please login again");
            session().clear();
            return redirect(routes.LoginController.login());
        }

        String username = session().get(CookieTags.fullname);
        if ((username == null || username.isEmpty()) &&
                !session().get(CookieTags.user_type).equals(UserType.RCD.getType())) {
            flash("warning", "Please complete your account information. Go to Settings > Personal & Academic Information");
        }

        List<EntityEthicsApplication> entity_newApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.RCD);
        List<EntityEthicsApplication> entity_ownApplications = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.PrimaryInvestigator);
        List<EntityEthicsApplication> entity_approveApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.PrimaryResponsiblePerson);
        List<EntityEthicsApplication> entity_reviewApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.Reviewer);
        List<EntityEthicsApplication> entity_liaiseApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.Liaison);
        List<EntityEthicsApplication> entity_facultyApps = EntityEthicsApplication.findApplicationsByPerson(person.getUserEmail(), UserType.FacultyRTI);

        List<Application> collect = entity_newApps.stream().map(app -> Application.create(app, person)).filter(Application::hasRecentActivity).collect(Collectors.toList());
        List<Application> collect1 = entity_ownApplications.stream().map(app -> Application.create(app, person)).filter(Application::hasRecentActivity).collect(Collectors.toList());
        List<Application> collect2 = entity_approveApps.stream().map(app -> Application.create(app, person)).filter(Application::hasRecentActivity).collect(Collectors.toList());
        List<Application> collect3 = entity_reviewApps.stream().map(app -> Application.create(app, person)).filter(Application::hasRecentActivity).collect(Collectors.toList());
        List<Application> collect4 = entity_liaiseApps.stream().map(app -> Application.create(app, person)).filter(Application::hasRecentActivity).collect(Collectors.toList());
        List<Application> collect5 = entity_facultyApps.stream().map(app -> Application.create(app, person)).filter(Application::hasRecentActivity).collect(Collectors.toList());

        return ok(views.html.UserSystem.Dashboard.render(collect, collect1, collect2, collect3, collect4, collect5));
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
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        String userId = session().get("user_id");
        if (userId == null || userId.isEmpty()) {
            flash("danger", "Invalid session, please login again!");
            result.put("danger", "Invalid session, please login again!");
            return badRequest();
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("danger", "An error occurred, please login again!");
            result.put("danger", "An error occurred, please login again!");
            return notFound();
        }

        String title = json.findPath("title").textValue();
        String firstname = json.findPath("firstname").textValue();
        String lastname = json.findPath("lastname").textValue();
        String mobile = json.findPath("mobile").textValue();
        String degree = json.findPath("degree").textValue();
        String faculty = json.findPath("faculty").textValue();
        String department = json.findPath("department").textValue();

        if (!title.isEmpty())
            personById.setUserTitle(title);
        if (!firstname.isEmpty())
            personById.setUserFirstname(firstname);
        if (!lastname.isEmpty())
            personById.setUserLastname(lastname);
        if (!mobile.isEmpty())
            personById.setContactNumberMobile(mobile);
        if (!degree.isEmpty())
            personById.setCurrentDegreeLevel(degree);
        if (!faculty.isEmpty())
            personById.setFacultyName(faculty);
        if (!department.isEmpty())
            personById.setDepartmentName(department);
        personById.update();

        flash("success", "Basic information updated");
        result.put("success", "Basic information updated");
        return ok();
    }

    @RequireCSRFCheck
    public Result updateAcademicInfo() {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        String userId = session().get("user_id");
        if (userId == null || userId.isEmpty()) {
            flash("danger", "Invalid session, please login again!");
            result.put("danger", "Invalid session, please login again!");
            return badRequest();
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("danger", "An error occurred, please login again!");
            result.put("danger", "An error occurred, please login again!");
            return notFound();
        }

        String telephone = json.findPath("telephone").textValue();
        String address = json.findPath("address").textValue();
        String campus = json.findPath("campus").textValue();

        if (!telephone.isEmpty())
            personById.setContactOfficeTelephone(telephone);
        if (!address.isEmpty())
            personById.setOfficeAddress(address);
        if (!campus.isEmpty())
            personById.setOfficeCampus(campus);
        personById.update();

        flash("success", "Academic information updated");
        result.put("success", "Academic information updated");
        return ok();
    }

    @RequireCSRFCheck
    public Result updatePassword() {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        String userId = session().get("user_id");
        if (userId == null || userId.isEmpty()) {
            flash("danger", "Invalid session, please login again!");
            result.put("danger", "Invalid session, please login again!");
            return badRequest(result);
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("danger", "An error occurred, please login again!");
            result.put("danger", "An error occurred, please login again!");
            return notFound();
        }
        String old_password = json.findPath("old_password").textValue();
        if (!personById.authenticate(old_password)) {
            flash("danger", "Incorrect existing password");
            result.put("danger", "Incorrect existing password");
            return notFound();
        }

        String newPassword = json.findPath("new_password").textValue();
        String confirmPassword = json.findPath("confirm_password").textValue();
        if (!newPassword.equals(confirmPassword)) {
            flash("danger", "Passwords mismatch, try again");
            result.put("danger", "Passwords mismatch, try again");
            return badRequest();
        }
        personById.setUserPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
        personById.update();

        Notifier.changedPassword(personById);
        flash("success", "Password changed");
        result.put("success", "Password changed");
        return ok();
    }

    public Result sendForgotPassword() {
        JsonNode node = request().body().asJson();
        node.findPath("email");

        flash("success", "Password reset sent");
        return ok();
    }

    public Result changePassword(String token) {
        session("change", token);
        return ok(views.html.UserSystem.ResetPassword.render());
    }

    @RequireCSRFCheck
    public Result doChangePassword() {
        if (session().get("change").isEmpty()) {
            flash("danger", "Invalid session, please try again!");
            return badRequest();
        }

        JsonNode node = request().body().asJson();
        String password = node.findPath("new_password").textValue();
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
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        String userId = session().get("user_id");
        if (userId == null || userId.isEmpty()) {
            flash("danger", "Invalid session, please login again!");
            result.put("danger", "Invalid session, please login again!");
            return badRequest();
        }
        EntityPerson personById = EntityPerson.getPersonById(userId);
        if (personById == null) {
            flash("danger", "An error occurred, please login again!");
            result.put("danger", "An error occurred, please login again!");
            return notFound();
        }

        String type = json.findPath("enrol_type").textValue();
        String code = json.findPath("enrol_code").textValue();
        if (type == null || type.isEmpty() ||
                code == null || code.isEmpty()) {
            flash("info", "User not found, please login again");
            result.put("info", "User not found, please login again");
            return badRequest();
        }

        UserType userType = UserType.parse(type);

        Map.Entry<UserType, String> enrol_code = enrolmentMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(userType) && entry.getValue().equals(code))
                .findFirst()
                .orElse(null);
        if (enrol_code == null) {
            flash("danger", "Invalid enrolment key");
            result.put("danger", "Invalid enrolment key");
            return notFound();
        }

        if (personById.userType().equals(enrol_code.getKey())) {
            flash("danger", "You are already a " + userType.getDescription());
            result.put("danger", "You are already a " + userType.getDescription());
            return badRequest();
        }

        personById.setPersonType(enrol_code.getKey().toString());
        personById.update();
        session().remove(CookieTags.user_type);
        session(CookieTags.user_type, enrol_code.getKey().toString());

        Notifier.enrolledUser(enrol_code.getKey(), personById.getUserEmail());

        flash("success", "You are now a " + enrol_code.getKey().getDescription());
        result.put("success", "You are now a " + enrol_code.getKey().getDescription());
        return ok();
    }

    public static List<String> getEnrolmentPositions() {
        ProfileHandler profileHandler = new ProfileHandler();
        profileHandler.loadEnrolmentKeys();
        return enrolmentMap.entrySet().stream().map(entry -> entry.getKey().toString()).collect(Collectors.toList());
    }
}
