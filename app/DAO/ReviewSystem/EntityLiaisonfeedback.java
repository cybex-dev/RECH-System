package DAO.ReviewSystem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "liaisonfeedback", schema = "rech_system", catalog = "")
public class EntityLiaisonfeedback {
    private Integer liaisonFeedbackId;
    private Timestamp feedbackDate;
    private Timestamp applicationAssignedDate;
    private String liaisonEmail;
    private Integer applicationId;
    private Boolean requiresEdits;

    @Id
    @Column(name = "liaison_feedback_id", nullable = false)
    public Integer getLiaisonFeedbackId() {
        return liaisonFeedbackId;
    }

    public void setLiaisonFeedbackId(Integer liaisonFeedbackId) {
        this.liaisonFeedbackId = liaisonFeedbackId;
    }

    @Basic
    @Column(name = "feedback_date", nullable = false)
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
    @Column(name = "liaison_email", nullable = false, length = 100)
    public String getLiaisonEmail() {
        return liaisonEmail;
    }

    public void setLiaisonEmail(String liaisonEmail) {
        this.liaisonEmail = liaisonEmail;
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
    @Column(name = "requires_edits", nullable = true, length = 45)
    public Boolean getRequiresEdits() {
        return requiresEdits;
    }

    public void setRequiresEdits(Boolean requiresEdits) {
        this.requiresEdits = requiresEdits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLiaisonfeedback that = (EntityLiaisonfeedback) o;
        return Objects.equals(liaisonFeedbackId, that.liaisonFeedbackId) &&
                Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail) &&
                Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(requiresEdits, that.requiresEdits);
    }

    @Override
    public int hashCode() {

        return Objects.hash(liaisonFeedbackId, feedbackDate, applicationAssignedDate, liaisonEmail, applicationId, requiresEdits);
    }
}
