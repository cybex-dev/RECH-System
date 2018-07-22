package dao.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityLiaisoncomponentfeedbackPK implements Serializable {
    private Short version;
    private String componentId;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private Timestamp feedbackDate;
    private String liaisonEmail;

    public EntityLiaisoncomponentfeedbackPK() {
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

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
        EntityLiaisoncomponentfeedbackPK that = (EntityLiaisoncomponentfeedbackPK) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(feedbackDate, that.feedbackDate) &&
                Objects.equals(liaisonEmail, that.liaisonEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId, applicationType, applicationYear, applicationNumber, departmentName, facultyName, feedbackDate, liaisonEmail);
    }
}
