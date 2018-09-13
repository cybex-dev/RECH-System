package dao.ReviewSystem;

import dao.ApplicationSystem.EntityComponentVersionPK;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "liaisoncomponentfeedback", schema = "rech_system")
@IdClass(EntityLiaisonComponentFeedbackPK.class)
public class EntityLiaisonComponentFeedback extends Model {
    private Boolean changeSatisfactory;
    private Timestamp feedbackDate;
    private String liaisonEmail;
    private short version;
    private String componentId;
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;


    public static Finder<EntityLiaisonComponentFeedbackPK, EntityLiaisonComponentFeedback> find = new Finder<>(EntityLiaisonComponentFeedback.class);

    public static List<EntityLiaisonComponentFeedback> GetFeedbackByComponent(EntityComponentVersionPK entityComponentVersionPK) {
        return find.all().stream().filter(feedback -> feedback.componentVersionPrimaryKey().equals(entityComponentVersionPK)).collect(Collectors.toList());
    }

    @Basic
    @Column(name = "change_satisfactory")
    public Boolean getChangeSatisfactory() {
        return changeSatisfactory;
    }

    public void setChangeSatisfactory(Boolean changeSatisfactory) {
        this.changeSatisfactory = changeSatisfactory;
    }

    @Basic
    @Column(name = "feedback_date")
    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Id
    @Column(name = "liaison_email")
    public String getLiaisonEmail() {
        return liaisonEmail;
    }

    public void setLiaisonEmail(String liaisonEmail) {
        this.liaisonEmail = liaisonEmail;
    }

    @Id
    @Column(name = "version")
    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    @Id
    @Column(name = "component_id")
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Id
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Id
    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Id
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Id
    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLiaisonComponentFeedback that = (EntityLiaisonComponentFeedback) o;
        return version == that.version &&
                applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(changeSatisfactory, that.changeSatisfactory) &&
                Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(changeSatisfactory, feedbackDate, liaisonEmail, version, componentId, applicationYear, applicationNumber, applicationType, departmentName, facultyName);
    }


    public dao.ApplicationSystem.EntityEthicsApplicationPK applicationPrimaryKey() {
        dao.ApplicationSystem.EntityEthicsApplicationPK pk = new EntityEthicsApplicationPK();
        pk.setApplicationNumber(applicationNumber);
        pk.setApplicationType(applicationType);
        pk.setApplicationYear(applicationYear);
        pk.setDepartmentName(departmentName);
        pk.setFacultyName(facultyName);
        return pk;
    }

    public EntityComponentVersionPK componentVersionPrimaryKey(){
        EntityComponentVersionPK pk = new EntityComponentVersionPK();
        pk.setApplicationNumber(applicationNumber);
        pk.setApplicationType(applicationType);
        pk.setApplicationYear(applicationYear);
        pk.setDepartmentName(departmentName);
        pk.setFacultyName(facultyName);
        pk.setComponentId(componentId);
        pk.setVersion(version);
        return pk;
    }
}
