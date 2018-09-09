package models.UserSystem;

import dao.ApplicationSystem.EntityEthicsApplication;
import models.ApplicationSystem.ApplicationStatus;
import scala.App;

import java.sql.Timestamp;

public class Application {

    public enum NotificationType {
        INFO, ATTENTION, MESSAGE, ACCEPTED;
    }

    public String applicationID;
    public String title;
    public Timestamp date_submitted;
    public Timestamp date_approved;
    public Timestamp date_assigned;
    public Timestamp due_date;
    public ApplicationStatus status;
    public int[] notifications;

    public Application(String applicationID, String title, Timestamp date_submitted, Timestamp date_approved, ApplicationStatus status) {
        this.applicationID = applicationID;
        this.title = title;
        this.date_submitted = date_submitted;
        this.date_approved = date_approved;
        this.status = status;
    }

    public static Application create(EntityEthicsApplication entityEthicsApplication) {
        ApplicationStatus status = ApplicationStatus.parse(entityEthicsApplication.getInternalStatus());
        Application application = new Application(entityEthicsApplication.applicationPrimaryKey().toString(), entityEthicsApplication.title(), entityEthicsApplication.getDateSubmitted(), entityEthicsApplication.getDateApproved(), status);
        return application;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate_submitted() {
        return date_submitted;
    }

    public void setDate_submitted(Timestamp date_submitted) {
        this.date_submitted = date_submitted;
    }

    public Timestamp getDate_approved() {
        return date_approved;
    }

    public void setDate_approved(Timestamp date_approved) {
        this.date_approved = date_approved;
    }

    public Timestamp getDate_assigned() {
        return date_assigned;
    }

    public void setDate_assigned(Timestamp date_assigned) {
        this.date_assigned = date_assigned;
    }

    public Timestamp getDue_date() {
        return due_date;
    }

    public void setDue_date(Timestamp due_date) {
        this.due_date = due_date;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public int[] getNotifications() {
        return notifications;
    }

    public void setNotifications(int[] notifications) {
        this.notifications = notifications;
    }
}
