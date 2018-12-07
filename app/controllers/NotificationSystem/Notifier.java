package controllers.NotificationSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.UserSystem.EntityPerson;
import engine.SystemNotification;
import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;
import models.UserSystem.UserType;

import java.time.Instant;
import java.util.Date;

public class Notifier {
    /**
     * Used to notify a PRP / PI about an application status change
     * @param new_status
     */
    public static void notifyStatus(EntityEthicsApplicationPK applicationId, ApplicationStatus new_status, String applicationTitle, String... person) {
        System.out.print("Hi\n\nApplication Title: " + applicationTitle + ".Application ID: " + applicationId.shortName() + ". New Status: " + new_status.description());
        for (String s : person) {
            Mailer.StatusUpdate(s, applicationId.shortName(), applicationTitle, new_status.description());
        }
    }

    /**
     * Used to notify a reviewer, liaison, hod, rti that their attention is needed for an application
     * @param new_status
     * @param applicationTitle
     */
    public static void requireAttention(EntityEthicsApplicationPK applicationId, ApplicationStatus new_status, String applicationTitle, String... person) {
        System.out.print("Hi\n\nYour attention is required for your application title: " + applicationTitle + ".\n\nNew Status: " + new_status.description());
        for (String s : person) {
            Mailer.AttentionRequired(s, applicationId.shortName(), applicationTitle, new_status.description());
        }
    }

    /**
     * Used to notify RCD of a message, a new user enrolled for a privileged position on the system
     *
     * ALso notifies the new user
     */
    public static void enrolledUser(UserType newUserType, String user_email) {
        System.out.print("Hi\n\nNew user enrolled into the system. \n\nEmail: " + user_email + ".\nUser Permission: " + newUserType.getType());
    }

    /**
     * Notifies the RCD and RTI that an application has been saved to the database requiring a faculty level reviewable
     * @param applicationId
     * @param applicationTitle
     */
    public static void facultyReview(EntityEthicsApplicationPK applicationId, String applicationTitle, String rcd_email, String rti_email) {
        System.out.print("Hi\n\nNew application added to the faculty review. Your attention is required for this application title: " + applicationTitle + ",");
    }

    /**
     * Uses the Messenger to send new messages between a PI, PRP, Liaison and Reviewer
     * @param applicationId
     * @param applicationTitle
     * @param sender
     * @param receiver
     */
    public static void newMessage(EntityEthicsApplicationPK applicationId, String applicationTitle, EntityPerson sender, EntityPerson receiver) {

    }

    /**
     * System notification to notifiy about statuses or results of applications' submissions/
     * @param applicationId
     * @param status
     * @param title
     * @param notification
     */
    public static void systemNotification(EntityEthicsApplicationPK applicationId, ApplicationStatus status, String title, SystemNotification notification, String... person) {
        System.out.print("Hi\n\nNew system notification!\n\nNOTIFICATION: " + notification.name() + ".\n\nApplication Title: " + title + ".\n\nApplication ID: " + applicationId.shortName() + ".\n\nNew Status: " + status.description());
        for (String s : person) {
            Mailer.PasswordChange(s);
        }
    }

    /**
     * Notifies the person that their password has changed.
     * @param personById
     */
    public static void changedPassword(EntityPerson personById) {
        System.out.print("Hi\n\nUser has changed their password. User Email: " + personById.getUserEmail() + ".\n\nUser: " + personById.getFullName());
        Mailer.SendPasswordChange(personById.getFullName(), personById.getUserEmail(), Date.from(Instant.now()).toString());
    }

    /**
     * Notifies the RCD and the person that the new user has registered
     * @param person
     */
    public static void newUser(EntityPerson person) {
        // Send welcome email
        System.out.print("Welcome to the RECH committee.\n\nPlease complete your profile at your earliest convenience.");
        Mailer.SendWelcome(person.getUserFirstname(), person.getUserEmail());
    }

    public static void sendVerification(String person, String token) {
        System.out.print("Hi.\n\nEmail Verification sent to: " + person);
        Mailer.SendVerificationEmail(person, token);
    }
}
