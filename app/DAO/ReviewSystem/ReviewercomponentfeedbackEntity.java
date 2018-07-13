package DAO.ReviewSystem;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reviewercomponentfeedback", schema = "rech_system", catalog = "")
@IdClass(ReviewercomponentfeedbackEntityPK.class)
public class ReviewercomponentfeedbackEntity {
    private String componentFeedback;
    private short applicationNumber;
    private String applicationDepartment;
    private int applicationYear;
    private String applicationType;
    private short componentId;
    private short sectionId;
    private byte version;
    private String userEmail;
    private byte componentVersionVersion;
    private short componentVersionSectionId;
    private short componentVersionComponentId;
    private String componentVersionApplicationType;
    private int componentVersionApplicationYear;
    private String componentVersionApplicationDepartment;
    private short componentVersionApplicationNumber;

    @Basic
    @Column(name = "component_feedback")
    public String getComponentFeedback() {
        return componentFeedback;
    }

    public void setComponentFeedback(String componentFeedback) {
        this.componentFeedback = componentFeedback;
    }

    @Id
    @Column(name = "application_number")
    public short getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(short applicationNumber) {
        this.applicationNumber = applicationNumber;
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
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
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
    @Column(name = "component_id")
    public short getComponentId() {
        return componentId;
    }

    public void setComponentId(short componentId) {
        this.componentId = componentId;
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
    @Column(name = "version")
    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
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
    @Column(name = "ComponentVersion_version")
    public byte getComponentVersionVersion() {
        return componentVersionVersion;
    }

    public void setComponentVersionVersion(byte componentVersionVersion) {
        this.componentVersionVersion = componentVersionVersion;
    }

    @Id
    @Column(name = "ComponentVersion_section_id")
    public short getComponentVersionSectionId() {
        return componentVersionSectionId;
    }

    public void setComponentVersionSectionId(short componentVersionSectionId) {
        this.componentVersionSectionId = componentVersionSectionId;
    }

    @Id
    @Column(name = "ComponentVersion_component_id")
    public short getComponentVersionComponentId() {
        return componentVersionComponentId;
    }

    public void setComponentVersionComponentId(short componentVersionComponentId) {
        this.componentVersionComponentId = componentVersionComponentId;
    }

    @Id
    @Column(name = "ComponentVersion_application_type")
    public String getComponentVersionApplicationType() {
        return componentVersionApplicationType;
    }

    public void setComponentVersionApplicationType(String componentVersionApplicationType) {
        this.componentVersionApplicationType = componentVersionApplicationType;
    }

    @Id
    @Column(name = "ComponentVersion_application_year")
    public int getComponentVersionApplicationYear() {
        return componentVersionApplicationYear;
    }

    public void setComponentVersionApplicationYear(int componentVersionApplicationYear) {
        this.componentVersionApplicationYear = componentVersionApplicationYear;
    }

    @Id
    @Column(name = "ComponentVersion_application_department")
    public String getComponentVersionApplicationDepartment() {
        return componentVersionApplicationDepartment;
    }

    public void setComponentVersionApplicationDepartment(String componentVersionApplicationDepartment) {
        this.componentVersionApplicationDepartment = componentVersionApplicationDepartment;
    }

    @Id
    @Column(name = "ComponentVersion_application_number")
    public short getComponentVersionApplicationNumber() {
        return componentVersionApplicationNumber;
    }

    public void setComponentVersionApplicationNumber(short componentVersionApplicationNumber) {
        this.componentVersionApplicationNumber = componentVersionApplicationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewercomponentfeedbackEntity that = (ReviewercomponentfeedbackEntity) o;
        return applicationNumber == that.applicationNumber &&
                applicationYear == that.applicationYear &&
                componentId == that.componentId &&
                sectionId == that.sectionId &&
                version == that.version &&
                componentVersionVersion == that.componentVersionVersion &&
                componentVersionSectionId == that.componentVersionSectionId &&
                componentVersionComponentId == that.componentVersionComponentId &&
                componentVersionApplicationYear == that.componentVersionApplicationYear &&
                componentVersionApplicationNumber == that.componentVersionApplicationNumber &&
                Objects.equals(componentFeedback, that.componentFeedback) &&
                Objects.equals(applicationDepartment, that.applicationDepartment) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(componentVersionApplicationType, that.componentVersionApplicationType) &&
                Objects.equals(componentVersionApplicationDepartment, that.componentVersionApplicationDepartment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(componentFeedback, applicationNumber, applicationDepartment, applicationYear, applicationType, componentId, sectionId, version, userEmail, componentVersionVersion, componentVersionSectionId, componentVersionComponentId, componentVersionApplicationType, componentVersionApplicationYear, componentVersionApplicationDepartment, componentVersionApplicationNumber);
    }
}
