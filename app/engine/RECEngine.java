package engine;

import controllers.NotificationSystem.Notifier;
import dao.ApplicationSystem.EntityComponent;
import dao.ApplicationSystem.EntityComponentVersion;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.Meeting.EntityAgendaItem;
import dao.ReviewSystem.EntityLiaisonComponentFeedback;
import dao.ReviewSystem.EntityReviewerApplications;
import dao.UserSystem.EntityPerson;
import helpers.CookieTags;
import models.ApplicationSystem.ApplicationStatus;
import models.ApplicationSystem.EthicsApplication;
import models.UserSystem.UserType;
import net.ddns.cyberstudios.Element;
import play.mvc.Controller;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class RECEngine extends Controller {

    static private RECEngine engine;

    public static RECEngine getInstance(){
        return engine;
    }

    @Inject
    private MessageProvider messageProvider;

    private RECEngine() {
    }

    public boolean nextStep(EntityEthicsApplicationPK applicationId) {
        System.out.print("[ENGINE] NextStep");

        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);
        if (entityEthicsApplication == null) {
            System.out.println("[ENGINE] nextStep: Application not found + " + applicationId.shortName());
            return false;
        }

        EntityPerson person = EntityPerson.getPersonById(session(CookieTags.user_id));
        if (person == null) {
            flash("danger", "Unknown error occured, please logout and login again.");
            return false;
        }

        Permission permission = checkAuthorized(person, entityEthicsApplication);
        if (permission != Permission.MODIFY) {
            System.out.println("Unauthorized to perform action on application");
            return false;
        }

        System.out.print(ApplicationStatus.parse(entityEthicsApplication.getInternalStatus()) + " -> ");
        Actionable nextAction = nextAction(applicationId);
        boolean b = nextAction.doAction();
        nextAction.doNotify();

        entityEthicsApplication.refresh();
        System.out.print(ApplicationStatus.parse(entityEthicsApplication.getInternalStatus()) + "\n");
        return b;
    }

    private static Permission checkAuthorized(EntityPerson person, EntityEthicsApplication entityEthicsApplication) {
        if (person.userType() == UserType.RCD)
            return Permission.MODIFY;
        switch (ApplicationStatus.parse(entityEthicsApplication.getInternalStatus())) {
            case FEEDBACK_GIVEN_LIAISON:
            case REJECTED:
            case APPROVED:
            case UNKNOWN:
            case DRAFT:
            case READY_FOR_SUBMISSION:
            case NOT_SUBMITTED: {
                return (person.getUserEmail().equals(entityEthicsApplication.getPiId())) ? Permission.MODIFY : Permission.NONE;
            }

            case TEMPORARILY_APPROVED:
            case FACULTY_REVIEW:
                return person.getUserEmail().equals(entityEthicsApplication.getPiId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                                person.getUserEmail().equals(entityEthicsApplication.getRtiId()) ? Permission.VIEW : Permission.NONE;

            case AWAITING_REVIEWER_ALLOCATION:
                return (person.getUserEmail().equals(entityEthicsApplication.getPiId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPrpId())) ? Permission.VIEW : Permission.NONE;

            case PENDING_REVIEW_REVIEWER:
                return (person.getUserEmail().equals(entityEthicsApplication.getPiId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPrpId()))
                        ? Permission.VIEW
                        : EntityReviewerApplications.getApplicationReviewers(entityEthicsApplication.applicationPrimaryKey()).stream().anyMatch(s -> s.equals(person.getUserEmail()))
                            ? Permission.MODIFY
                            : Permission.NONE;

            case PENDING_REVIEW_MEETING:
                return (person.getUserEmail().equals(entityEthicsApplication.getPiId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                        EntityReviewerApplications.getApplicationReviewers(entityEthicsApplication.applicationPrimaryKey()).stream().anyMatch(s -> s.equals(person.getUserEmail())))
                        ? Permission.VIEW
                        : Permission.NONE;

            case TEMPORARILY_APPROVED_EDITS:
            case PENDING_REVIEW_LIAISON:
                return (person.getUserEmail().equals(entityEthicsApplication.getPiId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPrpId()))
                        ? Permission.VIEW
                        : person.getUserEmail().equals(entityEthicsApplication.getLiaisonId())
                        ? Permission.MODIFY
                        : Permission.NONE;


            case AWAITING_PRP_APPROVAL:
                return person.getUserEmail().equals(entityEthicsApplication.getPiId())
                        ? Permission.VIEW
                        : person.getUserEmail().equals(entityEthicsApplication.getPrpId())
                        ? Permission.MODIFY
                        : Permission.NONE;

            case AWAITING_PRE_HOD_RTI_APPROVAL:
                return person.getUserEmail().equals(entityEthicsApplication.getHodId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getRtiId())
                        ? Permission.MODIFY
                        : (person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPiId()))
                        ? Permission.VIEW
                        : Permission.NONE;

            case AWAITING_PRE_HOD_APPROVAL:
                return person.getUserEmail().equals(entityEthicsApplication.getHodId())
                        ? Permission.MODIFY
                        : (person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPiId()))
                        ? Permission.VIEW
                        : Permission.NONE;

            case AWAITING_PRE_RTI_APPROVAL:
                return person.getUserEmail().equals(entityEthicsApplication.getRtiId())
                        ? Permission.MODIFY
                        : (person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPiId()))
                        ? Permission.VIEW
                        : Permission.NONE;

            case AWAITING_POST_HOD_RTI_APPROVAL: {
                return person.getUserEmail().equals(entityEthicsApplication.getHodId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getRtiId())
                        ? Permission.MODIFY
                        : (person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPiId()))
                        ? Permission.VIEW
                        : Permission.NONE;
            }

            case AWAITING_POST_HOD_APPROVAL:
                return person.getUserEmail().equals(entityEthicsApplication.getHodId())
                        ? Permission.MODIFY
                        : (person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPiId()))
                        ? Permission.VIEW
                        : Permission.NONE;

            case AWAITING_POST_RTI_APPROVAL:
                return person.getUserEmail().equals(entityEthicsApplication.getRtiId())
                        ? Permission.MODIFY
                        : (person.getUserEmail().equals(entityEthicsApplication.getPrpId()) ||
                        person.getUserEmail().equals(entityEthicsApplication.getPiId()))
                        ? Permission.VIEW
                        : Permission.NONE;


        }
        return Permission.NONE;

    }

    private Actionable nextAction(EntityEthicsApplicationPK applicationId) {
        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);
        if (entityEthicsApplication == null) {
            System.out.println("[ENGINE] nextAction: Application not found + " + applicationId.shortName());
            return null;
        }

        String applicationTitle = entityEthicsApplication.title();
        String piId = entityEthicsApplication.getPiId();
        String prpId = entityEthicsApplication.getPrpId();

        ApplicationStatus currentStatus = ApplicationStatus.parse(entityEthicsApplication.getInternalStatus());

        Actionable actionable = new Actionable() {
            @Override
            public boolean doAction() {
                return false;
            }

            @Override
            public void doNotify() {

            }
        };

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
                applicationComplete = isComplete(entityEthicsApplication.applicationPrimaryKey());

                actionable = new Actionable() {

                    ApplicationStatus newStatus = ApplicationStatus.NOT_SUBMITTED;

                    @Override
                    public boolean doAction() {
                        if (applicationComplete) {
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                            entityEthicsApplication.update();
                            return true;
                        }
                        return false;
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
                applicationComplete = isComplete(entityEthicsApplication.applicationPrimaryKey());

                actionable = new Actionable() {
                    @Override
                    public boolean doAction() {
                        if (applicationComplete) {
                            checkoutLatestApplication(applicationId);
                            entityEthicsApplication.setPiApprovedDate(Timestamp.from(Instant.now()));
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                            entityEthicsApplication.update();
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void doNotify() {
                        if (applicationComplete) {
                            Notifier.notifyStatus(applicationId, newStatus, piId);
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, prpId);
                        }
                    }
                };
                return actionable;

            case AWAITING_PRP_APPROVAL:

                // Action: PRP receives request and responds appropriately
                // STEP -> AWAITING_PRP_APPROVAL (PRP Approves)
                // STEP -> NOT_SUBMITTED (PRP Reject)

                boolean approved = (entityEthicsApplication.getPrpApprovedDate() == null);

                actionable = new Actionable() {
                    ApplicationStatus newStatus = (approved) ? ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL : ApplicationStatus.NOT_SUBMITTED;

                    @Override
                    public boolean doAction() {
                        if (approved){
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                            entityEthicsApplication.update();
                            return true;
                        }
                        return false;
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
                boolean hodAccepted = entityEthicsApplication.getHodApplicationReviewApproved();
                boolean rtiAccepted = entityEthicsApplication.getRtiApplicationReviewApproved();

                actionable = new Actionable() {

                    @Override
                    public boolean doAction() {

                        if (hodAccepted && rtiAccepted) {
                            entityEthicsApplication.setInternalStatus(ApplicationStatus.READY_FOR_SUBMISSION.getStatus());
                        } else {
                            if (hodAccepted) {
                                entityEthicsApplication.setInternalStatus(ApplicationStatus.AWAITING_PRE_RTI_APPROVAL.getStatus());
                            } else {
                                if (rtiAccepted) {
                                    entityEthicsApplication.setInternalStatus(ApplicationStatus.AWAITING_PRE_HOD_APPROVAL.getStatus());
                                } else {
                                    return false;
                                }
                            }
                        }

                        entityEthicsApplication.update();

                        return true;
                    }

                    @Override
                    public void doNotify() {

                        if (hodAccepted && rtiAccepted) {
                            Notifier.requireAttention(applicationId, ApplicationStatus.READY_FOR_SUBMISSION, applicationTitle, piId, prpId);
                        } else {
                            if (hodAccepted) {
                                Notifier.requireAttention(applicationId, ApplicationStatus.READY_FOR_SUBMISSION, applicationTitle, entityEthicsApplication.getHodId());
                            } else {
                                if (rtiAccepted) {
                                    Notifier.requireAttention(applicationId, ApplicationStatus.READY_FOR_SUBMISSION, applicationTitle, entityEthicsApplication.getHodId());
                                } else {
                                    Notifier.requireAttention(applicationId, ApplicationStatus.AWAITING_PRE_HOD_RTI_APPROVAL, applicationTitle, entityEthicsApplication.getHodId(), entityEthicsApplication.getRtiId());
                                }
                            }
                        }
                    }
                };
                return actionable;

            case AWAITING_PRE_RTI_APPROVAL:
            case AWAITING_PRE_HOD_APPROVAL:
                // PI/PRP notified of this
                // STEP -> AWAITING_PRE_HOD_APPROVAL (RTI approved)
                // STEP -> READY_FOR_SUBMISSION (Both approved)
                hodNoResponse = entityEthicsApplication.getHodApplicationReviewApproved() == null;
                rtiNoResponse = entityEthicsApplication.getRtiApplicationReviewApproved() == null;
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
                    public boolean doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        entityEthicsApplication.update();
                        return true;
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
                // STEP -> AWAITING_REVIEWER_ALLOCATION (Question -> Committee) //RCD needs to allocated reviewers
                boolean isFacultyLevel = entityEthicsApplication.getApplicationLevel() == 0;
                newStatus = (isFacultyLevel) ? ApplicationStatus.FACULTY_REVIEW : ApplicationStatus.AWAITING_REVIEWER_ALLOCATION;
                entityEthicsApplication.setApplicationRevision(entityEthicsApplication.getApplicationRevision() + 1);

                actionable = new Actionable() {
                    @Override
                    public boolean doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        entityEthicsApplication.update();
                        return true;
                    }

                    @Override
                    public void doNotify() {
                        if (isFacultyLevel) {
                            Notifier.facultyReview(applicationId, applicationTitle, EntityPerson.getRCD(), entityEthicsApplication.getRtiId());
                            Notifier.notifyStatus(applicationId, newStatus, applicationTitle, entityEthicsApplication.getHodId(), prpId);
                        } else {
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, EntityPerson.getRCD());
                        }
                    }
                };

                return actionable;


            case AWAITING_REVIEWER_ALLOCATION:
                List<String> applicationReviewers = EntityReviewerApplications.getApplicationReviewers(entityEthicsApplication.applicationPrimaryKey());

                actionable = new Actionable() {

                    @Override
                    public boolean doAction() {
                        if (applicationReviewers.size() > 0) {
                            entityEthicsApplication.setInternalStatus(ApplicationStatus.PENDING_REVIEW_REVIEWER.getStatus());
                            entityEthicsApplication.update();
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void doNotify() {
                        if (applicationReviewers.size() > 0) {
                            applicationReviewers.forEach(s -> Notifier.requireAttention(applicationId, ApplicationStatus.PENDING_REVIEW_REVIEWER, applicationTitle, s));
                        }
                    }
                };
                return actionable;

            case FACULTY_REVIEW:
                // RCD notified of faculty level application
                // Application is stored in database for reviewable (printing)

                break;


            // Reviewer reviewable
            case PENDING_REVIEW_REVIEWER:
                // Action: System randomly picks 4 reviewers, notifies them of a pending application
                // Reviewer reviews application, leaves feedback and submits feedback
                // PI/PRP notified of application reviewable applicationComplete, notify of next stage

                int size = EntityReviewerApplications.getApplicationReviewers(applicationId).size();

                actionable = new Actionable() {
                    @Override
                    public boolean doAction() {
                        if (size > 0) {
                            entityEthicsApplication.setInternalStatus(ApplicationStatus.PENDING_REVIEW_MEETING.getStatus());
                            entityEthicsApplication.update();
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void doNotify() {
                        if (size > 0 ) {
                            Notifier.requireAttention(applicationId, ApplicationStatus.PENDING_REVIEW_MEETING, applicationTitle, piId, prpId);
                        }
                    }
                };
                return actionable;

            // Meeting reviewable and feedback
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
                    public boolean doAction() {
                        return true;
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
                            Notifier.systemNotification(applicationId, ApplicationStatus.UNKNOWN, applicationTitle, SystemNotification.UNKNOWN_ASSIGNED, piId, prpId, EntityPerson.getRCD());
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
                    public boolean doAction() {
                        entityEthicsApplication.setInternalStatus(ApplicationStatus.DRAFT.getStatus());
                        entityEthicsApplication.update();
                        return true;
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

                // STEP -> (Optional Liaison reviewable / feedback)
                // STEP -> AWAITING_HOD_RTI_APPROVAL (liaison approved - application is satisfactory)

                newStatus = ApplicationStatus.AWAITING_POST_HOD_RTI_APPROVAL;

                actionable = new Actionable() {
                    @Override
                    public boolean doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        entityEthicsApplication.update();
                        return true;
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


                applicationComplete = isComplete(applicationId);
                newStatus = (applicationComplete) ? ApplicationStatus.PENDING_REVIEW_LIAISON : ApplicationStatus.TEMPORARILY_APPROVED_EDITS;

                actionable = new Actionable() {
                    @Override
                    public boolean doAction() {
                        if (applicationComplete) {
                            checkoutLatestApplication(applicationId);
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                            entityEthicsApplication.update();
                            return true;
                        }
                        return false;
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

                boolean requiresEdits = EntityLiaisonComponentFeedback.find.all().stream()
                        .filter(entityLiaisonComponentFeedback -> entityLiaisonComponentFeedback.applicationPrimaryKey().equals(applicationId))
                        .anyMatch(entityLiaisonComponentFeedback -> !entityLiaisonComponentFeedback.getChangeSatisfactory());
                newStatus = ApplicationStatus.FEEDBACK_GIVEN_LIAISON;

                actionable = new Actionable() {
                    @Override
                    public boolean doAction() {
                        if (requiresEdits) {
                            entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        } else {
                            entityEthicsApplication.setInternalStatus(ApplicationStatus.AWAITING_POST_HOD_RTI_APPROVAL.getStatus());
                        }
                        entityEthicsApplication.update();
                        return !requiresEdits;
                    }

                    @Override
                    public void doNotify() {

                        if (requiresEdits) {
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, piId, prpId);
                        } else {
                            Notifier.notifyStatus(applicationId, newStatus, applicationTitle, piId, prpId);
                            Notifier.requireAttention(applicationId, newStatus, applicationTitle, entityEthicsApplication.getLiaisonId());
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

                hodNoResponse = entityEthicsApplication.getHodFinalApplicationApproval() == null;
                rtiNoResponse = entityEthicsApplication.getRtiFinalApplicationApproval() == null;
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
                    public boolean doAction() {
                        entityEthicsApplication.setInternalStatus(newStatus.getStatus());
                        entityEthicsApplication.update();
                        return true;
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
                    public boolean doAction() {
                        // No action to perform
                        return true;
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
                    public boolean doAction() {
                        // No action to perform
                        return true;
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
                    public boolean doAction() {
                        // Do nothing
                        return false;
                    }

                    @Override
                    public void doNotify() {
                        Notifier.systemNotification(applicationId, ApplicationStatus.UNKNOWN, applicationTitle, SystemNotification.UNKNOWN_ASSIGNED, EntityPerson.getRCD());
                    }
                };
                return actionable;
            }
        }


        return actionable;
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
                .GetAllApplicationCompontents(applicationId)
                .forEach(entityComponent -> {
                    //Get latest component
                    EntityComponentVersion latestComponent = EntityComponentVersion.GetLatestComponent(applicationId, entityComponent.getComponentId());

                    // Set submitted state
                    latestComponent.setIsSubmitted(true);
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
    //TODO fix
//    private List<String> getApplicationReviewers(EntityEthicsApplicationPK applicationId) {
//        return EntityReviewerfeedback.find.all().stream()
//                .filter(entityReviewerfeedback -> entityReviewerfeedback.applicationPrimaryKey().equals(applicationId))
//                .map(EntityReviewerfeedback::getReviewerEmail)
//                .collect(Collectors.toList());
//    }

    // TODO check min lambda - this may not return the latest state

    /**
     * Gets the latest meeting status assigned to an application
     *
     * @param applicationId
     * @return
     */
    private ApplicationStatus getLatestMeetingStatus(EntityEthicsApplicationPK applicationId) {
        return EntityAgendaItem.find.all()
                .stream()
                .filter(entityAgendaitem -> entityAgendaitem.applicationPrimaryKey().equals(applicationId))
                .min((o1, o2) -> o1.getMeetingDate().before(o2.getMeetingDate()) ? -1 : (o1.getMeetingDate().after(o2.getMeetingDate()) ? 1 : 0))
                .map(entityAgendaitem -> ApplicationStatus.parse(entityAgendaitem.getApplicationStatus()))
                .orElse(ApplicationStatus.UNKNOWN);
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
     *
     * @param pk
     * @return TODO not completed
     */
    public boolean isComplete(EntityEthicsApplicationPK pk) {
        EntityEthicsApplication application = EntityEthicsApplication.find.byId(pk);
        if (application == null) {
            return false;
        }

        // Get EthicsApplication object
        EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(application.type());

        // Get latest components of an application
        List<EntityComponentVersion> latestComponents = EntityEthicsApplication.getLatestComponents(pk);

        // Get all latest values into a map with <String; Object> definition
        Map<String, Object> m = new HashMap<>();
        for (EntityComponentVersion latestComponent : latestComponents) {
            switch (latestComponent.getResponseType().toLowerCase()) {
                case "boolean": {
                    m.put(latestComponent.getComponentId(), latestComponent.getBoolValue());
                    break;
                }

                case "text": {
                    m.put(latestComponent.getComponentId(), latestComponent.getTextValue());
                    break;
                }

                case "document": {
                    m.put(latestComponent.getComponentId() + "_title", latestComponent.getDocumentName());
                    m.put(latestComponent.getComponentId() + "_desc", latestComponent.getDocumentDescription());
                    m.put(latestComponent.getComponentId() + "_document", latestComponent.getDocumentLocationHash());
                    break;
                }
            }
        }

        // Generate root Element with values attached
        Element rootWithValues = EthicsApplication.addValuesToRootElement(ethicsApplication.getRootElement(), m);
        return true;
//        return XMLTools.checkComplete(rootWithValues);
        // grp_risks_benefits > extension > no children
    }

    private boolean checkComplete(Element rootWithValues, boolean isRequired) {
        boolean childrenPresent = true;
        for (Element element : rootWithValues.getChildren()) {
            switch (element.getTag()) {
                case "application":
                case "section":
                case "list": {
                    for (Element e : rootWithValues.getChildren()) {
                        if (!checkComplete(e, isRequired)) {
                            childrenPresent = false;
                            break;
                        }
                    }
                    return childrenPresent;
                }

                case "group": {
                    // Nothing will happen here except is type is document, then we do some extra processing

                    // Process all children
                    for (Element e : rootWithValues.getChildren()) {
                        // Check if root element is a document root
                        if (element.getType().equals("document")) {
                            // If child does not have a value and value is not required
                            if ((!e.hasValue() && isRequired) || (e.hasValue() && !isRequired)) {
                                childrenPresent = false;
                                break;
                            }
                        }

                        // Else process children as normal
                        else {
                            // Check if children are complete
                            if (!checkComplete(e, isRequired)) {
                                childrenPresent = false;
                                break;
                            }
                        }
                    }
                    return childrenPresent;
                }

                case "extension": {
                    // Get first child of extension
                    LinkedList<Element> children = element.getChildren();

                    // Check value of first child
                    boolean newIsRequired = ((Boolean) children.pop().getValue());

                    // Traverse all the rest of the child nodes of extension
                    for (Element e : children) {
                        if (!checkComplete(e, newIsRequired)) {
                            childrenPresent = false;
                            break;
                        }
                    }

                    return childrenPresent;
                }

                case "component": {
                    Element valueElement = rootWithValues.getChildren().getLast();
                    return (isRequired && valueElement.hasValue());
                }

            }
        }
        return false;
    }
}
