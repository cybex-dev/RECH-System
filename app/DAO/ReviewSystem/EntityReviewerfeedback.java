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

    public static Finder<Integer, EntityReviewerfeedback> find = new Finder<>(EntityReviewerfeedback.class);


    @Id
    @Column(name = "reviewer_feedback_id")
    public Integer getReviewerFeedbackId() {
        return reviewerFeedbackId;
    }

    public void setReviewerFeedbackId(Integer reviewerFeedbackId) {
        this.reviewerFeedbackId = reviewerFeedbackId;
    }

    @Basic
    @Column(name = "feedback_date")
    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Basic
    @Column(name = "application_assigned_date")
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Basic
    @Column(name = "reviewer_email")
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    @Basic
    @Column(name = "application_id")
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
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
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewerFeedbackId, feedbackDate, applicationAssignedDate, reviewerEmail, applicationId);
    }

    public static List<EntityEthicsApplication> getApplicationsByReviewer(String reviewerEmail){
        return find.all()
                .stream()
                .filter(entityReviewerfeedback -> entityReviewerfeedback.getReviewerEmail().equals(reviewerEmail))
                .map(entityReviewerfeedback -> EntityEthicsApplication.find.byId(entityReviewerfeedback.applicationId))
                .collect(Collectors.toList());
    }

}
