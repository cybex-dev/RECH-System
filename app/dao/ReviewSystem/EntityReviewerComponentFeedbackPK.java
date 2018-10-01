package dao.ReviewSystem;

import dao.ApplicationSystem.EntityComponentVersion;
import dao.ApplicationSystem.EntityComponentVersionPK;
import dao.ApplicationSystem.EntityEthicsApplicationPK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EntityReviewerComponentFeedbackPK implements Serializable {
    private String reviewerEmail;
    private short version;
    private String componentId;
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;

    @Column(name = "reviewer_email")
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    @Column(name = "version")
    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    @Column(name = "component_id")
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
        EntityReviewerComponentFeedbackPK that = (EntityReviewerComponentFeedbackPK) o;
        return version == that.version &&
                applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(reviewerEmail, that.reviewerEmail) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewerEmail, version, componentId, applicationYear, applicationNumber, applicationType, departmentName, facultyName);
    }

    public void setApplicationId(EntityEthicsApplicationPK applicationId) {
        this.applicationYear = applicationId.getApplicationYear();
        this.applicationType = applicationId.getApplicationType();
        this.departmentName = applicationId.getDepartmentName();
        this.facultyName = applicationId.getFacultyName();
        this.applicationNumber = applicationId.getApplicationNumber();
    }

    public void setComponentVersionId(EntityComponentVersionPK componentVersionPK) {
        this.applicationYear = componentVersionPK.getApplicationYear();
        this.applicationType = componentVersionPK.getApplicationType();
        this.departmentName = componentVersionPK.getDepartmentName();
        this.facultyName = componentVersionPK.getFacultyName();
        this.applicationNumber = componentVersionPK.getApplicationNumber();
        this.componentId = componentVersionPK.getComponentId();
        this.version = componentVersionPK.getVersion();
    }
}
