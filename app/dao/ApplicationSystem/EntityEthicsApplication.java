package dao.ApplicationSystem;

import dao.ReviewSystem.EntityReviewerfeedback;
import dao.UserSystem.EntityPerson;
import io.ebean.Finder;
import models.UserSystem.UserType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "ethics_application", schema = "rech_system", catalog = "")
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

    public static Finder<dao.ApplicationSystem.EntityEthicsApplicationPK, dao.ApplicationSystem.EntityEthicsApplication> find = new Finder<>(dao.ApplicationSystem.EntityEthicsApplication.class);

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
                Objects.equals(internalStatus, that.internalStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationType, applicationYear, applicationNumber, departmentName, facultyName, dateSubmitted, dateApproved, piId, piApprovedDate, prpId, prpApprovedDate, hodId, hodPreApprovedDate, hodPostApprovedDate, rtiId, rtiPreApprovedDate, rtiPostApprovedDate, internalStatus);
    }

    public dao.ApplicationSystem.EntityEthicsApplication findApplicationById(Integer applicationId) {
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
                        case Reviewer: return EntityReviewerfeedback.find.all().stream().anyMatch(entityReviewerfeedback -> entityReviewerfeedback.getReviewerEmail().equals(personEmail) && entityReviewerfeedback.getApplicationId().equals(ethicsApplicationEntity.applicationId));
                        case FacultyRTI: return ethicsApplicationEntity.getRtiId().equals(personEmail);
                        case DepartmentHead: return ethicsApplicationEntity.getHodId().equals(personEmail);
                        case RCD: return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public static String getTitle(int applicationId) {
        Integer componentId = EntityComponent.getAllApplicationCompontents(applicationId)
                .stream()
                .filter(entityComponent -> entityComponent.getQuestionId().equals("title"))
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
    public static List<EntityComponentversion> getLatestComponents(Integer applicationId){
        return EntityComponent
                .getAllApplicationCompontents(applicationId)
                .stream()
                .map(entityComponent -> EntityComponentversion.getLatestComponent(entityComponent.getComponentId()))
                .collect(Collectors.toList());
    }
}
