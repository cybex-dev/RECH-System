package dao.Meeting;

import dao.ApplicationSystem.EntityEthicsApplicationPK;
import io.ebean.Finder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "AgendaItem", schema = "rech_system")
@IdClass(EntityAgendaItemPK.class)
public class EntityAgendaItem {
    private Timestamp meetingDate;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private String resolution;
    private Short applicationStatus;

    public static Finder<EntityAgendaItemPK, EntityAgendaItem> find = new Finder<>(EntityAgendaItem.class);

    @Id
    @Column(name = "meeting_date", nullable = false)
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

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
    @Column(name = "resolution", nullable = true, length = -1)
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Basic
    @Column(name = "application_status", nullable = true)
    public Short getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Short applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityAgendaItem that = (EntityAgendaItem) o;
        return Objects.equals(meetingDate, that.meetingDate) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(resolution, that.resolution) &&
                Objects.equals(applicationStatus, that.applicationStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meetingDate, applicationType, applicationYear, applicationNumber, departmentName, facultyName, resolution, applicationStatus);
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
