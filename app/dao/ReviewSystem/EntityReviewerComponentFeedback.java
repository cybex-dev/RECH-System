package dao.ReviewSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import io.ebean.Finder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ReviewerComponentFeedback", schema = "rech_system")
@IdClass(EntityReviewerComponentFeedbackPK.class)
public class EntityReviewerComponentFeedback {
    private Short version;
    private String componentId;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private String reviewerEmail;
    private String componentFeedback;
    private Timestamp feedbackDate;

    public static Finder<EntityReviewerComponentFeedbackPK, EntityReviewerComponentFeedback> find = new Finder<>(dao.ReviewSystem.EntityReviewerComponentFeedback.class);

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
    @Column(name = "reviewer_email", nullable = false, length = 100)
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    @Basic
    @Column(name = "component_feedback", nullable = true, length = 255)
    public String getComponentFeedback() {
        return componentFeedback;
    }

    public void setComponentFeedback(String componentFeedback) {
        this.componentFeedback = componentFeedback;
    }

    @Basic
    @Column(name = "feedback_date", nullable = true)
    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerComponentFeedback that = (EntityReviewerComponentFeedback) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(reviewerEmail, that.reviewerEmail) &&
                Objects.equals(componentFeedback, that.componentFeedback) &&
                Objects.equals(feedbackDate, that.feedbackDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId, applicationType, applicationYear, applicationNumber, departmentName, facultyName, reviewerEmail, componentFeedback, feedbackDate);
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
