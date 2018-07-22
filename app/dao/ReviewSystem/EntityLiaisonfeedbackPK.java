package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityLiaisonfeedbackPK implements Serializable {
    private Timestamp feedbackDate;
    private String liaisonEmail;

    public EntityLiaisonfeedbackPK() {
    }

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

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
                Objects.equals(liaisonEmail, that.liaisonEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(feedbackDate, liaisonEmail);
    }
}
