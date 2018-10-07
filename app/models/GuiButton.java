package models;

import controllers.UserSystem.routes;
import play.api.mvc.Call;

public class GuiButton {

    public static GuiButton negHome = new GuiButton("Home", "btnHome", routes.ProfileHandler.overview(), "GET", "application/x-www-form-urlencoded");
    public static GuiButton negHomeCancel = new GuiButton("Back to Overview", "btnCancel", routes.ProfileHandler.overview(), "GET", "application/x-www-form-urlencoded");

    public static GuiButton netSaveApplication = new GuiButton("Save Application","btnSaveApplication",  controllers.ApplicationSystem.routes.ApplicationHandler.saveApplication());
    public static GuiButton posSubmitApplication = new GuiButton("Submit Application", "btnSubmitApplication", controllers.ApplicationSystem.routes.ApplicationHandler.submitApplication());

    public static GuiButton netSaveFeedback = new GuiButton("Save Feedback","btnSaveFeedback",  controllers.ReviewSystem.routes.ReviewHandler.saveReview());
    public static GuiButton posSubmitFeedback = new GuiButton("Submit Feedback", "btnSubmitFeedback", controllers.ReviewSystem.routes.ReviewHandler.submitReview());

    public static GuiButton netSaveSetupMeeting = new GuiButton("Save Meeting", "btnSaveMeeting", controllers.MeetingSystem.routes.MeetingController.saveMeeting());
    public static GuiButton posScheduleMeeting = new GuiButton("Schedule Meeting", "btnScheduleMeeting", controllers.MeetingSystem.routes.MeetingController.doSetupMeeting());

    public static GuiButton netSaveResolution = new GuiButton("Save Resolution", "btnSaveResolution", controllers.MeetingSystem.routes.MeetingController.saveResolution());
    public static GuiButton posSubmitResolution = new GuiButton("Submit Resolution", "btnSubmitResolution", controllers.MeetingSystem.routes.MeetingController.submitResolution());

    public static GuiButton posConcudeMeeting = new GuiButton("Conclude Meeting", "btnConcludeMeeting", controllers.MeetingSystem.routes.MeetingController.completeMeeting());

    public static GuiButton posApproveApplication = new GuiButton("Approve Application", "btnApproveApplication", controllers.ReviewSystem.routes.ReviewHandler.doViewApprove());
    public static GuiButton netRejectApplication = new GuiButton("Reject Application", "btnRejectApplication", controllers.ReviewSystem.routes.ReviewHandler.doViewReject());


    private Call callBack;
    private String title;
    private String id;
    private String method = "POST";
    private String enctype = "multipart/form-data";

    public GuiButton(String title, String id, Call callBack) {
        this.callBack = callBack;
        this.title = title;
        this.id = id;
    }

    public GuiButton(String title, String id, Call callBack, String method, String enctype) {
        this.enctype = enctype;
        this.callBack = callBack;
        this.method = method;
        this.title = title;
        this.id = id;
    }

    public Call getCallBack() {
        return callBack;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getEnctype() {
        return enctype;
    }
}
