package dao.ApplicationSystem;

import dao.UserSystem.EntityPerson;
import io.ebean.Finder;
import io.ebean.Model;
import models.ApplicationSystem.EthicsApplication;
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
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private Timestamp dateSubmitted;
    private Integer applicationRevision;
    private Timestamp dateApproved;
    private Timestamp piApprovedDate;
    private Timestamp prpApprovedDate;
    private Timestamp hodPreApprovedDate;
    private Timestamp hodPostApprovedDate;
    private Timestamp rtiPreApprovedDate;
    private Timestamp rtiPostApprovedDate;
    private Short internalStatus;
    private Timestamp liaisonAssignedDate;
    private Boolean hodApplicationReviewApproved;
    private Boolean hodFinalApplicationApproval;
    private Boolean rtiApplicationReviewApproved;
    private Boolean rtiFinalApplicationApproval;
    private Short applicationLevel;
    private String departmentName;
    private String facultyName;
    private String piId;
    private String prpId;
    private String rtiId;
    private String hodId;
    private String liaisonId;

    public static Finder<dao.ApplicationSystem.EntityEthicsApplicationPK, dao.ApplicationSystem.EntityEthicsApplication> find = new Finder<>(dao.ApplicationSystem.EntityEthicsApplication.class);

    @Id
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Id
    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Basic
    @Column(name = "date_submitted")
    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Basic
    @Column(name = "application_revision")
    public Integer getApplicationRevision() {
        return applicationRevision;
    }

    public void setApplicationRevision(Integer applicationRevision) {
        this.applicationRevision = applicationRevision;
    }

    @Basic
    @Column(name = "date_approved")
    public Timestamp getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Timestamp dateApproved) {
        this.dateApproved = dateApproved;
    }

    @Basic
    @Column(name = "pi_approved_date")
    public Timestamp getPiApprovedDate() {
        return piApprovedDate;
    }

    public void setPiApprovedDate(Timestamp piApprovedDate) {
        this.piApprovedDate = piApprovedDate;
    }

    @Basic
    @Column(name = "prp_approved_date")
    public Timestamp getPrpApprovedDate() {
        return prpApprovedDate;
    }

    public void setPrpApprovedDate(Timestamp prpApprovedDate) {
        this.prpApprovedDate = prpApprovedDate;
    }

    @Basic
    @Column(name = "hod_pre_approved_date")
    public Timestamp getHodPreApprovedDate() {
        return hodPreApprovedDate;
    }

    public void setHodPreApprovedDate(Timestamp hodPreApprovedDate) {
        this.hodPreApprovedDate = hodPreApprovedDate;
    }

    @Basic
    @Column(name = "hod_post_approved_date")
    public Timestamp getHodPostApprovedDate() {
        return hodPostApprovedDate;
    }

    public void setHodPostApprovedDate(Timestamp hodPostApprovedDate) {
        this.hodPostApprovedDate = hodPostApprovedDate;
    }

    @Basic
    @Column(name = "rti_pre_approved_date")
    public Timestamp getRtiPreApprovedDate() {
        return rtiPreApprovedDate;
    }

    public void setRtiPreApprovedDate(Timestamp rtiPreApprovedDate) {
        this.rtiPreApprovedDate = rtiPreApprovedDate;
    }

    @Basic
    @Column(name = "rti_post_approved_date")
    public Timestamp getRtiPostApprovedDate() {
        return rtiPostApprovedDate;
    }

    public void setRtiPostApprovedDate(Timestamp rtiPostApprovedDate) {
        this.rtiPostApprovedDate = rtiPostApprovedDate;
    }

    @Basic
    @Column(name = "internal_status")
    public Short getInternalStatus() {
        return internalStatus;
    }

    public void setInternalStatus(Short internalStatus) {
        this.internalStatus = internalStatus;
    }

    @Basic
    @Column(name = "liaison_assigned_date")
    public Timestamp getLiaisonAssignedDate() {
        return liaisonAssignedDate;
    }

    public void setLiaisonAssignedDate(Timestamp liaisonAssignedDate) {
        this.liaisonAssignedDate = liaisonAssignedDate;
    }

    @Basic
    @Column(name = "hod_application_review_approved")
    public Boolean getHodApplicationReviewApproved() {
        return hodApplicationReviewApproved;
    }

    public void setHodApplicationReviewApproved(Boolean hodApplicationReviewApproved) {
        this.hodApplicationReviewApproved = hodApplicationReviewApproved;
    }

    @Basic
    @Column(name = "hod_final_application_approval")
    public Boolean getHodFinalApplicationApproval() {
        return hodFinalApplicationApproval;
    }

    public void setHodFinalApplicationApproval(Boolean hodFinalApplicationApproval) {
        this.hodFinalApplicationApproval = hodFinalApplicationApproval;
    }

    @Basic
    @Column(name = "rti_application_review_approved")
    public Boolean getRtiApplicationReviewApproved() {
        return rtiApplicationReviewApproved;
    }

    public void setRtiApplicationReviewApproved(Boolean rtiApplicationReviewApproved) {
        this.rtiApplicationReviewApproved = rtiApplicationReviewApproved;
    }

    @Basic
    @Column(name = "rti_final_application_approval")
    public Boolean getRtiFinalApplicationApproval() {
        return rtiFinalApplicationApproval;
    }

    public void setRtiFinalApplicationApproval(Boolean rtiFinalApplicationApproval) {
        this.rtiFinalApplicationApproval = rtiFinalApplicationApproval;
    }

    @Basic
    @Column(name = "application_level")
    public Short getApplicationLevel() {
        return applicationLevel;
    }

    public void setApplicationLevel(Short applicationLevel) {
        this.applicationLevel = applicationLevel;
    }

    @Id
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Id
    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
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
    @Column(name = "prp_id")
    public String getPrpId() {
        return prpId;
    }

    public void setPrpId(String prpId) {
        this.prpId = prpId;
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
    @Column(name = "hod_id")
    public String getHodId() {
        return hodId;
    }

    public void setHodId(String hodId) {
        this.hodId = hodId;
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
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(dateSubmitted, that.dateSubmitted) &&
                Objects.equals(applicationRevision, that.applicationRevision) &&
                Objects.equals(dateApproved, that.dateApproved) &&
                Objects.equals(piApprovedDate, that.piApprovedDate) &&
                Objects.equals(prpApprovedDate, that.prpApprovedDate) &&
                Objects.equals(hodPreApprovedDate, that.hodPreApprovedDate) &&
                Objects.equals(hodPostApprovedDate, that.hodPostApprovedDate) &&
                Objects.equals(rtiPreApprovedDate, that.rtiPreApprovedDate) &&
                Objects.equals(rtiPostApprovedDate, that.rtiPostApprovedDate) &&
                Objects.equals(internalStatus, that.internalStatus) &&
                Objects.equals(liaisonAssignedDate, that.liaisonAssignedDate) &&
                Objects.equals(hodApplicationReviewApproved, that.hodApplicationReviewApproved) &&
                Objects.equals(hodFinalApplicationApproval, that.hodFinalApplicationApproval) &&
                Objects.equals(rtiApplicationReviewApproved, that.rtiApplicationReviewApproved) &&
                Objects.equals(rtiFinalApplicationApproval, that.rtiFinalApplicationApproval) &&
                Objects.equals(applicationLevel, that.applicationLevel) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(piId, that.piId) &&
                Objects.equals(prpId, that.prpId) &&
                Objects.equals(rtiId, that.rtiId) &&
                Objects.equals(hodId, that.hodId) &&
                Objects.equals(liaisonId, that.liaisonId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationYear, applicationNumber, applicationType, dateSubmitted, applicationRevision, dateApproved, piApprovedDate, prpApprovedDate, hodPreApprovedDate, hodPostApprovedDate, rtiPreApprovedDate, rtiPostApprovedDate, internalStatus, liaisonAssignedDate, hodApplicationReviewApproved, hodFinalApplicationApproval, rtiApplicationReviewApproved, rtiFinalApplicationApproval, applicationLevel, departmentName, facultyName, piId, prpId, rtiId, hodId, liaisonId);
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
                        //TODO fix
//                        case Reviewer: return EntityReviewerfeedback.find.all().stream()
//                                .anyMatch(entityReviewercomponentfeedback -> entityReviewercomponentfeedback.applicationPrimaryKey().equals(ethicsApplicationEntity.applicationPrimaryKey()) && entityReviewercomponentfeedback.getReviewerEmail().equals(personEmail));
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

        return EntityComponentVersion
                .getLatestComponent(componentId)
                .getTextValue();

    }

    // Deprecated since internal status is stored in this table


    /**
     * Gets the latest {@link EntityComponentVersion} for an {@link dao.ApplicationSystem.EntityEthicsApplication} given the application ID
     * @param applicationId application ID
     * @return
     */
    public static List<EntityComponentVersion> getLatestComponents(dao.ApplicationSystem.EntityEthicsApplicationPK applicationId){
        return EntityComponent
                .getAllApplicationCompontents(applicationId)
                .stream()
                .map(entityComponent -> EntityComponentVersion.getLatestComponent(entityComponent.getComponentId()))
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
