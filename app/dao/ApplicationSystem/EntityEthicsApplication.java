package dao.ApplicationSystem;

import dao.ReviewSystem.EntityReviewercomponentfeedback;
import dao.ReviewSystem.EntityReviewerfeedback;
import dao.UserSystem.EntityPerson;
import io.ebean.Finder;
import io.ebean.Model;
import models.UserSystem.UserType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "ethics_application", schema = "rech_system")
@IdClass(EntityEthicsApplicationPK.class)
public class EntityEthicsApplication extends Model {
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private Timestamp dateSubmitted;
    private Timestamp dateApproved;
    private String piId;
    private Timestamp piApprovedDate;
    private String prpId;
    private Timestamp prpReviewDate;
    private String hodId;
    private Timestamp hodReviewDate;
    private Timestamp hodFinalReviewDate;
    private Boolean hodApplicationReviewApproved;
    private Boolean hodFinalApplicationApproval;
    private String rtiId;
    private Timestamp rtiReviewDate;
    private Timestamp rtiFinalReviewDate;
    private Boolean rtiApplicationReviewApproved;
    private Boolean rtiFinalApplicationApproval;
    private Short internalStatus;
    private String liaisonId;
    private Timestamp liaisonAssignedDate;
    private Short applicationLevel;

    public static Finder<dao.ApplicationSystem.EntityEthicsApplicationPK, EntityEthicsApplication> find = new Finder<>(EntityEthicsApplication.class);

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

