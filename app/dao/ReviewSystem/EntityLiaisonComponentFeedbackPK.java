package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EntityLiaisonComponentFeedbackPK implements Serializable {
    private Short version;
    private String componentId;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private String liaisonEmail;

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

    @Column(name = "liaison_email", nullable = false, length = 100)
    @Id
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
        EntityLiaisonComponentFeedbackPK that = (EntityLiaisonComponentFeedbackPK) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(liaisonEmail, that.liaisonEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId, applicationType, applicationYear, applicationNumber, departmentName, facultyName, liaisonEmail);
    }
}
