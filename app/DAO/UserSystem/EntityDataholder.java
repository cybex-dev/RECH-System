package DAO.UserSystem;

import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dataholder", schema = "rech_system", catalog = "")
@IdClass(EntityDataholderPK.class)
public class EntityDataholder  extends Model {
    private String userEmail;
    private Integer applicationId;

    @Id
    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Id
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
        EntityDataholder that = (EntityDataholder) o;
        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userEmail, applicationId);
    }
}
