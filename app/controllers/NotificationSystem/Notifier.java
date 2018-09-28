package controllers.NotificationSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.UserSystem.EntityPerson;
import engine.SystemNotification;
import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;
import models.UserSystem.UserType;
import play.libs.mailer.Email;

public class Notifier {
    /**
     * Used to notify a PRP / PI about an application status change
     * @param new_status
     */
    public static void notifyStatus(EntityEthicsApplicationPK applicationId, ApplicationStatus new_status, String applicationTitle, String...user_emails) {

    }

    /**
     * Used to notify a reviewer, liaison, hod, rti that their attention is needed for an application
     * @param new_status
     * @param applicationTitle
     */
    public static void requireAttention(EntityEthicsApplicationPK applicationId, ApplicationStatus new_status, String applicationTitle, String...user_emails) {

    }

    /**
     * Used to notify RCD of a message, a new user enrolled for a privileged position on the system
     *
     * ALso notifies the new user
     */
    public static void enrolledUser(UserType newUserType, String user_email) {

    }

    /**
     * Notifies the RCD and RTI that an application has been saved to the database requiring a faculty level reviewable
     * @param applicationId
     * @param applicationTitle
     */
    public static void facultyReview(EntityEthicsApplicationPK applicationId, String applicationTitle, String rcd_email, String rti_email) {

    }

    /**
     * Uses the Messenger to send new messages between a PI, PRP, Liaison and Reviewer
     * @param applicationId
     * @param applicationTitle
     * @param sendername
     * @param receiver
     */
    public static void newMessage(EntityEthicsApplicationPK applicationId, String applicationTitle, String sendername, String receiver) {

    }

    /**
     * System notification to notifiy about statuses or results of applications' submissions/
     * @param applicationId
     * @param status
     * @param title
     * @param notification
     * @param emails
     */
    public static void systemNotification(EntityEthicsApplicationPK applicationId, ApplicationStatus status, String title, SystemNotification notification, String...emails) {

    }

    /**
     * Notifies the person that their password has changed.
     * @param personById
     */
    public static void changedPassword(EntityPerson personById) {

    }

    /**
     * Notifies the RCD and the person that the new user has registered
     * @param person
     */
    public static void newUser(EntityPerson person) {
        // Send welcome email
        Mailer.SendWelcome(person.getUserFirstname(), person.getUserEmail());
    }

    public static void sendVerification(EntityPerson person, String token) {
        Mailer.SendVerificationEmail(person, token);
    }
}
