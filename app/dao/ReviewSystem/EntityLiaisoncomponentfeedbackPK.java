package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EntityLiaisoncomponentfeedbackPK implements Serializable {
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

    @Column(name = "version", nullable = false)
    @Id
    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    @Column(name = "component_id", nullable = false, length = 50)
    @Id
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Column(name = "application_type", nullable = false, length = 1)
    @Id
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "application_year", nullable = false)
    @Id
    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_number", nullable = false)
    @Id
    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "department_name", nullable = false, length = 50)
    @Id
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "faculty_name", nullable = false, length = 50)
    @Id
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Column(name = "LiaisonFeedback_feedback_date", nullable = false)
    @Id
    public Timestamp getLiaisonFeedbackFeedbackDate() {
        return liaisonFeedbackFeedbackDate;
    }

    public void setLiaisonFeedbackFeedbackDate(Timestamp liaisonFeedbackFeedbackDate) {
        this.liaisonFeedbackFeedbackDate = liaisonFeedbackFeedbackDate;
    }

    @Column(name = "LiaisonFeedback_Ethics_Application_application_type", nullable = false, length = 1)
    @Id
    public String getLiaisonFeedbackEthicsApplicationApplicationType() {
        return liaisonFeedbackEthicsApplicationApplicationType;
    }

    public void setLiaisonFeedbackEthicsApplicationApplicationType(String liaisonFeedbackEthicsApplicationApplicationType) {
        this.liaisonFeedbackEthicsApplicationApplicationType = liaisonFeedbackEthicsApplicationApplicationType;
    }

    @Column(name = "LiaisonFeedback_Ethics_Application_application_year", nullable = false)
    @Id
    public Integer getLiaisonFeedbackEthicsApplicationApplicationYear() {
        return liaisonFeedbackEthicsApplicationApplicationYear;
    }

    public void setLiaisonFeedbackEthicsApplicationApplicationYear(Integer liaisonFeedbackEthicsApplicationApplicationYear) {
        this.liaisonFeedbackEthicsApplicationApplicationYear = liaisonFeedbackEthicsApplicationApplicationYear;
    }

    @Column(name = "LiaisonFeedback_Ethics_Application_application_number", nullable = false)
    @Id
    public Integer getLiaisonFeedbackEthicsApplicationApplicationNumber() {
        return liaisonFeedbackEthicsApplicationApplicationNumber;
    }

    public void setLiaisonFeedbackEthicsApplicationApplicationNumber(Integer liaisonFeedbackEthicsApplicationApplicationNumber) {
        this.liaisonFeedbackEthicsApplicationApplicationNumber = liaisonFeedbackEthicsApplicationApplicationNumber;
    }

    @Column(name = "LiaisonFeedback_Ethics_Application_department_name", nullable = false, length = 50)
    @Id
    public String getLiaisonFeedbackEthicsApplicationDepartmentName() {
        return liaisonFeedbackEthicsApplicationDepartmentName;
    }

    public void setLiaisonFeedbackEthicsApplicationDepartmentName(String liaisonFeedbackEthicsApplicationDepartmentName) {
        this.liaisonFeedbackEthicsApplicationDepartmentName = liaisonFeedbackEthicsApplicationDepartmentName;
    }

    @Column(name = "LiaisonFeedback_Ethics_Application_faculty_name", nullable = false, length = 50)
    @Id
    public String getLiaisonFeedbackEthicsApplicationFacultyName() {
        return liaisonFeedbackEthicsApplicationFacultyName;
    }

    public void setLiaisonFeedbackEthicsApplicationFacultyName(String liaisonFeedbackEthicsApplicationFacultyName) {
        this.liaisonFeedbackEthicsApplicationFacultyName = liaisonFeedbackEthicsApplicationFacultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLiaisoncomponentfeedbackPK that = (EntityLiaisoncomponentfeedbackPK) o;
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
                Objects.equals(liaisonFeedbackEthicsApplicationFacultyName, that.liaisonFeedbackEthicsApplicationFacultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId, applicationType, applicationYear, applicationNumber, departmentName, facultyName, liaisonFeedbackFeedbackDate, liaisonFeedbackEthicsApplicationApplicationType, liaisonFeedbackEthicsApplicationApplicationYear, liaisonFeedbackEthicsApplicationApplicationNumber, liaisonFeedbackEthicsApplicationDepartmentName, liaisonFeedbackEthicsApplicationFacultyName);
    }
}