    @Basic
    @Column(name = "date_submitted", nullable = true)
    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Basic
    @Column(name = "date_approved", nullable = true)
    public Timestamp getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Timestamp dateApproved) {
        this.dateApproved = dateApproved;
    }

    @Basic
    @Column(name = "pi_id", nullable = false, length = 100)
    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    @Basic
    @Column(name = "pi_approved_date", nullable = true)
    public Timestamp getPiApprovedDate() {
        return piApprovedDate;
    }

    public void setPiApprovedDate(Timestamp piApprovedDate) {
        this.piApprovedDate = piApprovedDate;
    }

    @Basic
    @Column(name = "prp_id", nullable = false, length = 100)
    public String getPrpId() {
        return prpId;
    }

    public void setPrpId(String prpId) {
        this.prpId = prpId;
    }

    @Basic
    @Column(name = "prp_approved_date", nullable = true)
    public Timestamp getPrpReviewDate() {
        return prpReviewDate;
    }

    public void setPrpReviewDate(Timestamp prpApprovedDate) {
        this.prpReviewDate = prpApprovedDate;
    }

    @Basic
    @Column(name = "hod_id", nullable = true, length = 100)
    public String getHodId() {
        return hodId;
    }

    public void setHodId(String hodId) {
        this.hodId = hodId;
    }

    @Basic
    @Column(name = "hod_pre_approved_date", nullable = true)
    public Timestamp getHodReviewDate() {
        return hodReviewDate;
    }

    public void setHodReviewDate(Timestamp hodPreApprovedDate) {
        this.hodReviewDate = hodPreApprovedDate;
    }

    @Basic
    @Column(name = "hod_post_approved_date", nullable = true)
    public Timestamp getHodFinalReviewDate() {
        return hodFinalReviewDate;
    }

    public void setHodFinalReviewDate(Timestamp hodPostApprovedDate) {
        this.hodFinalReviewDate = hodPostApprovedDate;
    }

    @Basic
    @Column(name = "hod_application_review_approved", nullable = true)
    public Boolean getHodApplicationReviewApproved() {
        return hodApplicationReviewApproved;
    }

    public void setHodApplicationReviewApproved(Boolean hodApplicationReviewApproved) {
        this.hodApplicationReviewApproved = hodApplicationReviewApproved;
    }

    @Basic
    @Column(name = "hod_final_application_approval", nullable = true)
    public Boolean getHodFinalApplicationApproval() {
        return hodFinalApplicationApproval;
    }

    public void setHodFinalApplicationApproval(Boolean hodFinalApplicationApproval) {
        this.hodFinalApplicationApproval = hodFinalApplicationApproval;
    }

    @Basic
    @Column(name = "rti_id", nullable = true, length = 100)
    public String getRtiId() {
        return rtiId;
    }

    public void setRtiId(String rtiId) {
        this.rtiId = rtiId;
    }

    @Basic
    @Column(name = "rti_pre_approved_date", nullable = true)
    public Timestamp getRtiReviewDate() {
        return rtiReviewDate;
    }

    public void setRtiReviewDate(Timestamp rtiPreApprovedDate) {
        this.rtiReviewDate = rtiPreApprovedDate;
    }

    @Basic
    @Column(name = "rti_post_approved_date", nullable = true)
    public Timestamp getRtiFinalReviewDate() {
        return rtiFinalReviewDate;
    }

    public void setRtiFinalReviewDate(Timestamp rtiPostApprovedDate) {
        this.rtiFinalReviewDate = rtiPostApprovedDate;
    }

    @Basic
    @Column(name = "rti_application_review_approved", nullable = true)
    public Boolean getRtiApplicationReviewApproved() {
        return rtiApplicationReviewApproved;
    }

    public void setRtiApplicationReviewApproved(Boolean rtiApplicationReviewApproved) {
        this.rtiApplicationReviewApproved = rtiApplicationReviewApproved;
    }

    @Basic
    @Column(name = "rti_final_application_approval", nullable = true)
    public Boolean getRtiFinalApplicationApproval() {
        return rtiFinalApplicationApproval;
    }

    public void setRtiFinalApplicationApproval(Boolean rtiFinalApplicationApproval) {
        this.rtiFinalApplicationApproval = rtiFinalApplicationApproval;
    }

    @Basic
    @Column(name = "internal_status", nullable = true)
    public Short getInternalStatus() {
        return internalStatus;
    }

    public void setInternalStatus(Short internalStatus) {
        this.internalStatus = internalStatus;
    }

    @Basic
    @Column(name = "liaison_id", nullable = true, length = 100)
    public String getLiaisonId() {
        return liaisonId;
    }

    public void setLiaisonId(String liaisonId) {
        this.liaisonId = liaisonId;
    }

    @Basic
    @Column(name = "liaison_assigned_date", nullable = true)
    public Timestamp getLiaisonAssignedDate() {
        return liaisonAssignedDate;
    }

    public void setLiaisonAssignedDate(Timestamp liaisonAssignedDate) {
        this.liaisonAssignedDate = liaisonAssignedDate;
    }

    @Basic
    @Column(name = "application_level", nullable = true)
    public Short getApplicationLevel() {
        return applicationLevel;
    }

    public void setApplicationLevel(Short applicationLevel) {
        this.applicationLevel = applicationLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEthicsApplication that = (EntityEthicsApplication) o;
        return Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(dateSubmitted, that.dateSubmitted) &&
                Objects.equals(dateApproved, that.dateApproved) &&
                Objects.equals(piId, that.piId) &&
                Objects.equals(piApprovedDate, that.piApprovedDate) &&
                Objects.equals(prpId, that.prpId) &&
                Objects.equals(prpReviewDate, that.prpReviewDate) &&
                Objects.equals(hodId, that.hodId) &&
                Objects.equals(hodReviewDate, that.hodReviewDate) &&
                Objects.equals(hodFinalReviewDate, that.hodFinalReviewDate) &&
                Objects.equals(rtiId, that.rtiId) &&
                Objects.equals(rtiReviewDate, that.rtiReviewDate) &&
                Objects.equals(rtiFinalReviewDate, that.rtiFinalReviewDate) &&
                Objects.equals(internalStatus, that.internalStatus) &&
                Objects.equals(liaisonId, that.liaisonId) &&
                Objects.equals(liaisonAssignedDate, that.liaisonAssignedDate) &&
                Objects.equals(applicationLevel, that.applicationLevel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationType, applicationYear, applicationNumber, departmentName, facultyName, dateSubmitted, dateApproved, piId, piApprovedDate, prpId, prpReviewDate, hodId, hodReviewDate, hodFinalReviewDate, rtiId, rtiReviewDate, rtiFinalReviewDate, internalStatus, liaisonId, liaisonAssignedDate, applicationLevel);
    }

    public EntityEthicsApplication findApplicationById(EntityEthicsApplicationPK applicationId) {
        return find.byId(applicationId);
    }

    public static List<EntityEthicsApplication> findApplicationsByPerson(EntityPerson person) {
        return findApplicationsByPerson(person.getUserEmail(), person.userType());
    }

    public static List<EntityEthicsApplication> findApplicationsByPerson(String personEmail, UserType userType) {
        return find.all()
                .stream()
                .filter(ethicsApplicationEntity -> {
                    switch (userType) {
                        case PrimaryInvestigator: return ethicsApplicationEntity.getPiId().equals(personEmail);
                        case PrimaryResponsiblePerson: return ethicsApplicationEntity.getPrpId().equals(personEmail);
                        case Liaison: return ethicsApplicationEntity.getLiaisonId().equals(personEmail);
                        case Reviewer: return EntityReviewerfeedback.find.all().stream()
                                    .anyMatch(entityReviewercomponentfeedback -> entityReviewercomponentfeedback.applicationPrimaryKey().equals(ethicsApplicationEntity.applicationPrimaryKey()) && entityReviewercomponentfeedback.getReviewerEmail().equals(personEmail));
                        case FacultyRTI: return ethicsApplicationEntity.getRtiId().equals(personEmail);
                        case DepartmentHead: return ethicsApplicationEntity.getHodId().equals(personEmail);
                        case RCD: return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public String title() {
        return GetTitle(applicationPrimaryKey());
    }

    public static String GetTitle(EntityEthicsApplicationPK applicationId) {
        String componentId = EntityComponent.getAllApplicationCompontents(applicationId)
                .stream()
                .filter(entityComponent -> entityComponent.getQuestion().equals("title"))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cannot find any application with ID: " + String.valueOf(applicationId)))
                .getComponentId();

        return EntityComponentversion
                .getLatestComponent(componentId)
                .getTextValue();

    }

    // Deprecated since internal status is stored in this table


    /**
     * Gets the latest {@link EntityComponentversion} for an {@link EntityEthicsApplication} given the application ID
     * @param applicationId application ID
     * @return
     */
    public static List<EntityComponentversion> getLatestComponents(EntityEthicsApplicationPK applicationId){
        return EntityComponent
                .getAllApplicationCompontents(applicationId)
                .stream()
                .map(entityComponent -> EntityComponentversion.getLatestComponent(entityComponent.getComponentId()))
                .collect(Collectors.toList());
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
