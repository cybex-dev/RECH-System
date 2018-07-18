package DAO.ReviewSystem;

import DAO.ApplicationSystem.EntityEthicsApplication;
import io.ebean.Finder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "reviewerfeedback", schema = "rech_system", catalog = "")
public class EntityReviewerfeedback {
    private Integer reviewerFeedbackId;
    private Timestamp feedbackDate;
    private Timestamp applicationAssignedDate;
    private String reviewerEmail;
    private Integer applicationId;
    private Boolean requiresEdits;
    private Boolean satisfactory;

    public static Finder<Integer, DAO.ReviewSystem.EntityReviewerfeedback> find = new Finder<>(DAO.ReviewSystem.EntityReviewerfeedback.class);

    @Id
    @Column(name = "reviewer_feedback_id", nullable = false)
    public Integer getReviewerFeedbackId() {
        return reviewerFeedbackId;
    }

    public void setReviewerFeedbackId(Integer reviewerFeedbackId) {
        this.reviewerFeedbackId = reviewerFeedbackId;
    }

    @Basic
    @Column(name = "feedback_date", nullable = true)
    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Basic
    @Column(name = "application_assigned_date", nullable = true)
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Basic
    @Column(name = "reviewer_email", nullable = false, length = 100)
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    @Basic
    @Column(name = "application_id", nullable = false)
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Basic
    @Column(name = "requires_edits", nullable = true)
    public Boolean getRequiresEdits() {
        return requiresEdits;
    }

    public void setRequiresEdits(Boolean requiresEdits) {
        this.requiresEdits = requiresEdits;
    }

    @Basic
    @Column(name = "satisfactory", nullable = true)
    public Boolean getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(Boolean satisfactory) {
        this.satisfactory = satisfactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerfeedback that = (EntityReviewerfeedback) o;
        return Objects.equals(reviewerFeedbackId, that.reviewerFeedbackId) &&
                Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(reviewerEmail, that.reviewerEmail) &&
                Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(requiresEdits, that.requiresEdits) &&
                Objects.equals(satisfactory, that.satisfactory);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewerFeedbackId, feedbackDate, applicationAssignedDate, reviewerEmail, applicationId, requiresEdits, satisfactory);
    }

    public static List<EntityEthicsApplication> getApplicationsByReviewer(String reviewerEmail){
        return find.all()
                .stream()
                .filter(entityReviewerfeedback -> entityReviewerfeedback.getReviewerEmail().equals(reviewerEmail))
                .map(entityReviewerfeedback -> EntityEthicsApplication.find.byId(entityReviewerfeedback.applicationId))
                .collect(Collectors.toList());
    }
}
