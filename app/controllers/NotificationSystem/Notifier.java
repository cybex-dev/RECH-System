package controllers.NotificationSystem;

import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;

public class Notifier {
    /**
     * Used to notify a PRP / PI about an application status change
     * @param user_email
     * @param previous_status
     * @param new_status
     */
    public static void notifyStatus(ApplicationStatus previous_status, ApplicationStatus new_status, String...user_emails) {

    }

    /**
     * Used to notify a reviewer, liaison, hod, rti that their attention is needed for an application
     * @param reviewer_liaison_email
     * @param new_status
     * @param applicationID
     * @param applicationTitle
     */
    public static void requireAttention(String reviewer_liaison_email, ApplicationStatus new_status, Integer applicationID, String applicationTitle) {

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
    public static void facultyReview(Integer applicationId, String applicationTitle) {

    }




}
