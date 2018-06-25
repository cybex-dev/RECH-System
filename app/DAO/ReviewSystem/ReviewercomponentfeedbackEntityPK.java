package DAO.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ReviewercomponentfeedbackEntityPK implements Serializable {
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

    @Column(name = "application_number")
    @Id
    public short getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(short applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "application_department")
    @Id
    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    @Column(name = "application_year")
    @Id
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_type")
    @Id
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "component_id")
    @Id
    public short getComponentId() {
        return componentId;
    }

    public void setComponentId(short componentId) {
        this.componentId = componentId;
    }

    @Column(name = "section_id")
    @Id
    public short getSectionId() {
        return sectionId;
    }

    public void setSectionId(short sectionId) {
        this.sectionId = sectionId;
    }

    @Column(name = "version")
    @Id
    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    @Column(name = "user_email")
    @Id
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "ComponentVersion_version")
    @Id
    public byte getComponentVersionVersion() {
        return componentVersionVersion;
    }

    public void setComponentVersionVersion(byte componentVersionVersion) {
        this.componentVersionVersion = componentVersionVersion;
    }

    @Column(name = "ComponentVersion_section_id")
    @Id
    public short getComponentVersionSectionId() {
        return componentVersionSectionId;
    }

    public void setComponentVersionSectionId(short componentVersionSectionId) {
        this.componentVersionSectionId = componentVersionSectionId;
    }

    @Column(name = "ComponentVersion_component_id")
    @Id
    public short getComponentVersionComponentId() {
        return componentVersionComponentId;
    }

    public void setComponentVersionComponentId(short componentVersionComponentId) {
        this.componentVersionComponentId = componentVersionComponentId;
    }

    @Column(name = "ComponentVersion_application_type")
    @Id
    public String getComponentVersionApplicationType() {
        return componentVersionApplicationType;
    }

    public void setComponentVersionApplicationType(String componentVersionApplicationType) {
        this.componentVersionApplicationType = componentVersionApplicationType;
    }

    @Column(name = "ComponentVersion_application_year")
    @Id
    public int getComponentVersionApplicationYear() {
        return componentVersionApplicationYear;
    }

    public void setComponentVersionApplicationYear(int componentVersionApplicationYear) {
        this.componentVersionApplicationYear = componentVersionApplicationYear;
    }

    @Column(name = "ComponentVersion_application_department")
    @Id
    public String getComponentVersionApplicationDepartment() {
        return componentVersionApplicationDepartment;
    }

    public void setComponentVersionApplicationDepartment(String componentVersionApplicationDepartment) {
        this.componentVersionApplicationDepartment = componentVersionApplicationDepartment;
    }

    @Column(name = "ComponentVersion_application_number")
    @Id
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
        ReviewercomponentfeedbackEntityPK that = (ReviewercomponentfeedbackEntityPK) o;
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
                Objects.equals(applicationDepartment, that.applicationDepartment) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(componentVersionApplicationType, that.componentVersionApplicationType) &&
                Objects.equals(componentVersionApplicationDepartment, that.componentVersionApplicationDepartment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationNumber, applicationDepartment, applicationYear, applicationType, componentId, sectionId, version, userEmail, componentVersionVersion, componentVersionSectionId, componentVersionComponentId, componentVersionApplicationType, componentVersionApplicationYear, componentVersionApplicationDepartment, componentVersionApplicationNumber);
    }
}
