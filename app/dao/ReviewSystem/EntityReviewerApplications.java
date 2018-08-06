package dao.ReviewSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "ReviewerApplications", schema = "rech_system")
@IdClass(EntityReviewerApplicationsPK.class)
public class EntityReviewerApplications extends Model {
    private Timestamp dateAssigned;
    private String reviewerEmail;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;

    public static Finder<EntityReviewerApplicationsPK, EntityReviewerApplications> find = new Finder<>(EntityReviewerApplications.class);

    @Basic
    @Column(name = "date_assigned", nullable = true, length = 45)
    public Timestamp getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(Timestamp dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    @Id
    @Column(name = "reviewer_email", nullable = false, length = 100)
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerApplications that = (EntityReviewerApplications) o;
        return Objects.equals(dateAssigned, that.dateAssigned) &&
                Objects.equals(reviewerEmail, that.reviewerEmail) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateAssigned, reviewerEmail, applicationType, applicationYear, applicationNumber, departmentName, facultyName);
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

    public void setApplicationKey(EntityEthicsApplicationPK applicationId) {
        this.applicationNumber = applicationId.getApplicationNumber();
        this.applicationType = applicationId.getApplicationType();
        this.applicationYear = applicationId.getApplicationYear();
        this.facultyName = applicationId.getFacultyName();
        this.departmentName = applicationId.getDepartmentName();
    }

    public static List<String> getApplicationReviewers(EntityEthicsApplicationPK applicationId) {
        return EntityReviewerApplications.find.all().stream()
                .filter(application -> application.applicationPrimaryKey().equals(applicationId))
                .map(EntityReviewerApplications::getReviewerEmail)
                .collect(Collectors.toList());
    }
}
