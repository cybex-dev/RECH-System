package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityReviewerfeedbackPK implements Serializable {
    private Timestamp applicationAssignedDate;
    private String ethicsApplicationApplicationType;
    private Integer ethicsApplicationApplicationYear;
    private Integer ethicsApplicationApplicationNumber;
    private String ethicsApplicationDepartmentName;
    private String ethicsApplicationFacultyName;

    @Column(name = "application_assigned_date", nullable = false)
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Column(name = "Ethics_Application_application_type", nullable = false, length = 1)
    public String getEthicsApplicationApplicationType() {
        return ethicsApplicationApplicationType;
    }

    public void setEthicsApplicationApplicationType(String ethicsApplicationApplicationType) {
        this.ethicsApplicationApplicationType = ethicsApplicationApplicationType;
    }

    @Column(name = "Ethics_Application_application_year", nullable = false)
    public Integer getEthicsApplicationApplicationYear() {
        return ethicsApplicationApplicationYear;
    }

    public void setEthicsApplicationApplicationYear(Integer ethicsApplicationApplicationYear) {
        this.ethicsApplicationApplicationYear = ethicsApplicationApplicationYear;
    }

    @Column(name = "Ethics_Application_application_number", nullable = false)
    public Integer getEthicsApplicationApplicationNumber() {
        return ethicsApplicationApplicationNumber;
    }

    public void setEthicsApplicationApplicationNumber(Integer ethicsApplicationApplicationNumber) {
        this.ethicsApplicationApplicationNumber = ethicsApplicationApplicationNumber;
    }

    @Column(name = "Ethics_Application_department_name", nullable = false, length = 50)
    public String getEthicsApplicationDepartmentName() {
        return ethicsApplicationDepartmentName;
    }

    public void setEthicsApplicationDepartmentName(String ethicsApplicationDepartmentName) {
        this.ethicsApplicationDepartmentName = ethicsApplicationDepartmentName;
    }

    @Column(name = "Ethics_Application_faculty_name", nullable = false, length = 50)
    public String getEthicsApplicationFacultyName() {
        return ethicsApplicationFacultyName;
    }

    public void setEthicsApplicationFacultyName(String ethicsApplicationFacultyName) {
        this.ethicsApplicationFacultyName = ethicsApplicationFacultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerfeedbackPK that = (EntityReviewerfeedbackPK) o;
        return Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(ethicsApplicationApplicationType, that.ethicsApplicationApplicationType) &&
                Objects.equals(ethicsApplicationApplicationYear, that.ethicsApplicationApplicationYear) &&
                Objects.equals(ethicsApplicationApplicationNumber, that.ethicsApplicationApplicationNumber) &&
                Objects.equals(ethicsApplicationDepartmentName, that.ethicsApplicationDepartmentName) &&
                Objects.equals(ethicsApplicationFacultyName, that.ethicsApplicationFacultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationAssignedDate, ethicsApplicationApplicationType, ethicsApplicationApplicationYear, ethicsApplicationApplicationNumber, ethicsApplicationDepartmentName, ethicsApplicationFacultyName);
    }
}
