package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityReviewercomponentfeedbackPK implements Serializable {
    private Short version;
    private String componentId;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private Timestamp reviewerFeedbackApplicationAssignedDate;
    private String reviewerFeedbackEthicsApplicationApplicationType;
    private Integer reviewerFeedbackEthicsApplicationApplicationYear;
    private Integer reviewerFeedbackEthicsApplicationApplicationNumber;
    private String reviewerFeedbackEthicsApplicationDepartmentName;
    private String reviewerFeedbackEthicsApplicationFacultyName;

    @Column(name = "version", nullable = false)
    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    @Column(name = "component_id", nullable = false, length = 50)
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Column(name = "application_type", nullable = false, length = 1)
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "application_year", nullable = false)
    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_number", nullable = false)
    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "department_name", nullable = false, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "faculty_name", nullable = false, length = 50)
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Column(name = "ReviewerFeedback_application_assigned_date", nullable = false)
    public Timestamp getReviewerFeedbackApplicationAssignedDate() {
        return reviewerFeedbackApplicationAssignedDate;
    }

    public void setReviewerFeedbackApplicationAssignedDate(Timestamp reviewerFeedbackApplicationAssignedDate) {
        this.reviewerFeedbackApplicationAssignedDate = reviewerFeedbackApplicationAssignedDate;
    }

    @Column(name = "ReviewerFeedback_Ethics_Application_application_type", nullable = false, length = 1)
    public String getReviewerFeedbackEthicsApplicationApplicationType() {
        return reviewerFeedbackEthicsApplicationApplicationType;
    }

    public void setReviewerFeedbackEthicsApplicationApplicationType(String reviewerFeedbackEthicsApplicationApplicationType) {
        this.reviewerFeedbackEthicsApplicationApplicationType = reviewerFeedbackEthicsApplicationApplicationType;
    }

    @Column(name = "ReviewerFeedback_Ethics_Application_application_year", nullable = false)
    public Integer getReviewerFeedbackEthicsApplicationApplicationYear() {
        return reviewerFeedbackEthicsApplicationApplicationYear;
    }

    public void setReviewerFeedbackEthicsApplicationApplicationYear(Integer reviewerFeedbackEthicsApplicationApplicationYear) {
        this.reviewerFeedbackEthicsApplicationApplicationYear = reviewerFeedbackEthicsApplicationApplicationYear;
    }

    @Column(name = "ReviewerFeedback_Ethics_Application_application_number", nullable = false)
    public Integer getReviewerFeedbackEthicsApplicationApplicationNumber() {
        return reviewerFeedbackEthicsApplicationApplicationNumber;
    }

    public void setReviewerFeedbackEthicsApplicationApplicationNumber(Integer reviewerFeedbackEthicsApplicationApplicationNumber) {
        this.reviewerFeedbackEthicsApplicationApplicationNumber = reviewerFeedbackEthicsApplicationApplicationNumber;
    }

    @Column(name = "ReviewerFeedback_Ethics_Application_department_name", nullable = false, length = 50)
    public String getReviewerFeedbackEthicsApplicationDepartmentName() {
        return reviewerFeedbackEthicsApplicationDepartmentName;
    }

    public void setReviewerFeedbackEthicsApplicationDepartmentName(String reviewerFeedbackEthicsApplicationDepartmentName) {
        this.reviewerFeedbackEthicsApplicationDepartmentName = reviewerFeedbackEthicsApplicationDepartmentName;
    }

    @Column(name = "ReviewerFeedback_Ethics_Application_faculty_name", nullable = false, length = 50)
    public String getReviewerFeedbackEthicsApplicationFacultyName() {
        return reviewerFeedbackEthicsApplicationFacultyName;
    }

    public void setReviewerFeedbackEthicsApplicationFacultyName(String reviewerFeedbackEthicsApplicationFacultyName) {
        this.reviewerFeedbackEthicsApplicationFacultyName = reviewerFeedbackEthicsApplicationFacultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewercomponentfeedbackPK that = (EntityReviewercomponentfeedbackPK) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(reviewerFeedbackApplicationAssignedDate, that.reviewerFeedbackApplicationAssignedDate) &&
                Objects.equals(reviewerFeedbackEthicsApplicationApplicationType, that.reviewerFeedbackEthicsApplicationApplicationType) &&
                Objects.equals(reviewerFeedbackEthicsApplicationApplicationYear, that.reviewerFeedbackEthicsApplicationApplicationYear) &&
                Objects.equals(reviewerFeedbackEthicsApplicationApplicationNumber, that.reviewerFeedbackEthicsApplicationApplicationNumber) &&
                Objects.equals(reviewerFeedbackEthicsApplicationDepartmentName, that.reviewerFeedbackEthicsApplicationDepartmentName) &&
                Objects.equals(reviewerFeedbackEthicsApplicationFacultyName, that.reviewerFeedbackEthicsApplicationFacultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId, applicationType, applicationYear, applicationNumber, departmentName, facultyName, reviewerFeedbackApplicationAssignedDate, reviewerFeedbackEthicsApplicationApplicationType, reviewerFeedbackEthicsApplicationApplicationYear, reviewerFeedbackEthicsApplicationApplicationNumber, reviewerFeedbackEthicsApplicationDepartmentName, reviewerFeedbackEthicsApplicationFacultyName);
    }
}
