package dao.ReviewSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "liaisoncomponentfeedback", schema = "rech_system")
@IdClass(EntityLiaisoncomponentfeedbackPK.class)
public class EntityLiaisoncomponentfeedback {
    private Short version;
    private String componentId;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private Timestamp liaisonFeedbackFeedbackDate;
    private String liaisonFeedbackEthicsApplicationApplicationType;
    private Integer liaisonFeedbackEthicsApplicationApplicationYear;
    private Integer liaisonFeedbackEthicsApplicationApplicationNumber;
    private String liaisonFeedbackEthicsApplicationDepartmentName;
    private String liaisonFeedbackEthicsApplicationFacultyName;
    private String componentFeedback;

    @Id
    @Column(name = "version", nullable = false)
    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    @Id
    @Column(name = "component_id", nullable = false, length = 50)
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Id
    @Column(name = "application_type", nullable = false, length = 1)
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Id
    @Column(name = "application_year", nullable = false)
    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_number", nullable = false)
    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Id
    @Column(name = "department_name", nullable = false, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Id
    @Column(name = "faculty_name", nullable = false, length = 50)
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Id
    @Column(name = "LiaisonFeedback_feedback_date", nullable = false)
    public Timestamp getLiaisonFeedbackFeedbackDate() {
        return liaisonFeedbackFeedbackDate;
    }

    public void setLiaisonFeedbackFeedbackDate(Timestamp liaisonFeedbackFeedbackDate) {
        this.liaisonFeedbackFeedbackDate = liaisonFeedbackFeedbackDate;
    }

    @Id
    @Column(name = "LiaisonFeedback_Ethics_Application_application_type", nullable = false, length = 1)
    public String getLiaisonFeedbackEthicsApplicationApplicationType() {
        return liaisonFeedbackEthicsApplicationApplicationType;
    }

    public void setLiaisonFeedbackEthicsApplicationApplicationType(String liaisonFeedbackEthicsApplicationApplicationType) {
        this.liaisonFeedbackEthicsApplicationApplicationType = liaisonFeedbackEthicsApplicationApplicationType;
    }

    @Id
    @Column(name = "LiaisonFeedback_Ethics_Application_application_year", nullable = false)
    public Integer getLiaisonFeedbackEthicsApplicationApplicationYear() {
        return liaisonFeedbackEthicsApplicationApplicationYear;
    }

    public void setLiaisonFeedbackEthicsApplicationApplicationYear(Integer liaisonFeedbackEthicsApplicationApplicationYear) {
        this.liaisonFeedbackEthicsApplicationApplicationYear = liaisonFeedbackEthicsApplicationApplicationYear;
    }

    @Id
    @Column(name = "LiaisonFeedback_Ethics_Application_application_number", nullable = false)
    public Integer getLiaisonFeedbackEthicsApplicationApplicationNumber() {
        return liaisonFeedbackEthicsApplicationApplicationNumber;
    }

    public void setLiaisonFeedbackEthicsApplicationApplicationNumber(Integer liaisonFeedbackEthicsApplicationApplicationNumber) {
        this.liaisonFeedbackEthicsApplicationApplicationNumber = liaisonFeedbackEthicsApplicationApplicationNumber;
    }

    @Id
    @Column(name = "LiaisonFeedback_Ethics_Application_department_name", nullable = false, length = 50)
    public String getLiaisonFeedbackEthicsApplicationDepartmentName() {
        return liaisonFeedbackEthicsApplicationDepartmentName;
    }

    public void setLiaisonFeedbackEthicsApplicationDepartmentName(String liaisonFeedbackEthicsApplicationDepartmentName) {
        this.liaisonFeedbackEthicsApplicationDepartmentName = liaisonFeedbackEthicsApplicationDepartmentName;
    }

    @Id
    @Column(name = "LiaisonFeedback_Ethics_Application_faculty_name", nullable = false, length = 50)
    public String getLiaisonFeedbackEthicsApplicationFacultyName() {
        return liaisonFeedbackEthicsApplicationFacultyName;
    }

    public void setLiaisonFeedbackEthicsApplicationFacultyName(String liaisonFeedbackEthicsApplicationFacultyName) {
        this.liaisonFeedbackEthicsApplicationFacultyName = liaisonFeedbackEthicsApplicationFacultyName;
    }

    @Basic
    @Column(name = "component_feedback", nullable = true, length = 255)
    public String getComponentFeedback() {
        return componentFeedback;
    }

    public void setComponentFeedback(String componentFeedback) {
        this.componentFeedback = componentFeedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLiaisoncomponentfeedback that = (EntityLiaisoncomponentfeedback) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(liaisonFeedbackFeedbackDate, that.liaisonFeedbackFeedbackDate) &&
                Objects.equals(liaisonFeedbackEthicsApplicationApplicationType, that.liaisonFeedbackEthicsApplicationApplicationType) &&
                Objects.equals(liaisonFeedbackEthicsApplicationApplicationYear, that.liaisonFeedbackEthicsApplicationApplicationYear) &&
                Objects.equals(liaisonFeedbackEthicsApplicationApplicationNumber, that.liaisonFeedbackEthicsApplicationApplicationNumber) &&
                Objects.equals(liaisonFeedbackEthicsApplicationDepartmentName, that.liaisonFeedbackEthicsApplicationDepartmentName) &&
                Objects.equals(liaisonFeedbackEthicsApplicationFacultyName, that.liaisonFeedbackEthicsApplicationFacultyName) &&
                Objects.equals(componentFeedback, that.componentFeedback);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId, applicationType, applicationYear, applicationNumber, departmentName, facultyName, liaisonFeedbackFeedbackDate, liaisonFeedbackEthicsApplicationApplicationType, liaisonFeedbackEthicsApplicationApplicationYear, liaisonFeedbackEthicsApplicationApplicationNumber, liaisonFeedbackEthicsApplicationDepartmentName, liaisonFeedbackEthicsApplicationFacultyName, componentFeedback);
    }

    public EntityEthicsApplicationPK applicationPrimaryKey() {
        EntityEthicsApplicationPK pk = new EntityEthicsApplicationPK();
        pk.setApplicationNumber(applicationNumber);
        pk.setApplicationType(applicationType);
        pk.setApplicationYear(applicationYear);
        pk.setDepartmentName(departmentName);
        pk.setFacultyName(facultyName);
        return pk;
    }
}
