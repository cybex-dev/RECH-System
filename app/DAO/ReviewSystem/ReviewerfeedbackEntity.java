package DAO.ReviewSystem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reviewerfeedback", schema = "rech_system", catalog = "")
@IdClass(ReviewerfeedbackEntityPK.class)
public class ReviewerfeedbackEntity {
    private Timestamp feedbackDate;
    private Timestamp applicationAssignedDate;
    private String userEmail;
    private byte version;
    private short sectionId;
    private short componentId;
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;

    @Basic
    @Column(name = "feedback_date")
    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Basic
    @Column(name = "application_assigned_date")
    public Timestamp getApplicationAssignedDate() {
        return applicationAssignedDate;
    }

    public void setApplicationAssignedDate(Timestamp applicationAssignedDate) {
        this.applicationAssignedDate = applicationAssignedDate;
    }

    @Id
    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Id
    @Column(name = "version")
    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    @Id
    @Column(name = "section_id")
    public short getSectionId() {
        return sectionId;
    }

    public void setSectionId(short sectionId) {
        this.sectionId = sectionId;
    }

    @Id
    @Column(name = "component_id")
    public short getComponentId() {
        return componentId;
    }

    public void setComponentId(short componentId) {
        this.componentId = componentId;
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
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_department")
    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    @Id
    @Column(name = "application_number")
    public short getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(short applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewerfeedbackEntity that = (ReviewerfeedbackEntity) o;
        return version == that.version &&
                sectionId == that.sectionId &&
                componentId == that.componentId &&
                applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(applicationAssignedDate, that.applicationAssignedDate) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(feedbackDate, applicationAssignedDate, userEmail, version, sectionId, componentId, applicationType, applicationYear, applicationDepartment, applicationNumber);
    }
}
