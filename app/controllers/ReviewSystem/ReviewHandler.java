package controllers.ReviewSystem;

import controllers.ApplicationSystem.ApplicationHandler;
import controllers.UserSystem.Secured;
import controllers.UserSystem.routes;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.ReviewSystem.EntityReviewerApplications;
import dao.UserSystem.EntityPerson;
import engine.RECEngine;
import helpers.CookieTags;
import models.ApplicationSystem.ApplicationStatus;
import models.UserSystem.Application;
import net.ddns.cyberstudios.Element;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Security.Authenticated(Secured.class)
public class ReviewHandler extends Controller {

    @Inject
    private FormFactory formFactory;

    public ReviewHandler(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Provides the latest application and feedback, allows edits only if {@link RECEngine} allows it.
     * @param applicationID
     * @return
     */
    public Result reviewApplication(String applicationID) {
        String email = session().get(CookieTags.user_id);

        EntityEthicsApplication ethicsApplication = EntityEthicsApplication.find.byId(EntityEthicsApplicationPK.fromString(applicationID));
        if (ethicsApplication == null) {
            flash("danger", "Could not find application");
            return internalServerError("Could not find application");
        }
        List<String> allReviewers = ethicsApplication.findAllReviewers();
        if (allReviewers.stream().filter(s -> s.equals(email)).collect(Collectors.toList()).size() != 1) {
            flash("danger", "You are not authorized to review this application");
            return unauthorized("You are not authorized to review this application");
        }

        Element element = ApplicationHandler.PopulateRootElement(ethicsApplication);
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Edit Application", ethicsApplication.getApplicationType(), element, ApplicationStatus.parse(ethicsApplication.getInternalStatus()), null, false, controllers.ReviewSystem.routes.ReviewHandler.submitReview(), false));
    }

    /**
     * Submit revised application and component data only if {@link RECEngine} allows it
     * @return
     */
    public Result submitReview() {
        return TODO;
    }

    public Result assignReviewer(String applicationId){
        EntityEthicsApplication application = EntityEthicsApplication.findByShortName(applicationId);
        if (application == null){
            flash("danger", "Application could not be found");
            return internalServerError();
        }

        Application app = Application.create(application);
        List<EntityPerson> rawReviewers = EntityPerson.findAllReviewers();
        String prp = application.getPrpId();
        String pi = application.getPiId();
        List<EntityPerson> reviewers = rawReviewers.stream()
                .filter(entityPerson -> !entityPerson.getUserEmail().equals(prp) && !entityPerson.getUserEmail().equals(pi))
                .collect(Collectors.toList());
        return ok(views.html.ReviewSystem.AssignApplication.render(app, reviewers));
    }

    public Result doAssignReviewers(){
        DynamicForm form = formFactory.form().bindFromRequest();

        if (form.hasGlobalErrors()){
            flash("danger", "Check all reviewers have been assigned");
            return assignReviewer(form.get("application_id"));
        }

        EntityEthicsApplicationPK application_id = EntityEthicsApplicationPK.fromString(form.get("application_id"));
        String reviewer1 = form.get("reviewer1");
        String reviewer2 = form.get("reviewer2");
        String reviewer3 = form.get("reviewer3");
        String reviewer4 = form.get("reviewer4");

        Timestamp ts = Timestamp.from(Instant.now());
        for (String reviewer: new String[]{reviewer1, reviewer2, reviewer3, reviewer4}){
            EntityReviewerApplications reviewerApplications = new EntityReviewerApplications();
            reviewerApplications.setApplicationKey(application_id);
            reviewerApplications.setReviewerEmail(reviewer);
            reviewerApplications.setDateAssigned(ts);
            reviewerApplications.insert();
        }

        new RECEngine().nextStep(application_id);

        flash("success", "Application assigned");
        return redirect(routes.ProfileHandler.overview());
    }
}
