package controllers.MeetingSystem;

import controllers.NotificationSystem.Notifier;
import controllers.UserSystem.Secured;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.Meeting.EntityAgendaItem;
import dao.Meeting.EntityMeeting;
import dao.UserSystem.EntityPerson;
import engine.RECEngine;
import models.ApplicationSystem.ApplicationStatus;
import net.ddns.cyberstudios.Element;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import javax.swing.text.html.parser.Entity;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Security.Authenticated(Secured.class)
public class MeetingController extends Controller {

    @Inject
    private FormFactory formFactory;

    public MeetingController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public MeetingController(){}

    /**
     * Gets a list of all applications to be reviewed at the meeting
     * @return
     */
    public Result allApplications(String meetingId){
//        List<EntityEthicsApplication> allApplicationsByStatus = EntityEthicsApplication.getAllApplicationsByStatus(ApplicationStatus.PENDING_REVIEW_MEETING);
//        return ok(views.html.MeetingSystem.AllApplications.apply(allApplicationsByStatus));

        Timestamp id = Timestamp.valueOf(meetingId + " 23:59:59.0");

        EntityMeeting meeting = EntityMeeting.getMeeting(id);

        Map<EntityAgendaItem, EntityEthicsApplication> applications = new HashMap<>();
        EntityMeeting
                .getAllApplications(meetingId)
                .forEach(entityAgendaItem -> applications.put(entityAgendaItem, EntityEthicsApplication.GetApplication(entityAgendaItem.applicationPrimaryKey())));

        return ok(views.html.MeetingSystem.AllApplications.apply(meeting, applications));
    }

    public Result meetingHome(){
        List<EntityMeeting> allMeetings = EntityMeeting.getAllMeetings().stream().sorted(Comparator.comparing(EntityMeeting::getMeetingDate)).collect(Collectors.toList());
        List<EntityMeeting> completeMeetings = allMeetings.stream().filter(EntityMeeting::getIsComplete).collect(Collectors.toList());
        List<EntityMeeting> incompleteMeetings = allMeetings.stream().filter(entityMeeting -> !entityMeeting.getIsComplete()).collect(Collectors.toList());
        return ok(views.html.MeetingSystem.Meetings.render(completeMeetings, incompleteMeetings));
    }

    public Result setupMeeting(){
        List<EntityEthicsApplication> allApplicationsByStatus = EntityEthicsApplication.getAllApplicationsByStatus(ApplicationStatus.PENDING_REVIEW_MEETING);
        return ok(views.html.MeetingSystem.SetupMeeting.apply(allApplicationsByStatus));
    }

    public Result doSetupMeeting(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        if (dynamicForm.hasErrors()){
            flash("danger", "Please check all fields are filled in");
            List<EntityEthicsApplication> allApplicationsByStatus = EntityEthicsApplication.getAllApplicationsByStatus(ApplicationStatus.PENDING_REVIEW_MEETING);
            return ok(views.html.MeetingSystem.SetupMeeting.apply(allApplicationsByStatus));
        }

        String meeting_date = dynamicForm.get("meeting_date");
        if (meeting_date == null) {
            flash("danger", "Please select a meeting date");
            List<EntityEthicsApplication> allApplicationsByStatus = EntityEthicsApplication.getAllApplicationsByStatus(ApplicationStatus.PENDING_REVIEW_MEETING);
            return ok(views.html.MeetingSystem.SetupMeeting.apply(allApplicationsByStatus));
        }

        Timestamp meetingId = Timestamp.valueOf(meeting_date + " 23:59:59");
        EntityMeeting meeting = EntityMeeting.getMeeting(meetingId);
        if (meeting != null){
            flash("danger", "There is already meeting set for this date, please select another date");
            List<EntityEthicsApplication> allApplicationsByStatus = EntityEthicsApplication.getAllApplicationsByStatus(ApplicationStatus.PENDING_REVIEW_MEETING);
            return ok(views.html.MeetingSystem.SetupMeeting.apply(allApplicationsByStatus));
        }

        String announcements = dynamicForm.get("announcements");

        meeting = new EntityMeeting();
        meeting.setMeetingDate(meetingId);
        meeting.setAnnouncements(announcements);
        meeting.insert();

        int count = 0;

        dynamicForm.get().getData().forEach((s, o) -> {
            // an input checkbox being checked. id = appId and value = true, checking which enables the name attribute, where name == id

            boolean matches = s.matches("^chk_(\\w|-)+$");
            boolean on = o.toString().toLowerCase().equals("on");
            if (matches && on){
                String applicationId = s.split("^chk_")[1];
                EntityAgendaItem item = new EntityAgendaItem();
                item.setApplicationId(EntityEthicsApplicationPK.fromString(applicationId));
                item.setMeetingDate(meetingId);
                item.insert();
            }
        });

        flash("success", "New meeting set for " + meetingId.toString().split(" ")[0] + " reviewing " + meeting.getNumberOfApplications() + " applications.");

        return redirect(routes.MeetingController.meetingHome());

    }

