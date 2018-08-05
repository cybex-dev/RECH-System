package dao.ApplicationSystem;

import dao.ApplicationSystem.EntityComponentversion;
import dao.ReviewSystem.EntityReviewerfeedback;
import dao.UserSystem.EntityPerson;
import io.ebean.Finder;
import models.ApplicationSystem.EthicsApplication;
import models.UserSystem.UserType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "Ethics_Application", schema = "rech_system")
@IdClass(EntityEthicsApplicationPK.class)
public class EntityEthicsApplication {
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
    private Timestamp prpApprovedDate;
    private String hodId;
    private Timestamp hodPreApprovedDate;
    private Timestamp hodPostApprovedDate;
    private String rtiId;
    private Timestamp rtiPreApprovedDate;
    private Timestamp rtiPostApprovedDate;
    private Short internalStatus;
    private String liaisonId;
    private Timestamp liaisonAssignedDate;
    private Byte hodApplicationReviewApproved;
    private Byte hodFinalApplicationApproval;
    private Byte rtiApplicationReviewApproved;
    private Byte rtiFinalApplicationApproval;
    private Byte applicationLevel;

    public static Finder<dao.ApplicationSystem.EntityEthicsApplicationPK, dao.ApplicationSystem.EntityEthicsApplication> find = new Finder<>(dao.ApplicationSystem.EntityEthicsApplication.class);

    @Id
    @Column(name = "application_type", nullable = false, length = 255)
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
    public Timestamp getPrpApprovedDate() {
        return prpApprovedDate;
    }

