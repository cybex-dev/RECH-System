package models;

import controllers.UserSystem.routes;
import play.api.mvc.Call;

public class GuiButton {

    public static GuiButton negHome = new GuiButton("Home", "btnHome", routes.ProfileHandler.overview());
    public static GuiButton negHomeCancel = new GuiButton("Cancel", "btnCancel", routes.ProfileHandler.overview());

    public static GuiButton netSaveApplication = new GuiButton("Save Application","btnSaveApplication",  routes.ProfileHandler.overview());
    public static GuiButton posSubmitApplication = new GuiButton("Submit Application", "btnSubmitApplication", routes.ProfileHandler.overview());

    public static GuiButton netSaveFeedback = new GuiButton("Save Feedback","btnSaveFeedback",  routes.ProfileHandler.overview());
    public static GuiButton posSubmitFeedback = new GuiButton("Submit Feedback", "btnSubmitFeedback", routes.ProfileHandler.overview());

    public static GuiButton netSaveSetupMeeting = new GuiButton("Save Meeting", "btnSaveMeeting", routes.ProfileHandler.overview());
    public static GuiButton posScheduleMeeting = new GuiButton("Schedule Meeting", "btnScheduleMeeting", routes.ProfileHandler.overview());

    public static GuiButton netSaveResolution = new GuiButton("Save Resolution", "btnSaveResolution", routes.ProfileHandler.overview());
    public static GuiButton posSubmitResolution = new GuiButton("Submit Resolution", "btnSubmitResolution", routes.ProfileHandler.overview());

    public static GuiButton posConcudeMeeting = new GuiButton("Conclude Meeting", "btnConcludeMeeting", routes.ProfileHandler.overview());

    public static GuiButton posPrintApplication = new GuiButton("Print Application", "btnPrintApplication", routes.ProfileHandler.overview());

    public static GuiButton posApproveApplication = new GuiButton("Approve Application", "btnApproveApplication", routes.ProfileHandler.overview());
    public static GuiButton netRejectApplication = new GuiButton("Reject Application", "btnRejectApplication", routes.ProfileHandler.overview());


    private Call callBack;
    private String title;
    private String id;

    public GuiButton(String title, String id, Call callBack) {
        this.callBack = callBack;
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
}
