package dao.ReviewSystem;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reviewerfeedback", schema = "rech_system")
@IdClass(EntityReviewerfeedbackPK.class)
public class EntityReviewerfeedback extends Model {
    private Timestamp applicationAssignedDate;
    private String reviewerEmail;
    private Timestamp feedbackDate;
    private Byte requiresEdits;

    public static Finder<EntityReviewerfeedbackPK, dao.ReviewSystem.EntityReviewerfeedback> find = new Finder<>(dao.ReviewSystem.EntityReviewerfeedback.class);

    @Id
    @Column(name = "application_assigned_date", nullable = false)
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Id
    @Column(name = "reviewer_email", nullable = false, length = 100)
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
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
    @Column(name = "requires_edits", nullable = true)
    public Byte getRequiresEdits() {
        return requiresEdits;
    }

    public void setRequiresEdits(Byte requiresEdits) {
        this.requiresEdits = requiresEdits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerfeedback that = (EntityReviewerfeedback) o;
        return Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(reviewerEmail, that.reviewerEmail) &&
                Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(requiresEdits, that.requiresEdits);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationAssignedDate, reviewerEmail, feedbackDate, requiresEdits);
    }
}
