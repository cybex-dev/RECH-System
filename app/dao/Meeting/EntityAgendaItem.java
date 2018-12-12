package dao.Meeting;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "agendaitem", schema = "rech_system")
@IdClass(EntityAgendaItemPK.class)
public class EntityAgendaItem extends Model {
    private String resolution;
    private Short applicationStatus;
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;
    private Timestamp meetingDate;
    private Boolean isReviewed;

    public static Finder<EntityAgendaItemPK, EntityAgendaItem> find = new Finder<>(EntityAgendaItem.class);

    public static List<EntityAgendaItem> getAllMeetingItems(Timestamp meetingDate) {
        return find.all().stream().filter(entityAgendaItem -> entityAgendaItem.meetingDate.equals(meetingDate)).collect(Collectors.toList());
    }

    @Basic
    @Column(name = "resolution")
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Basic
    @Column(name = "is_reviewed")
    public Boolean getIsReviewed() {
        if (isReviewed == null){
            isReviewed = false;
            save();
        }
        return isReviewed;
    }

    public void setIsReviewed(Boolean isReviewed) {
        this.isReviewed = isReviewed;
    }

    @Basic
    @Column(name = "application_status")
    public Short getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Short applicationStatus) {
        this.applicationStatus = applicationStatus;
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

    @Id
    @Column(name = "meeting_date")
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityAgendaItem that = (EntityAgendaItem) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(resolution, that.resolution) &&
                Objects.equals(applicationStatus, that.applicationStatus) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(meetingDate, that.meetingDate) &&
                Objects.equals(isReviewed, that.isReviewed);
    }

    @Override
    public int hashCode() {

        return Objects.hash(resolution, applicationStatus, applicationYear, applicationNumber, applicationType, departmentName, facultyName, meetingDate, isReviewed);
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

    public void setApplicationId(EntityEthicsApplicationPK applicationId) {
        this.applicationYear = applicationId.getApplicationYear();
        this.applicationType = applicationId.getApplicationType();
        this.departmentName = applicationId.getDepartmentName();
        this.facultyName = applicationId.getFacultyName();
        this.applicationNumber = applicationId.getApplicationNumber();
    }
}
