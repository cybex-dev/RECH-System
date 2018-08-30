package models.UserSystem;

import dao.ApplicationSystem.EntityEthicsApplication;
import models.ApplicationSystem.ApplicationStatus;
import scala.App;

import java.sql.Timestamp;

public class Application {
    public String title;
    public Timestamp date_submitted;
    public Timestamp date_approved;
    public ApplicationStatus status;

    public Application(String title, Timestamp date_submitted, Timestamp date_approved, ApplicationStatus status) {
        this.title = title;
        this.date_submitted = date_submitted;
        this.date_approved = date_approved;
        this.status = status;
    }

    public static Application create(EntityEthicsApplication entityEthicsApplication) {
        ApplicationStatus status = ApplicationStatus.parse(entityEthicsApplication.getInternalStatus());
        Application application = new Application(entityEthicsApplication.title(), entityEthicsApplication.getDateSubmitted(), entityEthicsApplication.getDateApproved(), status);
        return application;
    }
}
