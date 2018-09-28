package models.UserSystem;

import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.ReviewSystem.EntityReviewerApplications;
import dao.UserSystem.EntityPerson;
import engine.Permission;
import engine.RECEngine;
import models.ApplicationSystem.ApplicationStatus;
import scala.App;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public enum NotificationType {
        INFO, ATTENTION, MESSAGE, ACCEPTED;
    }

    private EntityEthicsApplicationPK applicationID;
    private String title;
    private Timestamp date_submitted;
    private Timestamp date_approved;
    private Timestamp date_assigned;
    private Timestamp due_date;
    private ApplicationStatus status;
    private Permission permission;
    private int[] notifications;

    public Application(){}

    public Application(EntityEthicsApplicationPK applicationID, String title, Timestamp date_submitted, Timestamp date_approved, ApplicationStatus status) {
        this.applicationID = applicationID;
        this.title = title;
        this.date_submitted = date_submitted == null ? Timestamp.from(Instant.now()) : date_submitted;
        this.date_approved = date_approved  == null ? Timestamp.from(Instant.now()) : date_submitted;
        this.date_assigned = Timestamp.from(Instant.now());
        this.due_date = Timestamp.from(Instant.now());
        this.status = status;
    }

    public static Application create(EntityEthicsApplication app, EntityPerson person) {

        Permission permission = RECEngine.checkAuthorized(person, app);
        Application application = new Application();
        application.setPermission(permission);

        if (permission == Permission.NONE)
            return application;

        ApplicationStatus status = ApplicationStatus.parse(app.getInternalStatus());
        application.setApplicationID(app.applicationPrimaryKey());
        application.setStatus(status);
        application.setTitle(app.title());

        application.setDate_submitted(app.getDateSubmitted());
        application.setDate_approved(app.getDateApproved());
        Timestamp dueDate = app.getDateSubmitted();
        if (dueDate != null)
            dueDate = addDays(dueDate, 14);
        application.setDue_date(dueDate);

        UserType userType = person.userType();

        switch (userType) {
            case Liaison:
                application.setDate_assigned(app.getLiaisonAssignedDate());
                break;
            case Reviewer:
                List<EntityReviewerApplications> collect = EntityReviewerApplications.find.all().stream()
                        .filter(apps -> apps.applicationPrimaryKey().equals(app.applicationPrimaryKey()))
                        .collect(Collectors.toList());
                if (collect.size() > 0) {
                    application.setDate_assigned(collect.get(0).getDateAssigned());
                } else {
                    application.setDate_assigned(null);
                }
                break;
            default:
                break;
        }
        return application;

    }

    public EntityEthicsApplicationPK getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(EntityEthicsApplicationPK applicationID) {
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

    private static Timestamp addDays(Timestamp now, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_WEEK, days);
        now.setTime(cal.getTime().getTime()); // or
        return new Timestamp(cal.getTime().getTime());
    }

    public String getAssignedDateSafe(){
        return (date_assigned == null) ? "-" : due_date.toString().split(" ")[0];
    }

    public String getDueDateSafe(){
        return (due_date == null) ? "-" : due_date.toString().split(" ")[0];
    }

    public String getApprovedDateSafe(){
        return (date_approved == null) ? "-" : due_date.toString().split(" ")[0];
    }

    public String getSubmittedDateSafe(){
        return (date_submitted == null) ? "Not submitted" : due_date.toString().split(" ")[0];
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
