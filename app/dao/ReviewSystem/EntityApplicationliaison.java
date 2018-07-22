package dao.ReviewSystem;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "applicationliaison", schema = "rech_system")
@IdClass(EntityApplicationliaisonPK.class)
public class EntityApplicationliaison  extends Model {
    private Timestamp applicationAssignedDate;
    private String liaisonEmail;

    public static Finder<EntityApplicationliaisonPK, EntityApplicationliaison> find = new Finder<>(EntityApplicationliaison.class);

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
        EntityApplicationliaison that = (EntityApplicationliaison) o;
        return Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationAssignedDate, liaisonEmail);
    }
}
