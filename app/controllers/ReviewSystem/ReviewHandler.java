package controllers.ReviewSystem;

import controllers.UserSystem.Secured;
import controllers.UserSystem.routes;
import dao.ApplicationSystem.EntityComponentVersion;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.ReviewSystem.EntityReviewerApplications;
import dao.ReviewSystem.EntityReviewerComponentFeedback;
import dao.ReviewSystem.EntityReviewerComponentFeedbackPK;
import dao.UserSystem.EntityPerson;
import engine.RECEngine;
import helpers.CookieTags;
import models.GuiButton;
import models.UserSystem.Application;
import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Security.Authenticated(Secured.class)
public class ReviewHandler extends Controller {

    @Inject
    private FormFactory formFactory;

    public ReviewHandler() {
    }

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
            flash("danger", "You are not authorized to reviewable this application");
            return unauthorized("You are not authorized to reviewable this application");
        }

        Element element = ethicsApplication.GetPopulatedElement();
        Map<String, Boolean> editableMap = new HashMap<>();
        XMLTools.flatten(element).forEach(s -> editableMap.put(s, false));
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Review/Feedback Application", ethicsApplication.type(), element, editableMap, false, ethicsApplication.applicationPrimaryKey().shortName(), true, false, new HashMap<>(), GuiButton.negHomeCancel, GuiButton.posSubmitFeedback, GuiButton.netSaveFeedback));
    }

    @AddCSRFToken
    public Result viewApprove(String applicationID){
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(applicationID);
        EntityEthicsApplication ethicsApplication = EntityEthicsApplication.find.byId(entityEthicsApplicationPK);
        if (ethicsApplication == null) {
            return badRequest();
        }

        Element element =  ethicsApplication.GetPopulatedElement();
        Map<String, Boolean> editableMap = new HashMap<>();
        XMLTools.flatten(element).forEach(s -> editableMap.put(s, false));

        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: Edit Application", ethicsApplication.type(), element, editableMap, false, entityEthicsApplicationPK.shortName(), false, false, new HashMap<>(), GuiButton.negHomeCancel, GuiButton.posApproveApplication, GuiButton.netRejectApplication));
    }

    @RequireCSRFCheck
    public Result doViewApprove(){
        DynamicForm form = formFactory.form().bindFromRequest();
        String id = form.get("application_id");
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(id);
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(entityEthicsApplicationPK);

        if (application == null){
            flash("danger", "Unable to set the application status");
            return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
        }

        String email = session().get(CookieTags.user_id);
        if (application.getHodId().equals(email)){
            if (application.getHodApplicationReviewApproved() == null){
                application.setHodApplicationReviewApproved(true);
            } else {
                application.setHodFinalApplicationApproval(true);
            }
        } else {
            if (application.getRtiId().equals(email)){
                if (application.getRtiApplicationReviewApproved() == null){
                    application.setRtiApplicationReviewApproved(true);
                } else {
                    application.setRtiFinalApplicationApproval(true);
                }
            }
        }
        application.update();

        RECEngine.getInstance().nextStep(entityEthicsApplicationPK);
        flash("success", "Application approved");
        return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
    }

    @RequireCSRFCheck
    public Result doViewReject(){
        DynamicForm form = formFactory.form().bindFromRequest();
        String id = form.get("application_id");
        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(id);

        RECEngine.getInstance().rejected(entityEthicsApplicationPK);
        flash("danger", "Application rejected");
        return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
    }

    public Result saveAssignedReviewers(){
        DynamicForm form = formFactory.form().bindFromRequest();

        if (form.hasGlobalErrors()){
            flash("danger", "Check all reviewers have been assigned");
            return assignReviewer(form.get("application_id"));
        }

        EntityEthicsApplicationPK application_id = EntityEthicsApplicationPK.fromString(form.get("application_id"));
        assignReviewersToApplication(form, application_id);

        flash("success", "Assigned reviewers saved");
        return redirect(routes.ProfileHandler.overview());
    }

    public void assignReviewersToApplication(DynamicForm form, EntityEthicsApplicationPK application_id){
        // Get current timestamp
        Timestamp ts = Timestamp.from(Instant.now());

        // Assign reviewers to applications
        form.get().getData()
                .entrySet().stream()
                .filter(entry -> entry.getKey().matches("^reviewer\\d*$"))
                .forEach(entry -> {
                    EntityReviewerApplications reviewerApplications = new EntityReviewerApplications();
                    reviewerApplications.setApplicationKey(application_id);
                    reviewerApplications.setReviewerEmail(entry.getValue().toString());
                    reviewerApplications.setDateAssigned(ts);
                    reviewerApplications.insert();
                });
    }

    /**
     * Submit revised application and component data only if {@link RECEngine} allows it
     * @return
     */
    public Result saveReview() {
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.hasErrors()){
            return badRequest();
        }

        EntityEthicsApplicationPK pk = EntityEthicsApplicationPK.fromString(form.get("application_id"));
        saveReviewData(form, pk);

        flash("success", "Feedback has been saved.");
        return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
    }

    private void saveReviewData(DynamicForm form, EntityEthicsApplicationPK pk) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        String reviewer = session(CookieTags.user_id);

        HashMap<String, String> map = new HashMap<>();
        form.get().getData().entrySet().stream()
                .filter(stringObjectEntry -> stringObjectEntry.getKey().contains("feedback_"))
                .forEach(stringObjectEntry -> {
                    String id = stringObjectEntry.getKey().replace("feedback_", "");
                    map.put(id, stringObjectEntry.getValue().toString());
                });

        map.forEach((key, value) -> {

            if (!value.equals("{\"ops\":[{\"insert\":\"\\n\"}]}")) {

                if (key.startsWith("doc_") && (key.contains("_title") || key.contains("_desc") || key.contains("_document"))) {
                    key = key.substring(key.lastIndexOf("_"));
                }

                EntityComponentVersion entityComponentVersion = EntityComponentVersion.GetLatestComponent(pk, key);

                if (entityComponentVersion == null) {
                    System.out.println("EntityComponentVersion is null while adding feedback for this component");

                } else {

                    // Create Primary Key
                    EntityReviewerComponentFeedbackPK feedbackPK = new EntityReviewerComponentFeedbackPK();
                    feedbackPK.setComponentVersionId(entityComponentVersion.componentVersionPrimaryKey());
                    feedbackPK.setReviewerEmail(reviewer);

                    // Search by primary key
                    EntityReviewerComponentFeedback feedback = EntityReviewerComponentFeedback.find.byId(feedbackPK);

                    // Check if this component ahs had feedback set
                    if (feedback == null) {
                        // Vanilla component
                        feedback = new EntityReviewerComponentFeedback();
                        feedback.setPrimaryKey(feedbackPK);

                        feedback.setComponentId(key);
                        feedback.setReviewerEmail(reviewer);
                        feedback.setFeedbackDate(timestamp);
                        feedback.setComponentFeedback(value);
                        feedback.insert();
                    } else {
                        // Update feedback
                        feedback.setComponentFeedback(value);
                        feedback.update();
                    }
                }
            }
        });
    }

    public Result assignReviewer(String applicationId){
        EntityEthicsApplication application = EntityEthicsApplication.findByShortName(applicationId);
        if (application == null){
            flash("danger", "Application could not be found");
            return internalServerError();
        }

        EntityPerson personById = EntityPerson.getPersonById(session().get(CookieTags.user_id));
        if (personById == null)
            return redirect(routes.ProfileHandler.overview());
        else {
            Application app = Application.create(application, personById);
            return ok(views.html.ReviewSystem.AssignApplication.render(app));
        }
    }

    public Result doAssignReviewers(){
        DynamicForm form = formFactory.form().bindFromRequest();

        if (form.hasGlobalErrors()){
            flash("danger", "Check all reviewers have been assigned");
            return assignReviewer(form.get("application_id"));
        }

        EntityEthicsApplicationPK application_id = EntityEthicsApplicationPK.fromString(form.get("application_id"));

        assignReviewersToApplication(form, application_id);

        RECEngine.getInstance().nextStep(application_id);

        flash("success", "Applications assigned");
        return redirect(routes.ProfileHandler.overview());
    }

    public Result submitReview(){
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.hasErrors()){
            return badRequest();
        }

        EntityEthicsApplicationPK pk = EntityEthicsApplicationPK.fromString(form.get("application_id"));
        saveReviewData(form, pk);

        if (RECEngine.getInstance().nextStep(pk)){
            flash("success", "Feedback has been submitted.");
        } else {
            flash("success", "Feedback has been submitted.");
            flash("info", "There are other reivewers that need to give feedback still");
        }
        return redirect(controllers.UserSystem.routes.ProfileHandler.overview());
    }
}
