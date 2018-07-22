package controllers.NotificationSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;

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
     */
    public static void enrolledUser(String user_email) {

    }

    /**
     * Notifies the RCD and RTI that an application has been saved to the database requiring a faculty level review
     * @param applicationId
     * @param applicationTitle
     */
    public static void facultyReview(EntityEthicsApplicationPK applicationId, String applicationTitle, String rcd_email, String rti_email) {

    }

    public static void newMessage(EntityEthicsApplicationPK applicationId, String applicationTitle, String sendername, String receiver) {

    }

}
