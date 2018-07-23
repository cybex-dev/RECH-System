package engine;

import controllers.NotificationSystem.Notifier;
import dao.ApplicationSystem.EntityComponent;
import dao.ApplicationSystem.EntityComponentversion;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.Meeting.EntityAgendaitem;
import dao.ReviewSystem.EntityLiaisonfeedback;
import dao.ReviewSystem.EntityReviewerfeedback;
import dao.UserSystem.EntityPerson;
import helpers.Mailer;
import models.ApplicationSystem.ApplicationStatus;
import models.UserSystem.UserType;
import scala.App;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RECEngine {

    @Inject
    private MessageProvider messageProvider;

    public RECEngine() {
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

        String hod, rti;

        boolean applicationComplete;
        boolean hodNoResponse;
        boolean rtiNoResponse;
        boolean hodPreApproved;
        boolean hodDenied;
        boolean rtiPreApproved;
        boolean rtiDenied;

        switch (currentStatus) {
            // Pre-submission phase
            case DRAFT:

                // Fill into element
                // Create form and fill in elements
                // Receive element data from request
                // Create new component version for each element filled in
                // Check if application is applicationComplete - compare XML with component values

                // Process:
                // Get all latest component data

                // Check if application is ready to be submitted
                applicationComplete = checkComplete(entityEthicsApplication.applicationPrimaryKey());
                checkoutLatestApplication(applicationId);

                actionable = new Actionable() {

                    ApplicationStatus newStatus = ApplicationStatus.NOT_SUBMITTED;

                    @Override
                    public void doAction() {
                        if (applicationComplete) {
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                            entityEthicsApplication.setPiApprovedDate(Timestamp.from(new Date().toInstant()));
                            entityEthicsApplication.update();
                        }
                    }

                    @Override
                    public void doNotify() {
                        if (applicationComplete) {
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
                        entityEthicsApplication.update();
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
                        entityEthicsApplication.update();
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
                // Assume application is applicationComplete and reviewed and approved by PI & PRP
                // HOD/RTI notified
                // Action : HOD/RTI pre-approves application before submission to committee / faculty
                //
                // STEP -> READY_FOR_SUBMISSION (Both approved)
                // STEP -> AWAITING_PRE_HOD_APPROVAL (RTI approved)
                // STEP -> AWAITING_PRE_RTI_APPROVAL (HOD approved)
                // PI/PRP notified

                // Get RTI & HOD ids
                hod = EntityPerson.getHod(entityEthicsApplication.getDepartmentName());
                rti = EntityPerson.getRTI(entityEthicsApplication.getFacultyName());

                actionable = new Actionable() {

                    @Override
                    public void doAction() {

                        if (hod.isEmpty() ||
                                rti.isEmpty()) {
                            return;
                        }

                        // Assign HOD to application
                        entityEthicsApplication.setHodId(hod);

                        // Assign RTI to application
                        entityEthicsApplication.setRtiId(rti);

                        entityEthicsApplication.update();

                    }

                    @Override
                    public void doNotify() {
                        if (hod.isEmpty() ||
                                rti.isEmpty()) {
                            if (hod.isEmpty()) {
                                Notifier.systemNotification(applicationId, ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL, applicationTitle, SystemNotification.NO_HOD_AVAILABLE, piId, prpId, getRCDEmail());
                            }
                            if (rti.isEmpty()) {
                                Notifier.systemNotification(applicationId, ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL, applicationTitle, SystemNotification.NO_RTI_AVAILABLE, piId, prpId, getRCDEmail());
                            }

                        } else {
                            Notifier.requireAttention(applicationId, ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL, applicationTitle, entityEthicsApplication.getHodId(), entityEthicsApplication.getRtiId());
                        }
                    }
                };
                return actionable;

            case AWAITING_PRE_RTI_APPROVAL:
            case AWAITING_PRE_HOD_APPROVAL:
                // PI/PRP notified of this
                // STEP -> AWAITING_PRE_HOD_APPROVAL (RTI approved)
                // STEP -> READY_FOR_SUBMISSION (Both approved)
                hodNoResponse = entityEthicsApplication.getHodReviewDate() == null;
                rtiNoResponse = entityEthicsApplication.getRtiReviewDate() == null;
                hodPreApproved = (entityEthicsApplication.getHodApplicationReviewApproved());
                hodDenied = (!entityEthicsApplication.getHodApplicationReviewApproved());
                rtiPreApproved = (entityEthicsApplication.getHodApplicationReviewApproved());
                rtiDenied = (!entityEthicsApplication.getRtiApplicationReviewApproved());

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
                        entityEthicsApplication.update();
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
                        else {
                            Notifier.requireAttention(applicationId, ApplicationStatus.UNKNOWN, applicationTitle, piId, prpId);
                            Notifier.systemNotification(applicationId, newStatus, applicationTitle, SystemNotification.UNKNOWN_ASSIGNED, prpId);
                        }

                    }
                };
                return actionable;

            // Submission Phase
            case READY_FOR_SUBMISSION:
                // PI is notified of this
                // Assume: Application has PI, PRP approval and is applicationComplete
                // Action: PI submits application

                // Determine application destination
                // STEP -> FACULTY_REVIEW (Question -> Faculty)
                // STEP -> PENDING_REVIEW_REVIEWER (Question -> Committee)

                boolean isFacultyLevel = entityEthicsApplication.getApplicationLevel() == 0;
                List<String> availableReviewers = getAvailableReviewers();

                actionable = new Actionable() {

                    ApplicationStatus newStatus = (isFacultyLevel) ? ApplicationStatus.FACULTY_REVIEW : ApplicationStatus.PENDING_REVIEW_REVIEWER;

                    @Override
                    public void doAction() {

                        if (availableReviewers.size() == 4) {
                            if (!isFacultyLevel) {
                                Timestamp ts = Timestamp.from(new Date().toInstant());
                                for (String reviewer : availableReviewers) {
                                    EntityReviewerfeedback reviewerfeedback = new EntityReviewerfeedback();
                                    reviewerfeedback.setApplicationAssignedDate(ts);
                                    reviewerfeedback.setReviewerEmail(reviewer);
                                    reviewerfeedback.insert();
                                }
                            }
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                            entityEthicsApplication.update();
                        }

                    }

                    @Override
                    public void doNotify() {
                        if (availableReviewers.size() < 4) {
                            Notifier.systemNotification(applicationId, ApplicationStatus.READY_FOR_SUBMISSION, applicationTitle, SystemNotification.NOT_ENOUGH_REVIEWERS, getRCDEmail());
                        } else if (availableReviewers.size() == 4) {
                            if (isFacultyLevel) {
                                Notifier.facultyReview(applicationId, applicationTitle, getRCDEmail(), entityEthicsApplication.getRtiId());
                                Notifier.notifyStatus(applicationId, newStatus, applicationTitle, entityEthicsApplication.getHodId(), prpId);
                            } else {
                                List<String> reviewers = getApplicationReviewers(applicationId);
                                reviewers.forEach(s -> Notifier.requireAttention(applicationId, newStatus, applicationTitle, s));
                            }
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
                // PI/PRP notified of application review applicationComplete, notify of next stage

                int size = getApplicationReviewers(applicationId).size();
                newStatus = (size == 4) ? ApplicationStatus.PENDING_REVIEW_MEETING : ApplicationStatus.PENDING_REVIEW_REVIEWER;

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        entityEthicsApplication.update();
                    }

                    @Override
                    public void doNotify() {
                        Notifier.requireAttention(applicationId, newStatus, applicationTitle, piId, prpId);
                    }
                };
                return actionable;

            // Meeting review and feedback
            case PENDING_REVIEW_MEETING:
                // System: when application feedback is all given for all reviewers, application becomes available for a meeting
                // Each meeting assumes all outstanding applications will be discussed
                // Action: RCD provides resolution and sets application status

                ApplicationStatus latestMeetingStatus = getLatestMeetingStatus(applicationId);

                // Status outcome
                // STEP -> REJECTED
                // STEP -> TEMPORARILY_APPROVED (no edits required)
                // STEP -> TEMPORARILY_APPROVED_EDITS (edits required)

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        if (latestMeetingStatus == ApplicationStatus.TEMPORARILY_APPROVED ||
                                latestMeetingStatus == ApplicationStatus.TEMPORARILY_APPROVED_EDITS) {
                            String liaisonId = getAvailableLiaison();

                            if (liaisonId.isEmpty()) {
                                return;
                            }

                            entityEthicsApplication.setLiaisonId(liaisonId);
                            entityEthicsApplication.setLiaisonAssignedDate(Timestamp.from(new Date().toInstant()));
                        }

                        entityEthicsApplication.setInternalStatus(latestMeetingStatus.getStatus());
                        entityEthicsApplication.update();
                    }

                    @Override
                    public void doNotify() {
                        if (latestMeetingStatus == ApplicationStatus.REJECTED) {
                            Notifier.requireAttention(applicationId, latestMeetingStatus, applicationTitle, piId, prpId);
                        } else if (latestMeetingStatus == ApplicationStatus.TEMPORARILY_APPROVED) {
                            Notifier.notifyStatus(applicationId, ApplicationStatus.TEMPORARILY_APPROVED, applicationTitle, piId, prpId);
                            Notifier.requireAttention(applicationId, ApplicationStatus.TEMPORARILY_APPROVED, applicationTitle, entityEthicsApplication.getLiaisonId());
                        } else if (latestMeetingStatus == ApplicationStatus.TEMPORARILY_APPROVED_EDITS) {
                            Notifier.requireAttention(applicationId, latestMeetingStatus, applicationTitle, piId, prpId);
                        } else {
                            Notifier.systemNotification(applicationId, ApplicationStatus.UNKNOWN, applicationTitle, SystemNotification.UNKNOWN_ASSIGNED, piId, prpId, getRCDEmail());
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
                        entityEthicsApplication.update();
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

                newStatus = ApplicationStatus.AWAITING_POST_HOD_RTI_APPROVAL;

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        entityEthicsApplication.update();
                    }

                    @Override
                    public void doNotify() {
                        Notifier.notifyStatus(applicationId, newStatus, applicationTitle, piId, prpId);
                    }
                };
                return actionable;

            case TEMPORARILY_APPROVED_EDITS:
            case FEEDBACK_GIVEN_LIAISON:
                // PI/PRP notified of this
                // Action: Assign Liaison to application
                // Liaison is notified
                // Action: PI Reviews resolution and makes changes where necessary, and submits to liaison

                // Liaision notified


                applicationComplete = checkComplete(applicationId);
                newStatus = (applicationComplete) ? ApplicationStatus.PENDING_REVIEW_LIAISON : ApplicationStatus.TEMPORARILY_APPROVED_EDITS;

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        if (applicationComplete) {
                            checkoutLatestApplication(applicationId);
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                            entityEthicsApplication.update();
                        }
                    }

                    @Override
                    public void doNotify() {
                        if (applicationComplete) {
                            Notifier.notifyStatus(applicationId, newStatus, applicationTitle, piId, prpId);
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, entityEthicsApplication.getLiaisonId());
                        } else {
                            Notifier.requireAttention(applicationId, ApplicationStatus.TEMPORARILY_APPROVED_EDITS, applicationTitle, piId, prpId);
                        }
                    }
                };
                return actionable;

            // Liaison Review & feedback loop
            case PENDING_REVIEW_LIAISON:
                // Liaison reviews changes (determined by resolution date / liaison feedback date vs component changes date) and provides feedback

                // STEP -> AWAITING_HOD_RTI_APPROVAL (implies all changes are satisfactory)
                // STEP -> FEEDBACK_GIVEN_LIAISON (changes rejected / edits suggested)
                // PI/PRP notified

                EntityLiaisonfeedback entityLiaisonfeedback1 = EntityLiaisonfeedback.find.all().stream().filter(entityLiaisonfeedback -> entityEthicsApplication.applicationPrimaryKey().equals(applicationId)).findFirst().orElse(null);
                newStatus = ApplicationStatus.FEEDBACK_GIVEN_LIAISON;

                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        if (entityLiaisonfeedback1 == null) {
                            entityEthicsApplication.setInternalStatus(ApplicationStatus.UNKNOWN.getStatus());

                        } else if (entityLiaisonfeedback1.getRequiresEdits()) {
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        } else {
                            entityEthicsApplication.setInternalStatus(ApplicationStatus.AWAITING_POST_HOD_RTI_APPROVAL.getStatus());
                        }
                        entityEthicsApplication.update();
                    }

                    @Override
                    public void doNotify() {
                        if (entityLiaisonfeedback1 == null) {
                            Notifier.systemNotification(applicationId, currentStatus, applicationTitle, SystemNotification.LIAISON_FEEDBACK_NOT_FOUND, getRCDEmail(), entityEthicsApplication.getLiaisonId(), piId, prpId);
                        } else {
                            if (entityLiaisonfeedback1.getRequiresEdits()) {
                                Notifier.requireAttention(applicationId, newStatus, applicationTitle, piId, prpId);
                            } else {
                                Notifier.notifyStatus(applicationId, newStatus, applicationTitle, piId, prpId);
                                Notifier.requireAttention(applicationId, newStatus, applicationTitle, entityEthicsApplication.getLiaisonId());
                            }
                        }
                    }
                };
                return actionable;

            // Final Stage - RTI / HOD approval
            case AWAITING_POST_HOD_APPROVAL:
            case AWAITING_POST_RTI_APPROVAL:
                // PI/PRP notified of this
                // STEP -> AWAITING_HOD_APPROVAL (RTI approved)
                // STEP -> AWAITING_RTI_APPROVAL (HOD approved)
                // STEP -> APPROVED (Both approved)

                hodNoResponse = entityEthicsApplication.getHodFinalReviewDate() == null;
                rtiNoResponse = entityEthicsApplication.getRtiFinalReviewDate() == null;
                hodPreApproved = (entityEthicsApplication.getHodFinalApplicationApproval());
                hodDenied = (!entityEthicsApplication.getHodApplicationReviewApproved());
                rtiPreApproved = (entityEthicsApplication.getRtiFinalApplicationApproval());
                rtiDenied = (!entityEthicsApplication.getRtiApplicationReviewApproved());

                newStatus = (hodNoResponse)
                        ? ApplicationStatus.AWAITING_POST_HOD_APPROVAL
                        : (rtiNoResponse)
                        ? ApplicationStatus.AWAITING_POST_RTI_APPROVAL
                        : (hodDenied || rtiDenied)
                        ? ApplicationStatus.TEMPORARILY_APPROVED_EDITS
                        : (hodPreApproved && rtiPreApproved)
                        ? ApplicationStatus.APPROVED
                        : ApplicationStatus.UNKNOWN;

                actionable = new Actionable() {

                    @Override
                    public void doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        entityEthicsApplication.update();
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
                        else {
                            Notifier.requireAttention(applicationId, ApplicationStatus.UNKNOWN, applicationTitle, piId, prpId);
                            Notifier.systemNotification(applicationId, newStatus, applicationTitle, SystemNotification.UNKNOWN_ASSIGNED, prpId);
                        }
                    }
                };
                return actionable;


            case AWAITING_POST_HOD_RTI_APPROVAL:
                // Assume application is applicationComplete and of satisfactory quality and standard as reviewed by committee
                // HOD/RTI notified
                // Action : HOD/RTI approves application
                //
                // STEP -> APPROVED (Both approved)
                // STEP -> AWAITING_HOD_APPROVAL (RTI approved)
                // STEP -> AWAITING_RTI_APPROVAL (HOD approved)
                // PI/PRP notified

                actionable = new Actionable() {

                    @Override
                    public void doAction() {
                        // No action to perform
                    }

                    @Override
                    public void doNotify() {
                        Notifier.requireAttention(applicationId, ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL, applicationTitle, entityEthicsApplication.getHodId(), entityEthicsApplication.getRtiId());
                    }
                };
                return actionable;


            // Approved
            case APPROVED:
                // PI/PRP notified of this
                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        // No action to perform
                    }

                    @Override
                    public void doNotify() {
                        // No notification to send
                    }
                };
                return actionable;

            // Exceptional cases, unknowns
            case UNKNOWN:
            default: {
                actionable = new Actionable() {
                    @Override
                    public void doAction() {
                        // Do nothing
                    }

                    @Override
                    public void doNotify() {
                        Notifier.systemNotification(applicationId, ApplicationStatus.UNKNOWN, applicationTitle, SystemNotification.UNKNOWN_ASSIGNED, getRCDEmail());
                    }
                };
                return actionable;
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

    private void checkoutLatestApplication(EntityEthicsApplicationPK applicationId) {
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
    }

    /**
     * Gets a list of all application reviewers given an application primary key
     *
     * @param applicationId
     * @return
     */
    private List<String> getApplicationReviewers(EntityEthicsApplicationPK applicationId) {
        return EntityReviewerfeedback.find.all().stream()
                .filter(entityReviewerfeedback -> entityReviewerfeedback.applicationPrimaryKey().equals(applicationId))
                .map(EntityReviewerfeedback::getReviewerEmail)
                .collect(Collectors.toList());
    }

    // TODO check min lambda - this may not return the latest state

    /**
     * Gets the latest meeting status assigned to an application
     *
     * @param applicationId
     * @return
     */
    private ApplicationStatus getLatestMeetingStatus(EntityEthicsApplicationPK applicationId) {
        return EntityAgendaitem.find.all()
                .stream()
                .filter(entityAgendaitem -> entityAgendaitem.applicationPrimaryKey().equals(applicationId))
                .min((o1, o2) -> o1.getMeetingDate().before(o2.getMeetingDate()) ? -1 : (o1.getMeetingDate().after(o2.getMeetingDate()) ? 1 : 0))
                .map(entityAgendaitem -> ApplicationStatus.parse(entityAgendaitem.getApplicationStatus()))
                .orElse(ApplicationStatus.UNKNOWN);
    }

    /**
     * Strategy for assigning applications goes as follows:
     * - Sort all reviewers by latest date
     * - Add all reviewers to list, with distinct selection criteria
     * i.e. each reviewer who has review an application will be sorted by last reviewed, most recently reviewed first
     *
     * @return
     */
    private List<String> getAvailableReviewers() {
        final List<String> distinctReviewersByDate = new ArrayList<>();
        EntityReviewerfeedback.find.all().stream()
                .sorted((o1, o2) -> o1.getApplicationAssignedDate().before(o2.getApplicationAssignedDate()) ? -1 : (o1.getApplicationAssignedDate().after(o2.getApplicationAssignedDate()) ? 1 : 0))
                .forEach(entityReviewerfeedback -> {
                    if (!distinctReviewersByDate.contains(entityReviewerfeedback.getReviewerEmail()))
                        distinctReviewersByDate.add(entityReviewerfeedback.getReviewerEmail());
                });
        if (distinctReviewersByDate.size() < 4)
            return new ArrayList<>();

        int index = distinctReviewersByDate.size() - 1;
        return distinctReviewersByDate.subList(index - 3, index);
    }

    /**
     * Gets the liaison which has not received a new application for the longest period of all liaisons
     *
     * @return
     */
    private String getAvailableLiaison() {
        List<String> liaisons = new ArrayList<>();
        EntityEthicsApplication.find.all().stream()
                .sorted((o1, o2) -> o1.getLiaisonAssignedDate().before(o2.getLiaisonAssignedDate()) ? -1 : (o1.getLiaisonAssignedDate().after(o2.getLiaisonAssignedDate()) ? 1 : 0))
                .forEach(entityEthicsApplication -> {
                    if (!liaisons.contains(entityEthicsApplication.getLiaisonId())) {
                        liaisons.add(entityEthicsApplication.getLiaisonId());
                    }
                });
        return liaisons.get(liaisons.size() - 1);
    }

    /**
     * Traverses through all application elements, checking if all elements contain the required data -> this should match the appropriate XML file
     * @param pk
     * @return
     */
    public boolean checkComplete(EntityEthicsApplicationPK pk) {
        List<EntityComponentversion> latestComponents = EntityEthicsApplication.getLatestComponents(pk);



        return true;
    }

    public String getRCDEmail() {
        return EntityPerson.getRCD();
    }

}
