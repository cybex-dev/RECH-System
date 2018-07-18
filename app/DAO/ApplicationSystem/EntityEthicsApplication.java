package DAO.ApplicationSystem;

import DAO.Meeting.EntityAgendaitem;
import DAO.ReviewSystem.EntityReviewerfeedback;
import DAO.UserSystem.EntityPerson;
import io.ebean.Finder;
import io.ebean.Model;
import models.ApplicationSystem.ApplicationStatus;
import models.UserSystem.UserType;
import scala.App;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private Timestamp dateSubmitted;
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
    private Short internal_status;

    public static Finder<Integer, EntityEthicsApplication> find = new Finder<>(EntityEthicsApplication.class);


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
    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
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


    public EntityEthicsApplication findApplicationById(Integer applicationId) {
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
     * Gets the latest {@link EntityComponentversion} for an {@link EntityEthicsApplication} given the application ID
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

