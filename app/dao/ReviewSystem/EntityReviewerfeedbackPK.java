package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityReviewerfeedbackPK implements Serializable {
    private Timestamp applicationAssignedDate;
    private String reviewerEmail;

    public EntityReviewerfeedbackPK() {
    }

    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerfeedbackPK that = (EntityReviewerfeedbackPK) o;
        return Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(reviewerEmail, that.reviewerEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationAssignedDate, reviewerEmail);
    }
}
