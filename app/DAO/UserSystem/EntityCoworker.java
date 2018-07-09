package DAO.UserSystem;

import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coworker", schema = "rech_system", catalog = "")
@IdClass(EntityCoworkerPK.class)
public class EntityCoworker extends Model {
    private String userEmail;
    private Integer applicationId;
    private String affiliation;

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

    @Basic
    @Column(name = "affiliation")
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCoworker that = (EntityCoworker) o;
        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(affiliation, that.affiliation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userEmail, applicationId, affiliation);
    }
}
