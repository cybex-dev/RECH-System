package Engine;

import DAO.ApplicationSystem.EntityEthicsApplication;
import DAO.UserSystem.EntityPerson;
import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;

import javax.persistence.EntityNotFoundException;

public class RECEngine {
    public static void SubmitApplication(int applicationId){
        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);
        if (entityEthicsApplication == null) {
            throw new EntityNotFoundException("Cannot find application with ID: " + String.valueOf(applicationId));
        }

        ApplicationStatus previousApplicationStatus = EntityEthicsApplication.getApplicationStatus(applicationId);

        EntityPerson pi_id = EntityPerson.getPersonById(entityEthicsApplication.getPiId());
        EntityPerson prp_id = EntityPerson.getPersonById(entityEthicsApplication.getPiId());
        String title = EntityEthicsApplication.getTitle(applicationId);

        Mailer.NotifyApplicationSubmitted(pi_id.getUserFirstname(), pi_id.getUserEmail(), title);
        Mailer.NotifyApplicationSubmitted(prp_id.getUserFirstname(), prp_id.getUserEmail(), title);
    }

    public static void ChangeApplicationStatus(int applicationId, ApplicationStatus status){
        switch (status) {
            case NOT_SUBMITTED:
                break;
            case APPROVED:
                break;
            case APPROVED_EDITS:
                break;
            case TEMPORARILY_APPROVED:
                break;
            case TEMPORARILY_APPROVED_EDITS:
                break;
            case REJECTED:
                break;
            case REJECTED_EDITS:
                break;
            case RESUBMITTED:
                break;
            case RESUBMITTED_APPROVAL:
                break;
            case RESUBMITTED_REVIEW:
                break;
            case PENDING_REVIEW_REVIEWER:
                break;
            case PENDING_REVIEW_MEETING:
                break;
            case PENDING_REVIEW_LIAISON:
                break;
            case FEEDBACK_GIVEN_REVIEWER:
                break;
            case FEEDBACK_GIVEN_LIAISON:
                break;
            case AWAITING_HOD_RTI_APPROVAL:
                break;
            case AWAITING_HOD_APPROVAL:
                break;
            case AWAITING_RTI_APPROVAL:
                break;
            case AWAITING_PRP_APPROVAL:
                break;
            case FACULTY_REVIEW:
                break;
            case DRAFT:
                break;
            case UNKNOWN:
                break;
        }
    }
}