    public Result completeMeeting(){
        // Get all agenda items, get the status and set the application status in entity ethics application
        // Notifiy all about this
        DynamicForm form = formFactory.form().bindFromRequest();
        Timestamp meetingId = Timestamp.valueOf(form.get("meeting_id"));
        EntityMeeting entityMeeting = EntityMeeting.find.byId(meetingId);
        if (entityMeeting == null){
            flash("error", "Unable to find meeting identified by " + meetingId.toString());
            return meetingHome();
        }
        if (!entityMeeting.getIsComplete()) {
            flash("warning", "Unable to set meeting status to complete. There are agenda items not reviewed. Please review these to proceed.");
            return meetingHome();
        }

        List<String> errorApps = new ArrayList<>();
        entityMeeting.getMeetingCompleteItems().forEach(entityAgendaItem -> {
            // Get Ethics Application
            EntityEthicsApplication application = EntityEthicsApplication.GetApplication(entityAgendaItem.applicationPrimaryKey());
            if (application != null){
                // Set internal status
                application.setInternalStatus(entityAgendaItem.getApplicationStatus());
                application.save();

                // Proceed to next step
                RECEngine.getInstance().nextStep(application.applicationPrimaryKey());
            } else {
                errorApps.add(entityAgendaItem.applicationPrimaryKey().shortName());
            }
        });
        if (errorApps.size() > 0){
            flash("error", "There was an error setting the status for application(s): " + errorApps.stream().reduce((s, s2) -> s.concat(" ").concat(s2)));
        }

        entityMeeting.setIsComplete(true);
        entityMeeting.save();
        flash("success", "Meeting marked as complete!");
        return meetingHome();
    }

    public Result saveResolution(String meetingId, String applicationId){
        // Get all agenda items, get the status and set the application status in entity ethics application
        // Notifiy all about this
        DynamicForm form = formFactory.form().bindFromRequest();
        String resolution = form.get("resolution");
        String tStatus = form.get("status");
        short status = -1;
        switch (tStatus) {
            case "Approved": status = ApplicationStatus.APPROVED.getStatus(); break;
            case "Rejected": status = ApplicationStatus.REJECTED.getStatus(); break;
            case "Conditional Approval": status = ApplicationStatus.TEMPORARILY_APPROVED.getStatus(); break;
            case "Resubmission Required": status = ApplicationStatus.RESUBMISSION.getStatus(); break;
        }

        EntityEthicsApplicationPK entityEthicsApplicationPK = EntityEthicsApplicationPK.fromString(applicationId);
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(entityEthicsApplicationPK);
        if (application == null) {
            flash("error", "Unable to find application with ID #" + applicationId);
            return allApplications(meetingId);
        }

        String liaisonId = form.get("liaison").replace("[", "").replace("]", "").split(" ")[1];
        if (EntityPerson.getPersonById(liaisonId) == null){
            flash("error", "Unable to find liaison");
            return notFound();
        }

        if (status == ApplicationStatus.TEMPORARILY_APPROVED.getStatus()){
            application.setLiaisonId(liaisonId);
            application.setLiaisonAssignedDate(Timestamp.from(Instant.now()));
        }

        EntityAgendaItem entityAgendaItem = EntityMeeting.getAllApplications(meetingId).stream().filter(e -> e.applicationPrimaryKey().equals(entityEthicsApplicationPK)).findFirst().orElse(null);
        if (entityAgendaItem == null) {
            flash("error", "Unable to set the status of application with ID #" + applicationId);
            return allApplications(meetingId);
        }

        entityAgendaItem.setResolution(resolution);
        entityAgendaItem.setApplicationStatus(status);
        entityAgendaItem.setIsReviewed(true);
        entityAgendaItem.save();
        flash("success", "Application resolution saved!");
        return allApplications(meetingId);
    }

    /**
     * Gets a specific application to reviewable at meeting
     * @param applicationId
     * @return
     */
    public Result getApplication(String meeting_id, String applicationId) {
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(EntityEthicsApplicationPK.fromString(applicationId));
        if (application == null){
            flash("danger", "Application was not found");
            return meetingHome();
        }

        if (ApplicationStatus.parse(application.getInternalStatus()) != ApplicationStatus.PENDING_REVIEW_MEETING){
            flash("warning", "An error occurred, the application is not ready for scrutiny yet");
            return meetingHome();
        }

        HashMap<String, Boolean> stringBooleanHashMap = new HashMap<>();
        Element populatedElement = application.GetPopulatedElement();
        Map<String, List<String>> latestComponentFeedback = application.GetLatestComponentFeedback();

        return ok(views.html.MeetingSystem.meetingApplication.render(meeting_id, application, populatedElement, stringBooleanHashMap, latestComponentFeedback));
    }

    /*
     DynamicForm form = formFactory.form().bindFromRequest();
        String application_id = form.get("application_id");
        EntityEthicsApplication application = EntityEthicsApplication.findByShortName(application_id);
        if (application == null){
            flash("danger", "An error has occurred, application could not be determined. Please try again.");
            return meetingHome();
        }

        if (form.hasErrors()){
            flash("danger", "Please check all fields are complete");
            HashMap<String, Boolean> stringBooleanHashMap = new HashMap<>();
            Element populatedElement = application.GetPopulatedElement();
            Map<String, List<String>> latestComponentFeedback = application.GetLatestComponentFeedback();

            return badRequest(views.html.MeetingSystem.meetingApplication.render(application, populatedElement, stringBooleanHashMap, latestComponentFeedback));
        }
     */
}
