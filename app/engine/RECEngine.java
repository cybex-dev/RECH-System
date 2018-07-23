package engine;

import controllers.NotificationSystem.Notifier;
import dao.ApplicationSystem.EntityComponent;
import dao.ApplicationSystem.EntityComponentversion;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.ReviewSystem.EntityReviewerfeedback;
import dao.UserSystem.EntityPerson;
import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RECEngine {

    @Inject
    private MessageProvider messageProvider;

    public RECEngine() {
    }

    /**
     * Assumes
     * <ul>
     * <li>application is complete and ready for submission for review</li>
     * <li>PI has approved application</li>
     * <li>PRP has approved application</li>
     * </ul>
     * <p>
     * Process:
     * <ol>
     * <li>Receives an application ID</li>
     * <li>Gets application from database and all the latest associated components</li>
     * <li>Sets each component to submitted = true and sets the current date and time</li>
     * </ol>
     * <p>
     * Submission entry states:
     * <ul>
     *
     * </ul>
     *
     * @param applicationId
     */
    public void SubmitApplicationForReview(EntityEthicsApplicationPK applicationId) {

        // Get ethics application entity
        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);
        if (entityEthicsApplication == null) {
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
                    latestComponent.setSubmitted(true);
                    latestComponent.setDateSubmitted(ts);

                    // Save changes
                    latestComponent.update();
                });

        // Update Ethics application entity
        entityEthicsApplication.setDateSubmitted(ts);
        entityEthicsApplication.update();


        EntityPerson pi_id = EntityPerson.getPersonById(entityEthicsApplication.getPiId());
        EntityPerson prp_id = EntityPerson.getPersonById(entityEthicsApplication.getPrpId());
        String title = EntityEthicsApplication.GetTitle(applicationId);

        Mailer.NotifyApplicationSubmitted(pi_id.getUserFirstname(), pi_id.getUserEmail(), title);
        Mailer.NotifyApplicationSubmitted(prp_id.getUserFirstname(), prp_id.getUserEmail(), title);
    }

    public void nextStep(EntityEthicsApplicationPK applicationId) {
        Actionable nextAction = nextAction(applicationId);
        nextAction.doAction();
        nextAction.doNotify();
    }

    private Actionable nextAction(EntityEthicsApplicationPK applicationId) {
        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);

        if (entityEthicsApplication == null)
            return null;
        String applicationTitle = entityEthicsApplication.title();
        String piId = entityEthicsApplication.getPiId();
        String prpId = entityEthicsApplication.getPrpId();

        ApplicationStatus currentStatus = ApplicationStatus.parse(entityEthicsApplication.getInternalStatus());

        Actionable actionable;

        switch (currentStatus) {
            // Pre-submission phase
            case DRAFT:

                // Fill into element
                // Create form and fill in elements
                // Receive element data from request
                // Create new component version for each element filled in
                // Check if application is complete - compare XML with component values

                // Process:
                // Get all latest component data

                // Check if application is ready to be submitted
                final boolean checkComplete = checkComplete(entityEthicsApplication.applicationPrimaryKey());

                actionable = new Actionable() {

                    ApplicationStatus newStatus = ApplicationStatus.NOT_SUBMITTED;

                    @Override
                    public void doAction() {
                        if (checkComplete) {
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        }
                    }

                    @Override
                    public void doNotify() {
                        if (checkComplete) {
                            Notifier.notifyStatus(applicationId, newStatus, applicationTitle, piId);
                        }
                    }
                };
                return actionable;

            case NOT_SUBMITTED:
                // Action: Applicant clicks next step: Request PRP Approval

                ApplicationStatus newStatus = ApplicationStatus.AWAITING_PRP_APPROVAL;

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                    }

                    @Override
                    public void doNotify() {
                        Notifier.notifyStatus(applicationId, newStatus, piId);
                        Notifier.requireAttention(applicationId, newStatus, applicationTitle, prpId);
                    }
                };
                return actionable;

            case AWAITING_PRP_APPROVAL:

                // Action: PRP receives request and responds appropriately
                // STEP -> AWAITING_PRP_APPROVAL (PRP Approves)
                // STEP -> NOT_SUBMITTED (PRP Reject)

                boolean approved = (entityEthicsApplication.getPrpReviewDate() == null);

                actionable = new Actionable() {
                    ApplicationStatus newStatus = (approved) ? ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL : ApplicationStatus.NOT_SUBMITTED;

                    @Override
                    public void doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                    }

                    @Override
                    public void doNotify() {
                        if (approved) {
                            Notifier.requireAttention(applicationId, newStatus, piId);
                        } else {
                            Notifier.notifyStatus(applicationId, newStatus, piId);
                        }
                    }
                };
                return actionable;

            case AWAITING_PRE_HOD_RTI_APPROVAL:
                // Assume application is complete and reviewed and approved by PI & PRP
                // HOD/RTI notified
                // Action : HOD/RTI pre-approves application before submission to committee / faculty
                //
                // STEP -> READY_FOR_SUBMISSION (Both approved)
                // STEP -> AWAITING_PRE_HOD_APPROVAL (RTI approved)
                // STEP -> AWAITING_PRE_RTI_APPROVAL (HOD approved)
                // PI/PRP notified

                actionable = new Actionable() {

                    @Override
                    public void doAction() {
                        assignHodRti(applicationId);
                        // No action to be performed
                    }

                    @Override
                    public void doNotify() {
                        Notifier.requireAttention(applicationId, ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL, applicationTitle, entityEthicsApplication.getHodId(), entityEthicsApplication.getRtiId());
                    }
                };
                return actionable;

            case AWAITING_PRE_RTI_APPROVAL:
            case AWAITING_PRE_HOD_APPROVAL:
                // PI/PRP notified of this
                // STEP -> AWAITING_PRE_HOD_APPROVAL (RTI approved)
                // STEP -> READY_FOR_SUBMISSION (Both approved)
                boolean hodNoResponse = entityEthicsApplication.getHodReviewDate() == null;
                boolean rtiNoResponse = entityEthicsApplication.getRtiReviewDate() == null;
                boolean hodPreApproved = (entityEthicsApplication.getHodApplicationReviewApproved());
                boolean hodDenied = (!entityEthicsApplication.getHodApplicationReviewApproved());
                boolean rtiPreApproved = (entityEthicsApplication.getHodApplicationReviewApproved());
                boolean rtiDenied = (!entityEthicsApplication.getRtiApplicationReviewApproved());

                actionable = new Actionable() {

                    ApplicationStatus newStatus = (hodNoResponse)
                            ? ApplicationStatus.AWAITING_PRE_HOD_APPROVAL
                            : (rtiNoResponse)
                            ? ApplicationStatus.AWAITING_PRE_RTI_APPROVAL
                            : (hodDenied || rtiDenied)
                            ? ApplicationStatus.DRAFT
                            : (hodPreApproved && rtiPreApproved)
                            ? ApplicationStatus.READY_FOR_SUBMISSION
                            : ApplicationStatus.UNKNOWN;

                    @Override
                    public void doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                    }

                    @Override
                    public void doNotify() {
                        if (hodNoResponse) {
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, entityEthicsApplication.getHodId());
                        } else if (rtiNoResponse)
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, entityEthicsApplication.getHodId());
                        else if (hodDenied || rtiDenied)
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, piId, prpId);
                        else if (hodPreApproved && rtiPreApproved)
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, entityEthicsApplication.getHodId(), prpId);

                    }
                };
                return actionable;

            // Submission Phase
            case READY_FOR_SUBMISSION:
                // PI is notified of this
                // Assume: Application has PI, PRP approval and is complete
                // Action: PI submits application

                // Determine application destination
                // STEP -> FACULTY_REVIEW (Question -> Faculty)
                // STEP -> PENDING_REVIEW_REVIEWER (Question -> Committee)

                boolean isFacultyLevel = checkFacultyLevelRated(applicationId);
                List<String> availableReviewers = getAvailableReviewers();

                actionable = new Actionable() {

                    ApplicationStatus newStatus = (isFacultyLevel) ? ApplicationStatus.FACULTY_REVIEW : ApplicationStatus.PENDING_REVIEW_REVIEWER;

                    @Override
                    public void doAction() {
                        if (!isFacultyLevel) {
                            assignReviewersToApplication(applicationId, availableReviewers);
                        }
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                    }

                    @Override
                    public void doNotify() {
                        if (isFacultyLevel) {
                            // TODO get RCD email
                            Notifier.facultyReview(applicationId, applicationTitle, "", entityEthicsApplication.getRtiId());
                            Notifier.notifyStatus(applicationId, newStatus, applicationTitle, entityEthicsApplication.getHodId(), prpId);
                        } else {
                            List<String> reviewers = ;
                            reviewers.forEach(s -> Notifier.requireAttention(applicationId, newStatus, applicationTitle, s));
                        }
                    }
                };
                return actionable;


            case FACULTY_REVIEW:
                // RCD notified of faculty level application
                // Application is stored in database for review (printing)

                break;


            // Reviewer review
            case PENDING_REVIEW_REVIEWER:
                // Action: System randomly picks 4 reviewers, notifies them of a pending application
                // Reviewer reviews application, leaves feedback and submits feedback
                // PI/PRP notified of application review complete, notify of next stage

                int size = getReviewers(applicationId).size();
                newStatus = (size == 4) ? ApplicationStatus.PENDING_REVIEW_MEETING : ApplicationStatus.PENDING_REVIEW_REVIEWER;

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        if (newStatus == ApplicationStatus.PENDING_REVIEW_MEETING) {
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        }
                    }

                    @Override
                    public void doNotify() {
                        if (newStatus == ApplicationStatus.PENDING_REVIEW_MEETING) {
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, piId, prpId);
                        }
                    }
                };
                return actionable;

            // Meeting review and feedback
            case PENDING_REVIEW_MEETING:
                // System: when application feedback is all given for all reviewers, application becomes available for a meeting
                // Each meeting assumes all outstanding applications will be discussed
                // Action: RCD provides resolution and sets application status

                ApplicationStatus s = getMeetingStatus(applicationId);

                // Status outcome
                // STEP -> REJECTED
                // STEP -> TEMPORARILY_APPROVED (no edits required)
                // STEP -> TEMPORARILY_APPROVED_EDITS (edits required)

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        if (s == ApplicationStatus.TEMPORARILY_APPROVED ||
                                s == ApplicationStatus.TEMPORARILY_APPROVED_EDITS) {
                            String liaisonId = getLiaison();
                            entityEthicsApplication.setLiaisonId(liaisonId);
                            entityEthicsApplication.setLiaisonAssignedDate(Timestamp.from(new Date().toInstant()));
                        }

                        entityEthicsApplication.setInternalStatus(s.getStatus());
                    }

                    @Override
                    public void doNotify() {
                        if (s == ApplicationStatus.REJECTED) {
                            Notifier.requireAttention(applicationId, s, applicationTitle, piId, prpId);
                        } else if (s == ApplicationStatus.TEMPORARILY_APPROVED) {
                            Notifier.notifyStatus(applicationId, s, applicationTitle, piId, prpId);
                        } else if (s == ApplicationStatus.TEMPORARILY_APPROVED_EDITS) {
                            Notifier.requireAttention(applicationId, s, applicationTitle, piId, prpId);
                        }
                    }
                };
                return actionable;

            // Meeting status outcome
            case REJECTED:
                // PI/PRP notified of this
                // Action: Application submitted status = false

                actionable = new Actionable() {

                    @Override
                    public void doAction() {
                        entityEthicsApplication.setInternalStatus(ApplicationStatus.DRAFT.getStatus());
                    }

                    @Override
                    public void doNotify() {
                        Notifier.notifyStatus(applicationId, ApplicationStatus.DRAFT, applicationTitle, piId, prpId);
                    }
                };
                return actionable;

            case TEMPORARILY_APPROVED:
                // PI/PRP notified of this
                // Action: Assign Liaison to application
                // Liaison is notified

                // STEP -> (Optional Liaison review / feedback)
                // STEP -> AWAITING_HOD_RTI_APPROVAL (liaison approved - application is satisfactory)


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
            case AWAITING_POST_HOD_RTI_APPROVAL:
                // Assume application is complete and of satisfactory quality and standard as reviewed by committee
                // HOD/RTI notified
                // Action : HOD/RTI approves application
                //
                // STEP -> APPROVED (Both approved)
                // STEP -> AWAITING_HOD_APPROVAL (RTI approved)
                // STEP -> AWAITING_RTI_APPROVAL (HOD approved)
                // PI/PRP notified

                break;

            case AWAITING_POST_HOD_APPROVAL:
                // PI/PRP notified of this
                // STEP -> AWAITING_HOD_APPROVAL (RTI approved)
                // STEP -> APPROVED (Both approved)


                break;
            case AWAITING_POST_RTI_APPROVAL:
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
        return new Actionable() {
            @Override
            public void doAction() {

            }

            @Override
            public void doNotify() {

            }
        };
    }

    private String getLiaison() {
        return null;
    }

    private ApplicationStatus getMeetingStatus(EntityEthicsApplicationPK applicationId) {
        return null;
    }

    /**
     * Strategy for assigning applications goes as follows:
     *  - Sort all reviewers by latest date
     *  - Add all reviewers to list, with distinct selection criteria
     *  i.e. each reviewer who has review an application will be sorted by last reviewed, most recently reviewed first
     * @return
     */
    private List<String> getAvailableReviewers() {
        final List<String> distinctReviewersByDate = new ArrayList<>();
        EntityReviewerfeedback.find.all().stream().sorted((o1, o2) -> o1.getApplicationAssignedDate().before(o2.getApplicationAssignedDate()) ? -1 : (o1.getApplicationAssignedDate().after(o2.getApplicationAssignedDate()) ? 1 : 0))
                .forEach(entityReviewerfeedback -> {
                    if (!distinctReviewersByDate.contains(entityReviewerfeedback.getReviewerEmail()))
                        distinctReviewersByDate.add(entityReviewerfeedback.getReviewerEmail());
                });
        if (distinctReviewersByDate.size() < 4)
            return new ArrayList<>();

        int index = distinctReviewersByDate.size()-1;
        return distinctReviewersByDate.subList(index-3, index);
    }

    /**
     * Accepts a list of 4 reviewer emails and assigns them to an application
     * @param applicationId
     * @param availableReviewers
     */
    private void assignReviewersToApplication(EntityEthicsApplicationPK applicationId, List<String> availableReviewers) {
        Timestamp ts = Timestamp.from(new Date().toInstant());
        for (String reviewer : availableReviewers) {
            EntityReviewerfeedback reviewerfeedback = new EntityReviewerfeedback();
            reviewerfeedback.setApplicationAssignedDate(ts);
            reviewerfeedback.setReviewerEmail(reviewer);
            reviewerfeedback.insert();
        }
    }

    private void assignHodRti(EntityEthicsApplicationPK applicationId) {

    }

    private boolean checkFacultyLevelRated(EntityEthicsApplicationPK applicationId) {
        return true;
    }

    public void filterApplication() {

    }

    public boolean checkComplete(EntityEthicsApplicationPK pk) {
        List<EntityComponentversion> latestComponents = EntityEthicsApplication.getLatestComponents(pk);
        return true;
    }

}
