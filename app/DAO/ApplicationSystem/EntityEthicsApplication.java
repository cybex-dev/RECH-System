package DAO.ApplicationSystem;

import DAO.ReviewSystem.EntityReviewerfeedback;
import DAO.UserSystem.EntityPerson;
import io.ebean.Finder;
import io.ebean.Model;
import models.UserSystem.UserType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "ethics_application", schema = "rech_system", catalog = "")
public class EntityEthicsApplication  extends Model {
    private Integer applicationId;
    private Integer applicationNumber;
    private String applicationType;
    private Integer applicationYear;
    private String departmentName;
    private String facultyName;
    private Boolean isSubmitted;
    private Timestamp dateSubmitted;
    private Timestamp dateApproved;
    private String piId;
    private Timestamp piApprovedDate;
    private String prpId;
    private Timestamp prpApprovedDate;
    private String hodId;
    private Timestamp hodApprovedDate;
    private String rtiId;
    private Timestamp rtiApprovedDate;
    private String liaisonId;
    private Short internalStatus;
    private Boolean prpApprovedStatus;
    private Boolean hodApprovedStatus;
    private Boolean rtiApprovedStatus;

    public static Finder<Integer, DAO.ApplicationSystem.EntityEthicsApplication> find = new Finder<>(DAO.ApplicationSystem.EntityEthicsApplication.class);

    @Id
    @Column(name = "application_id", nullable = false)
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Basic
    @Column(name = "application_number", nullable = false)
    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Basic
    @Column(name = "application_type", nullable = false, length = 1)
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Basic
    @Column(name = "application_year", nullable = false)
    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Basic
    @Column(name = "department_name", nullable = false, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "faculty_name", nullable = false, length = 50)
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Basic
    @Column(name = "is_submitted", nullable = true)
    public Boolean getSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(Boolean submitted) {
        isSubmitted = submitted;
    }

    @Basic
    @Column(name = "date_submitted", nullable = true, length = 45)
    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Basic
    @Column(name = "date_approved", nullable = true, length = 45)
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
    @Column(name = "pi_approved_date", nullable = true, length = 45)
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
    @Column(name = "prp_approved_date", nullable = true, length = 45)
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
    @Column(name = "hod_approved_date", nullable = true, length = 45)
    public Timestamp getHodApprovedDate() {
        return hodApprovedDate;
    }

    public void setHodApprovedDate(Timestamp hodApproved) {
        this.hodApprovedDate = hodApproved;
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
    @Column(name = "rti_approved_date", nullable = true, length = 45)
    public Timestamp getRtiApprovedDate() {
        return rtiApprovedDate;
    }

    public void setRtiApprovedDate(Timestamp rtiApproved) {
        this.rtiApprovedDate = rtiApproved;
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
    @Column(name = "internal_status", nullable = true)
    public Short getInternalStatus() {
        return internalStatus;
    }

    public void setInternalStatus(Short internalStatus) {
        this.internalStatus = internalStatus;
    }

    @Basic
    @Column(name = "prp_approved_status", nullable = true)
    public Boolean getPrpApprovedStatus() {
        return prpApprovedStatus;
    }

    public void setPrpApprovedStatus(Boolean prpApprovedStatus) {
        this.prpApprovedStatus = prpApprovedStatus;
    }

    @Basic
    @Column(name = "hod_approved_status", nullable = true)
    public Boolean getHodApprovedStatus() {
        return hodApprovedStatus;
    }

    public void setHodApprovedStatus(Boolean hodApprovedStatus) {
        this.hodApprovedStatus = hodApprovedStatus;
    }

    @Basic
    @Column(name = "rti_approved_status", nullable = true)
    public Boolean getRtiApprovedStatus() {
        return rtiApprovedStatus;
    }

    public void setRtiApprovedStatus(Boolean rtiApprovedStatus) {
        this.rtiApprovedStatus = rtiApprovedStatus;
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
                Objects.equals(hodApprovedDate, that.hodApprovedDate) &&
                Objects.equals(rtiId, that.rtiId) &&
                Objects.equals(rtiApprovedDate, that.rtiApprovedDate) &&
                Objects.equals(liaisonId, that.liaisonId) &&
                Objects.equals(internalStatus, that.internalStatus) &&
                Objects.equals(prpApprovedStatus, that.prpApprovedStatus) &&
                Objects.equals(hodApprovedStatus, that.hodApprovedStatus) &&
                Objects.equals(rtiApprovedStatus, that.rtiApprovedStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationId, applicationNumber, applicationType, applicationYear, departmentName, facultyName, isSubmitted, dateSubmitted, dateApproved, piId, piApprovedDate, prpId, prpApprovedDate, hodId, hodApprovedDate, rtiId, rtiApprovedDate, liaisonId, internalStatus, prpApprovedStatus, hodApprovedStatus, rtiApprovedStatus);
    }



    public DAO.ApplicationSystem.EntityEthicsApplication findApplicationById(Integer applicationId) {
        return find.byId(applicationId);
    }

    public static List<DAO.ApplicationSystem.EntityEthicsApplication> findApplicationsByPerson(EntityPerson person) {
        return findApplicationsByPerson(person.getUserEmail(), person.userType());
    }

    public static List<DAO.ApplicationSystem.EntityEthicsApplication> findApplicationsByPerson(String personEmail, UserType userType) {
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

//    public static ApplicationStatus findApplicationStatus(EntityEthicsApplication entityEthicsApplication) {
//        return findApplicationStatus(entityEthicsApplication.applicationId);
//    }
//
//    public static ApplicationStatus findApplicationStatus(Integer applicationId) {
//        EntityAgendaitem latestStatus = EntityAgendaitem.getAllApplicationStatuses(applicationId)
//                .stream()
//                .reduce((entityAgendaitem, entityAgendaitem2) -> entityAgendaitem.getMeetingDate().after(entityAgendaitem2.getMeetingDate())
//                        ? entityAgendaitem : entityAgendaitem2).orElse(null);
//        return (latestStatus != null)
//                ? latestStatus.status()
//                : ApplicationStatus.UNKNOWN;
//    }
//
//
//    public static List<ApplicationStatus> getAllApplicationStatuses(Integer applicationId){
//        return EntityAgendaitem
//                .getAllApplicationStatuses(applicationId)
//                .stream()
//                .map(EntityAgendaitem::status)
//                .collect(Collectors.toList());
//    }
//
//    public static ApplicationStatus getApplicationStatus(Integer applicationId){
//        EntityEthicsApplication entityEthicsApplication = EntityEthicsApplication.find.byId(applicationId);
//        if (entityEthicsApplication == null)
//            throw new EntityNotFoundException("Cannot find an application with ID: " + String.valueOf(applicationId));
//        else {
//            if (entityEthicsApplication.isSubmitted) {
//                return EntityAgendaitem
//                        .getAllApplicationStatuses(applicationId)
//                        .stream()
//                        .max((o1, o2) -> o1.getMeetingDate().after(o2.getMeetingDate()) ? -1 : (o1.getMeetingDate().before(o2.getMeetingDate()) ? 1 : 0))
//                        .orElseThrow(() -> new EntityNotFoundException("Cannot find an application with ID: " + String.valueOf(applicationId)))
//                        .status();
//            } else {
//                return ApplicationStatus.DRAFT;
//            }
//        }
//    }

    /**
     * Gets the latest {@link EntityComponentversion} for an {@link DAO.ApplicationSystem.EntityEthicsApplication} given the application ID
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
