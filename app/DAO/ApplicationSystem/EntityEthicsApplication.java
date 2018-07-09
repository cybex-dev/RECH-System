package DAO.ApplicationSystem;

import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ethics_application", schema = "rech_system", catalog = "")
public class EntityEthicsApplication extends Model {
    private Integer applicationId;
    private Integer applicationNumber;
    private String applicationType;
    private Integer applicationYear;
    private String departmentName;
    private String facultyName;
    private Boolean isSubmitted;
    private String dateSubmitted;
    private String dateApproved;
    private String piId;
    private String piApprovedDate;
    private String prpId;
    private String prpApprovedDate;
    private String hodId;
    private String hodApproved;
    private String rtiId;
    private String rtiApproved;
    private String liaisonId;

    @Id
    @Column(name = "application_id")
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Basic
    @Column(name = "application_number")
    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Basic
    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Basic
    @Column(name = "application_year")
    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Basic
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Basic
    @Column(name = "is_submitted")
    public Boolean getSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(Boolean submitted) {
        isSubmitted = submitted;
    }

    @Basic
    @Column(name = "date_submitted")
    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Basic
    @Column(name = "date_approved")
    public String getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }

    @Basic
    @Column(name = "pi_id")
    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    @Basic
    @Column(name = "pi_approved_date")
    public String getPiApprovedDate() {
        return piApprovedDate;
    }

    public void setPiApprovedDate(String piApprovedDate) {
        this.piApprovedDate = piApprovedDate;
    }

    @Basic
    @Column(name = "prp_id")
    public String getPrpId() {
        return prpId;
    }

    public void setPrpId(String prpId) {
        this.prpId = prpId;
    }

    @Basic
    @Column(name = "prp_approved_date")
    public String getPrpApprovedDate() {
        return prpApprovedDate;
    }

    public void setPrpApprovedDate(String prpApprovedDate) {
        this.prpApprovedDate = prpApprovedDate;
    }

    @Basic
    @Column(name = "hod_id")
    public String getHodId() {
        return hodId;
    }

    public void setHodId(String hodId) {
        this.hodId = hodId;
    }

    @Basic
    @Column(name = "hod_approved")
    public String getHodApproved() {
        return hodApproved;
    }

    public void setHodApproved(String hodApproved) {
        this.hodApproved = hodApproved;
    }

    @Basic
    @Column(name = "rti_id")
    public String getRtiId() {
        return rtiId;
    }

    public void setRtiId(String rtiId) {
        this.rtiId = rtiId;
    }

    @Basic
    @Column(name = "rti_approved")
    public String getRtiApproved() {
        return rtiApproved;
    }

    public void setRtiApproved(String rtiApproved) {
        this.rtiApproved = rtiApproved;
    }

    @Basic
    @Column(name = "liaison_id")
    public String getLiaisonId() {
        return liaisonId;
    }

    public void setLiaisonId(String liaisonId) {
        this.liaisonId = liaisonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEthicsApplication that = (EntityEthicsApplication) o;
        return Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(isSubmitted, that.isSubmitted) &&
                Objects.equals(dateSubmitted, that.dateSubmitted) &&
                Objects.equals(dateApproved, that.dateApproved) &&
                Objects.equals(piId, that.piId) &&
                Objects.equals(piApprovedDate, that.piApprovedDate) &&
                Objects.equals(prpId, that.prpId) &&
                Objects.equals(prpApprovedDate, that.prpApprovedDate) &&
                Objects.equals(hodId, that.hodId) &&
                Objects.equals(hodApproved, that.hodApproved) &&
                Objects.equals(rtiId, that.rtiId) &&
                Objects.equals(rtiApproved, that.rtiApproved) &&
                Objects.equals(liaisonId, that.liaisonId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationId, applicationNumber, applicationType, applicationYear, departmentName, facultyName, isSubmitted, dateSubmitted, dateApproved, piId, piApprovedDate, prpId, prpApprovedDate, hodId, hodApproved, rtiId, rtiApproved, liaisonId);
    }
}
