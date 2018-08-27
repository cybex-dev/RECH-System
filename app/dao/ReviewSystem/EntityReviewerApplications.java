package dao.ReviewSystem;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "reviewerapplications", schema = "rech_system")
@IdClass(EntityReviewerApplicationsPK.class)
public class EntityReviewerApplications {
    private String dateAssigned;
    private String reviewerEmail;
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;


    public static Finder<EntityReviewerApplicationsPK, EntityReviewerApplications> find = new Finder<>(EntityReviewerApplications.class);

    @Basic
    @Column(name = "date_assigned")
    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    @Id
    @Column(name = "reviewer_email")
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityReviewerApplications that = (EntityReviewerApplications) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(dateAssigned, that.dateAssigned) &&
                Objects.equals(reviewerEmail, that.reviewerEmail) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateAssigned, reviewerEmail, applicationYear, applicationNumber, applicationType, departmentName, facultyName);
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

    public void setApplicationKey(dao.ApplicationSystem.EntityEthicsApplicationPK applicationId) {
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
