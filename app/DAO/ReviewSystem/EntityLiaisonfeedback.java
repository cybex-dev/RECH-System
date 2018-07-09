package DAO.ReviewSystem;

import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "liaisonfeedback", schema = "rech_system", catalog = "")
public class EntityLiaisonfeedback  extends Model {
    private Integer liaisonFeedbackId;
    private Timestamp feedbackDate;
    private Timestamp applicationAssignedDate;
    private String liaisonEmail;
    private Integer applicationId;

    @Id
    @Column(name = "liaison_feedback_id")
    public Integer getLiaisonFeedbackId() {
        return liaisonFeedbackId;
    }

    public void setLiaisonFeedbackId(Integer liaisonFeedbackId) {
        this.liaisonFeedbackId = liaisonFeedbackId;
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
    @Column(name = "liaison_email")
    public String getLiaisonEmail() {
        return liaisonEmail;
    }

    public void setLiaisonEmail(String liaisonEmail) {
        this.liaisonEmail = liaisonEmail;
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
        EntityLiaisonfeedback that = (EntityLiaisonfeedback) o;
        return Objects.equals(liaisonFeedbackId, that.liaisonFeedbackId) &&
                Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail) &&
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(liaisonFeedbackId, feedbackDate, applicationAssignedDate, liaisonEmail, applicationId);
    }
}
