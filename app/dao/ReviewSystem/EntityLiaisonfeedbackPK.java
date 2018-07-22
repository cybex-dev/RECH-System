package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EntityLiaisonfeedbackPK implements Serializable {
    private Timestamp feedbackDate;
    private Timestamp applicationAssignedDate;
    private String liaisonEmail;

    @Column(name = "feedback_date", nullable = false)
    @Id
    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Column(name = "application_assigned_date", nullable = false)
    @Id
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Column(name = "liaison_email", nullable = false, length = 100)
    @Id
    public String getLiaisonEmail() {
        return liaisonEmail;
    }

    public void setLiaisonEmail(String liaisonEmail) {
        this.liaisonEmail = liaisonEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLiaisonfeedbackPK that = (EntityLiaisonfeedbackPK) o;
        return Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(feedbackDate, applicationAssignedDate, liaisonEmail);
    }
}