    public void setPrpApprovedDate(Timestamp prpApprovedDate) {
        this.prpApprovedDate = prpApprovedDate;
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
    public Timestamp getHodPreApprovedDate() {
        return hodPreApprovedDate;
    }

    public void setHodPreApprovedDate(Timestamp hodPreApprovedDate) {
        this.hodPreApprovedDate = hodPreApprovedDate;
    }

    @Basic
    @Column(name = "hod_post_approved_date", nullable = true)
    public Timestamp getHodPostApprovedDate() {
        return hodPostApprovedDate;
    }

    public void setHodPostApprovedDate(Timestamp hodPostApprovedDate) {
        this.hodPostApprovedDate = hodPostApprovedDate;
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
    public Timestamp getRtiPreApprovedDate() {
        return rtiPreApprovedDate;
    }

    public void setRtiPreApprovedDate(Timestamp rtiPreApprovedDate) {
        this.rtiPreApprovedDate = rtiPreApprovedDate;
    }

    @Basic
    @Column(name = "rti_post_approved_date", nullable = true)
    public Timestamp getRtiPostApprovedDate() {
        return rtiPostApprovedDate;
    }

    public void setRtiPostApprovedDate(Timestamp rtiPostApprovedDate) {
        this.rtiPostApprovedDate = rtiPostApprovedDate;
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
    @Column(name = "hod_application_review_approved", nullable = true)
    public Byte getHodApplicationReviewApproved() {
        return hodApplicationReviewApproved;
    }

    public void setHodApplicationReviewApproved(Byte hodApplicationReviewApproved) {
        this.hodApplicationReviewApproved = hodApplicationReviewApproved;
    }

    @Basic
    @Column(name = "hod_final_application_approval", nullable = true)
    public Byte getHodFinalApplicationApproval() {
        return hodFinalApplicationApproval;
    }

    public void setHodFinalApplicationApproval(Byte hodFinalApplicationApproval) {
        this.hodFinalApplicationApproval = hodFinalApplicationApproval;
    }

    @Basic
    @Column(name = "rti_application_review_approved", nullable = true)
    public Byte getRtiApplicationReviewApproved() {
        return rtiApplicationReviewApproved;
    }

    public void setRtiApplicationReviewApproved(Byte rtiApplicationReviewApproved) {
        this.rtiApplicationReviewApproved = rtiApplicationReviewApproved;
    }

    @Basic
    @Column(name = "rti_final_application_approval", nullable = true)
    public Byte getRtiFinalApplicationApproval() {
        return rtiFinalApplicationApproval;
    }

    public void setRtiFinalApplicationApproval(Byte rtiFinalApplicationApproval) {
        this.rtiFinalApplicationApproval = rtiFinalApplicationApproval;
    }

    @Basic
    @Column(name = "application_level", nullable = true)
    public Byte getApplicationLevel() {
        return applicationLevel;
    }

    public void setApplicationLevel(Byte applicationLevel) {
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
                Objects.equals(prpApprovedDate, that.prpApprovedDate) &&
                Objects.equals(hodId, that.hodId) &&
                Objects.equals(hodPreApprovedDate, that.hodPreApprovedDate) &&
                Objects.equals(hodPostApprovedDate, that.hodPostApprovedDate) &&
                Objects.equals(rtiId, that.rtiId) &&
                Objects.equals(rtiPreApprovedDate, that.rtiPreApprovedDate) &&
                Objects.equals(rtiPostApprovedDate, that.rtiPostApprovedDate) &&
                Objects.equals(internalStatus, that.internalStatus) &&
                Objects.equals(liaisonId, that.liaisonId) &&
                Objects.equals(liaisonAssignedDate, that.liaisonAssignedDate) &&
                Objects.equals(hodApplicationReviewApproved, that.hodApplicationReviewApproved) &&
                Objects.equals(hodFinalApplicationApproval, that.hodFinalApplicationApproval) &&
                Objects.equals(rtiApplicationReviewApproved, that.rtiApplicationReviewApproved) &&
                Objects.equals(rtiFinalApplicationApproval, that.rtiFinalApplicationApproval) &&
                Objects.equals(applicationLevel, that.applicationLevel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationType, applicationYear, applicationNumber, departmentName, facultyName, dateSubmitted, dateApproved, piId, piApprovedDate, prpId, prpApprovedDate, hodId, hodPreApprovedDate, hodPostApprovedDate, rtiId, rtiPreApprovedDate, rtiPostApprovedDate, internalStatus, liaisonId, liaisonAssignedDate, hodApplicationReviewApproved, hodFinalApplicationApproval, rtiApplicationReviewApproved, rtiFinalApplicationApproval, applicationLevel);
    }

    public dao.ApplicationSystem.EntityEthicsApplication findApplicationById(dao.ApplicationSystem.EntityEthicsApplicationPK applicationId) {
        return find.byId(applicationId);
    }

    public static List<dao.ApplicationSystem.EntityEthicsApplication> findApplicationsByPerson(EntityPerson person) {
        return findApplicationsByPerson(person.getUserEmail(), person.userType());
    }

    public static List<dao.ApplicationSystem.EntityEthicsApplication> findApplicationsByPerson(String personEmail, UserType userType) {
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

    public static String GetTitle(dao.ApplicationSystem.EntityEthicsApplicationPK applicationId) {
        String componentId = dao.ApplicationSystem.EntityComponent.getAllApplicationCompontents(applicationId)
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
     * Gets the latest {@link EntityComponentversion} for an {@link dao.ApplicationSystem.EntityEthicsApplication} given the application ID
     * @param applicationId application ID
     * @return
     */
    public static List<EntityComponentversion> getLatestComponents(dao.ApplicationSystem.EntityEthicsApplicationPK applicationId){
        return EntityComponent
                .getAllApplicationCompontents(applicationId)
                .stream()
                .map(entityComponent -> EntityComponentversion.getLatestComponent(entityComponent.getComponentId()))
                .collect(Collectors.toList());
    }

    public dao.ApplicationSystem.EntityEthicsApplicationPK applicationPrimaryKey() {
        dao.ApplicationSystem.EntityEthicsApplicationPK pk = new dao.ApplicationSystem.EntityEthicsApplicationPK();
        pk.setApplicationNumber(applicationNumber);
        pk.setApplicationType(applicationType);
        pk.setApplicationYear(applicationYear);
        pk.setDepartmentName(departmentName);
        pk.setFacultyName(facultyName);
        return pk;
    }

    public EthicsApplication.ApplicationType type(){
        return EthicsApplication.ApplicationType.parse(applicationType);
    }
}
