package models.ApplicationSystem;

public enum ApplicationStatus {
    NOT_SUBMITTED(0),
    APPROVED(1),
    APPROVED_EDITS(2),
    TEMPORARILY_APPROVED(3),
    TEMPORARILY_APPROVED_EDITS(4),
    REJECTED(5),
    REJECTED_EDITS(6),

    RESUBMITTED(10),
    RESUBMITTED_APPROVAL(11),
    RESUBMITTED_REVIEW(12),

    PENDING_REVIEW_REVIEWER(20),
    PENDING_REVIEW_MEETING(21),
    PENDING_REVIEW_LIAISON(23),

    FEEDBACK_GIVEN_REVIEWER(30),
    FEEDBACK_GIVEN_LIAISON(31),

    AWAITING_HOD_RTI_APPROVAL(40),
    AWAITING_HOD_APPROVAL(41),
    AWAITING_RTI_APPROVAL(42),
    AWAITING_PRP_APPROVAL(43),

    FACULTY_REVIEW(50),

    DRAFT(98),
    UNKNOWN(99);


    private int status = 0;

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
                return ApplicationStatus.APPROVED_EDITS;

            case 3:
                return ApplicationStatus.TEMPORARILY_APPROVED;

            case 4:
                return ApplicationStatus.TEMPORARILY_APPROVED_EDITS;

            case 5:
                return ApplicationStatus.REJECTED;

            case 6:
                return ApplicationStatus.REJECTED_EDITS;

            case 10:
                return ApplicationStatus.RESUBMITTED;

            case 11:
                return ApplicationStatus.RESUBMITTED_APPROVAL;

            case 12:
                return ApplicationStatus.RESUBMITTED_REVIEW;

            case 20:
                return ApplicationStatus.PENDING_REVIEW_REVIEWER;

            case 21:
                return ApplicationStatus.PENDING_REVIEW_MEETING;

            case 23:
                return ApplicationStatus.PENDING_REVIEW_LIAISON;

            case 30:
                return ApplicationStatus.FEEDBACK_GIVEN_REVIEWER;

            case 31:
                return ApplicationStatus.FEEDBACK_GIVEN_LIAISON;

            case 40:
                return ApplicationStatus.AWAITING_HOD_RTI_APPROVAL;

            case 41:
                return ApplicationStatus.AWAITING_HOD_APPROVAL;

            case 42:
                return ApplicationStatus.AWAITING_RTI_APPROVAL;

            case 43:
                return ApplicationStatus.AWAITING_PRP_APPROVAL;

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
