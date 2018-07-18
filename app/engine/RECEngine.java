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



        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);
        if (entityEthicsApplication == null) {
            throw new EntityNotFoundException(messageProvider.getMessage("error.application.notfound", applicationId));
        }

        ApplicationStatus previousApplicationStatus = EntityEthicsApplication.getApplicationStatus(applicationId);

        EntityPerson pi_id = EntityPerson.getPersonById(entityEthicsApplication.getPiId());
        EntityPerson prp_id = EntityPerson.getPersonById(entityEthicsApplication.getPiId());
        String title = EntityEthicsApplication.getTitle(applicationId);

        Mailer.NotifyApplicationSubmitted(pi_id.getUserFirstname(), pi_id.getUserEmail(), title);
        Mailer.NotifyApplicationSubmitted(prp_id.getUserFirstname(), prp_id.getUserEmail(), title);
    }

    private static void ChangeApplicationStatus(int applicationId, ApplicationStatus status){
        switch (status) {
            case NOT_SUBMITTED:
                break;
            case APPROVED:
                break;
            case TEMPORARILY_APPROVED_EDITS:
                break;
            case REJECTED:
                break;
            case PENDING_REVIEW_REVIEWER:
                break;
            case PENDING_REVIEW_MEETING:
                break;
            case PENDING_REVIEW_LIAISON:
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
            case DRAFT:
                break;
            case UNKNOWN:
                break;
        }
    }

    public static void nextStep(Integer applicationId) {

    }

    public void filterApplication(){

    }
}
