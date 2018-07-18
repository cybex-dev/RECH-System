package engine;

import DAO.ApplicationSystem.EntityComponent;
import DAO.ApplicationSystem.EntityComponentversion;
import DAO.ApplicationSystem.EntityEthicsApplication;
import DAO.UserSystem.EntityPerson;
import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class RECEngine {

    @Inject
    private static MessageProvider messageProvider;

    List<Filter> applicationFilters;

    public RECEngine() {
        loadFilters();
    }

    /**
     * Loads application filters into engine
     */
    private void loadFilters() {

    }

    /**
     * Assumes
     * <ul>
     *     <li>application is complete and ready for submission for review</li>
     *     <li>PI has approved application</li>
     *     <li>PRP has approved application</li>
     * </ul>
     *
     * Process:
     * <ol>
     *     <li>Receives an application ID</li>
     *     <li>Gets application from database and all the latest associated components</li>
     *     <li>Sets each component to submitted = true and sets the current date and time</li>
     * </ol>
     *
     * Submission entry states:
     * <ul>
     *
     * </ul>
     *
     *
     *
     * @param applicationId
     */
    public static void SubmitApplicationForReview(int applicationId){

        // Get ethics application entity
        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);
        if (entityEthicsApplication == null){
            throw new EntityNotFoundException(messageProvider.getMessage("error.application.notfound", applicationId));
        }

        // Save current timestamp
        Timestamp ts = Timestamp.from(new Date().toInstant());
        EntityComponent
                // Get all application components
                .getAllApplicationCompontents(applicationId)
                .forEach(entityComponent -> {
                    //Get latest component
                    EntityComponentversion latestComponent = EntityComponentversion.getLatestComponent(entityComponent.getComponentId());

                    // Set submitted state
                    latestComponent.setIsSubmitted(true);
                    latestComponent.setDateSubmitted(ts);

                    // Save changes
                    latestComponent.update();
                });

        // Update Ethics application entity
        entityEthicsApplication.setSubmitted(true);
        entityEthicsApplication.setDateSubmitted(ts);
        entityEthicsApplication.update();




        EntityPerson pi_id = EntityPerson.getPersonById(entityEthicsApplication.getPiId());
        EntityPerson prp_id = EntityPerson.getPersonById(entityEthicsApplication.getPiId());
        String title = EntityEthicsApplication.getTitle(applicationId);

        Mailer.NotifyApplicationSubmitted(pi_id.getUserFirstname(), pi_id.getUserEmail(), title);
        Mailer.NotifyApplicationSubmitted(prp_id.getUserFirstname(), prp_id.getUserEmail(), title);
    }

    private static void ChangeApplicationStatus(int applicationId) {

        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);

        ApplicationStatus currentStatus = ApplicationStatus.UNKNOWN;

        switch (currentStatus) {
            // Pre-submission phase
            case DRAFT:
                // Process:
                // Get all latest component data
                List<EntityComponentversion> latestComponents = EntityEthicsApplication.getLatestComponents(applicationId);
                // Fill into element
                // Create form and fill in elements
                // Receive element data from request
                // Create new component version for each element filled in
                // Check if application is complete - compare XML with component values

                // Verify Application data
                // STEP -> Not Submitted (Filter -> Committee)
                // STEP -> FACULTY_REVIEW (Filter -> Faculty)
                // STEP -> APPROVED (Filter -> None)

                break;
            case NOT_SUBMITTED:
                // Action: Applicant clicks next step: Request PRP Approval


            break;

            // Submission Phase
            case FACULTY_REVIEW:
                // RCD notified of faculty level application
                // Application is stored in database for review (printing)

                break;


            case AWAITING_PRP_APPROVAL:
                // Action: PRP receives request and responds appropriately
                // STEP -> AWAITING_PRP_APPROVAL (PRP Approves)
                // STEP -> NOT_SUBMITTED (PRP Reject)

            break;

            case READY_FOR_SUBMISSION:
                // PI is notified of this
                // Assume: Application has PI, PRP approval and is complete
                // Action: PI submits application
                // STEP -> PENDING_REVIEW_REVIEWER

            break;


            // Reviewer review
            case PENDING_REVIEW_REVIEWER:
                // Action: System randomly picks 4 reviewers, notifies them of a pending application
                // Reviewer reviews application, leaves feedback and submits feedback
                // PI/PRP notified of application review complete, notify of next stage

                break;

            // Meeting review and feedback
            case PENDING_REVIEW_MEETING:
                // System: when application feedback is all given for all reviewers, application becomes available for a meeting
                // Each meeting assumes all outstanding applications will be discussed
                // Action: RCD provides resolution and sets application status
                //
                // Status outcome
                // STEP -> REJECTED
                // STEP -> AWAITING_HOD_RTI_APPROVAL (Implies temporary approval)
                // STEP -> TEMPORARILY_APPROVED_EDITS

                break;

            // Meeting status outcome
            case REJECTED:
                // PI/PRP notified of this
                // Action: Application submitted status = false

                break;

            case TEMPORARILY_APPROVED_EDITS:
                // PI/PRP notified of this
                // Action: Assign Liaison to application
                // Liaison is notified
                // Action: PI Reviews resolution and makes changes where necessary, and submits to liaison

                // Liaision notified

                break;

            // Liaison Review & feedback loop
            case PENDING_REVIEW_LIAISON:
                // Liaison reviews changes (determined by resolution date / liaison feedback date vs component changes date) and provides feedback

                // STEP -> AWAITING_HOD_RTI_APPROVAL (implies all changes are satisfactory)
                // STEP -> FEEDBACK_GIVEN_LIAISON (changes rejected / edits suggested)
                // PI/PRP notified

                break;
            case FEEDBACK_GIVEN_LIAISON:
                // Action: PI Reviews suggestions and makes changes where necessary, and submits
                // Liaison notified
                break;

            // Final Stage - RTI / HOD approval
            case AWAITING_HOD_RTI_APPROVAL:
                // Assume application is complete and of satisfactory quality and standard as reviewed by committee
                // HOD/RTI notified
                // Action : HOD/RTI approves application
                //
                // STEP -> APPROVED (Both approved)
                // STEP -> AWAITING_HOD_APPROVAL (RTI approved)
                // STEP -> AWAITING_RTI_APPROVAL (HOD approved)
                // PI/PRP notified

                break;

            case AWAITING_HOD_APPROVAL:
                // PI/PRP notified of this
                // STEP -> AWAITING_HOD_APPROVAL (RTI approved)
                // STEP -> APPROVED (Both approved)


                break;
            case AWAITING_RTI_APPROVAL:
                // PI/PRP notified of this
                // STEP -> AWAITING_RTI_APPROVAL (HOD approved)
                // STEP -> APPROVED (Both approved)

                break;

            // Approved
            case APPROVED:
                // PI/PRP notified of this
                break;

            // Exceptional cases, unknowns
            case UNKNOWN:
            default: {

            }
        }
    }

    public static void nextStep(Integer applicationId) {

    }

    public void filterApplication(){

    }
}
