package controllers.MeetingSystem;

import controllers.UserSystem.Secured;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.Meeting.EntityAgendaItem;
import dao.Meeting.EntityMeeting;
import models.ApplicationSystem.ApplicationStatus;
import net.ddns.cyberstudios.Element;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Timestamp id = Timestamp.valueOf(meetingId + " 23:59:59");

        EntityMeeting meeting = EntityMeeting.getMeeting(id);

        Map<EntityAgendaItem, EntityEthicsApplication> applications = new HashMap<>();
        EntityMeeting
                .getAllApplications(meetingId)
                .forEach(entityAgendaItem -> applications.put(entityAgendaItem, EntityEthicsApplication.GetApplication(entityAgendaItem.applicationPrimaryKey())));

        return ok(views.html.MeetingSystem.AllApplications.apply(meeting, applications));
    }

    public Result meetingHome(){
        List<EntityMeeting> allMeetings = EntityMeeting.getAllMeetings().stream().sorted(Comparator.comparing(EntityMeeting::getMeetingDate)).collect(Collectors.toList());
        return ok(views.html.MeetingSystem.Meetings.render(allMeetings));
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

            if ((boolean) o){
                EntityAgendaItem item = new EntityAgendaItem();
                item.setApplicationId(EntityEthicsApplicationPK.fromString(s));
                item.setMeetingDate(meetingId);
                item.insert();
            }
        });

        flash("success", "New meeting set for " + meetingId.toString().split(" ")[0] + " reviewing " + count + " applications.");

        return meetingHome();

    }

    public Result completeMeeting(){
        // Get all agenda items, get the status and set the application status in entity ethics application
        // Notifiy all about this
        return TODO;
    }

    public Result saveMeeting(){
        // Get all agenda items, get the status and set the application status in entity ethics application
        // Notifiy all about this
        return TODO;
    }

    public Result saveResolution(){
        // Get all agenda items, get the status and set the application status in entity ethics application
        // Notifiy all about this
        return TODO;
    }

    /**
     * Gets a specific application to reviewable at meeting
     * @param applicationId
     * @return
     */
    public Result getApplication(String applicationId) {
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

        return ok(views.html.MeetingSystem.meetingApplication.render(application, populatedElement, stringBooleanHashMap, latestComponentFeedback));
    }

    /**
     * Submits resolution feedback and status of an application
     * @return
     */
    public Result submitResolution() {
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

//        String meeting_id = form.get("meeting_id");
//        if ()
//
//        EntityAgendaItem item =

        flash("warning", "TODO - implement this - submitResolition");
        flash("success", "Application resolution saved");
        return meetingHome();
    }
}
