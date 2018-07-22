package dao.ReviewSystem;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "liaisonfeedback", schema = "rech_system")
@IdClass(EntityLiaisonfeedbackPK.class)
public class EntityLiaisonfeedback  extends Model {
    private Timestamp feedbackDate;
    private Byte requiresEdits;
    private Timestamp applicationAssignedDate;
    private String liaisonEmail;

    public static Finder<EntityLiaisonfeedbackPK, EntityLiaisonfeedback> find = new Finder<>(EntityLiaisonfeedback.class);

    @Id
    @Column(name = "feedback_date", nullable = false)
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

    @Id
    @Column(name = "application_assigned_date", nullable = false)
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Id
    @Column(name = "liaison_email", nullable = false, length = 100)
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
        EntityLiaisonfeedback that = (EntityLiaisonfeedback) o;
        return Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(requiresEdits, that.requiresEdits) &&
                Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(feedbackDate, requiresEdits, applicationAssignedDate, liaisonEmail);
    }
}
