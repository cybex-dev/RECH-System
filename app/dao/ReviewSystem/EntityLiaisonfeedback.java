package dao.ReviewSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
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
    private String ethicsApplicationApplicationType;
    private Integer ethicsApplicationApplicationYear;
    private Integer ethicsApplicationApplicationNumber;
    private String ethicsApplicationDepartmentName;
    private String ethicsApplicationFacultyName;
    private String liaisonEmail;
    private Boolean requiresEdits;

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
    @Column(name = "Ethics_Application_application_type", nullable = false, length = 1)
    public String getEthicsApplicationApplicationType() {
        return ethicsApplicationApplicationType;
    }

    public void setEthicsApplicationApplicationType(String ethicsApplicationApplicationType) {
        this.ethicsApplicationApplicationType = ethicsApplicationApplicationType;
    }

    @Id
    @Column(name = "Ethics_Application_application_year", nullable = false)
    public Integer getEthicsApplicationApplicationYear() {
        return ethicsApplicationApplicationYear;
    }

    public void setEthicsApplicationApplicationYear(Integer ethicsApplicationApplicationYear) {
        this.ethicsApplicationApplicationYear = ethicsApplicationApplicationYear;
    }

    @Id
    @Column(name = "Ethics_Application_application_number", nullable = false)
    public Integer getEthicsApplicationApplicationNumber() {
        return ethicsApplicationApplicationNumber;
    }

    public void setEthicsApplicationApplicationNumber(Integer ethicsApplicationApplicationNumber) {
        this.ethicsApplicationApplicationNumber = ethicsApplicationApplicationNumber;
    }

    @Id
    @Column(name = "Ethics_Application_department_name", nullable = false, length = 50)
    public String getEthicsApplicationDepartmentName() {
        return ethicsApplicationDepartmentName;
    }

    public void setEthicsApplicationDepartmentName(String ethicsApplicationDepartmentName) {
        this.ethicsApplicationDepartmentName = ethicsApplicationDepartmentName;
    }

    @Id
    @Column(name = "Ethics_Application_faculty_name", nullable = false, length = 50)
    public String getEthicsApplicationFacultyName() {
        return ethicsApplicationFacultyName;
    }

    public void setEthicsApplicationFacultyName(String ethicsApplicationFacultyName) {
        this.ethicsApplicationFacultyName = ethicsApplicationFacultyName;
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
    @Column(name = "requires_edits", nullable = true)
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
        return Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(ethicsApplicationApplicationType, that.ethicsApplicationApplicationType) &&
                Objects.equals(ethicsApplicationApplicationYear, that.ethicsApplicationApplicationYear) &&
                Objects.equals(ethicsApplicationApplicationNumber, that.ethicsApplicationApplicationNumber) &&
                Objects.equals(ethicsApplicationDepartmentName, that.ethicsApplicationDepartmentName) &&
                Objects.equals(ethicsApplicationFacultyName, that.ethicsApplicationFacultyName) &&
                Objects.equals(liaisonEmail, that.liaisonEmail) &&
                Objects.equals(requiresEdits, that.requiresEdits);
    }

    @Override
    public int hashCode() {

        return Objects.hash(feedbackDate, ethicsApplicationApplicationType, ethicsApplicationApplicationYear, ethicsApplicationApplicationNumber, ethicsApplicationDepartmentName, ethicsApplicationFacultyName, liaisonEmail, requiresEdits);
    }

    public EntityEthicsApplicationPK applicationPrimaryKey() {
        EntityEthicsApplicationPK pk = new EntityEthicsApplicationPK();
        pk.setApplicationNumber(ethicsApplicationApplicationNumber);
        pk.setApplicationType(ethicsApplicationApplicationType);
        pk.setApplicationYear(ethicsApplicationApplicationYear);
        pk.setDepartmentName(ethicsApplicationDepartmentName);
        pk.setFacultyName(ethicsApplicationFacultyName);
        return pk;
    }
}
