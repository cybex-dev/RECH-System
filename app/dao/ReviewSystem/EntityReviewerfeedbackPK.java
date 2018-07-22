package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EntityReviewerfeedbackPK implements Serializable {
    private Timestamp applicationAssignedDate;
    private String userEmail;

    @Column(name = "application_assigned_date", nullable = false)
    @Id
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Column(name = "user_email", nullable = false, length = 100)
    @Id
    public String getReviewerEmail() {
        return userEmail;
    }

    public void setReviewerEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerfeedbackPK that = (EntityReviewerfeedbackPK) o;
        return Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationAssignedDate, userEmail);
    }
}
