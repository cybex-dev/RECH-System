package models.ApplicationSystem;

import scala.App;

public enum ApplicationStatus {
    NOT_SUBMITTED(0),                   //
    APPROVED(1),                        //
    TEMPORARILY_APPROVED(2),            //
    TEMPORARILY_APPROVED_EDITS(3),      //
    REJECTED(4),                        //

    READY_FOR_SUBMISSION(10),           //

    PENDING_REVIEW_REVIEWER(20),        //
    PENDING_REVIEW_MEETING(21),         //
    PENDING_REVIEW_LIAISON(22),         //

    FEEDBACK_GIVEN_LIAISON(30),         //

    AWAITING_PRP_APPROVAL(40),          //
    AWAITING_PRE_HOD_RTI_APPROVAL(41),  //
    AWAITING_PRE_HOD_APPROVAL(42),      //
    AWAITING_PRE_RTI_APPROVAL(43),      //
    AWAITING_POST_HOD_RTI_APPROVAL(44), //
    AWAITING_POST_HOD_APPROVAL(45),     //
    AWAITING_POST_RTI_APPROVAL(46),     //


    FACULTY_REVIEW(50),                 //

    DRAFT(98),                          //
    UNKNOWN(99);


    private int status = 98;

    ApplicationStatus(int status) {
        this.status = status;
    }

    public static ApplicationStatus parse(int status) {
        switch (status) {
            case 0:
                return ApplicationStatus.NOT_SUBMITTED;

            case 1:
                return ApplicationStatus.APPROVED;

            case 2:
                return ApplicationStatus.TEMPORARILY_APPROVED;

            case 3:
                return ApplicationStatus.TEMPORARILY_APPROVED_EDITS;

            case 4:
                return ApplicationStatus.REJECTED;

            case 10:
                return ApplicationStatus.READY_FOR_SUBMISSION;

            case 20:
                return ApplicationStatus.PENDING_REVIEW_REVIEWER;

            case 21:
                return ApplicationStatus.PENDING_REVIEW_MEETING;

            case 23:
                return ApplicationStatus.PENDING_REVIEW_LIAISON;

            case 30:
                return ApplicationStatus.FEEDBACK_GIVEN_LIAISON;

            case 40:
                return ApplicationStatus.AWAITING_PRP_APPROVAL;

            case 41:
                return ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL;

            case 42:
                return ApplicationStatus.AWAITING_PRE_HOD_APPROVAL;

            case 43:
                return ApplicationStatus.AWAITING_PRE_RTI_APPROVAL;

            case 44:
                return ApplicationStatus.AWAITING_POST_HOD_RTI_APPROVAL;

            case 45:
                return ApplicationStatus.AWAITING_POST_HOD_APPROVAL;

            case 46:
                return ApplicationStatus.AWAITING_POST_RTI_APPROVAL;

            case 50:
                return ApplicationStatus.FACULTY_REVIEW;

            case 98:
                return ApplicationStatus.DRAFT;

            case 99:
            default:
                return ApplicationStatus.UNKNOWN;

        }
    }

    public short getStatus() {
        return (short) status;
    }
}
