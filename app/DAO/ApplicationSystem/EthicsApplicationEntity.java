package DAO.ApplicationSystem;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ethics_application", schema = "rech_system", catalog = "")
@IdClass(EthicsApplicationEntityPK.class)
public class EthicsApplicationEntity {
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;
    private Byte isSubmitted;
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

    @Basic
    @Column(name = "is_submitted")
    public Byte getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Byte isSubmitted) {
        this.isSubmitted = isSubmitted;
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
        EthicsApplicationEntity that = (EthicsApplicationEntity) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment) &&
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

        return Objects.hash(applicationType, applicationYear, applicationDepartment, applicationNumber, isSubmitted, dateSubmitted, dateApproved, piId, piApprovedDate, prpId, prpApprovedDate, hodId, hodApproved, rtiId, rtiApproved, liaisonId);
    }
}
