package dao.ReviewSystem;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "liaisonfeedback", schema = "rech_system")
@IdClass(EntityLiaisonfeedbackPK.class)
public class EntityLiaisonfeedback extends Model {
    private Timestamp feedbackDate;
    private String liaisonEmail;
    private Byte requiresEdits;

    public static Finder<EntityLiaisonfeedbackPK, EntityLiaisonfeedback> find = new Finder<>(EntityLiaisonfeedback.class);

    @Id
    @Column(name = "feedback_date", nullable = false)
    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Id
    @Column(name = "liaison_email", nullable = false, length = 100)
    public String getLiaisonEmail() {
        return liaisonEmail;
    }

    public void setLiaisonEmail(String liaisonEmail) {
        this.liaisonEmail = liaisonEmail;
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
        EntityLiaisonfeedback that = (EntityLiaisonfeedback) o;
        return Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail) &&
                Objects.equals(requiresEdits, that.requiresEdits);
    }

    @Override
    public int hashCode() {

        return Objects.hash(feedbackDate, liaisonEmail, requiresEdits);
    }
}
