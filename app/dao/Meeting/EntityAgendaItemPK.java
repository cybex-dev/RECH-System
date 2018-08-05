package dao.Meeting;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EntityAgendaItemPK implements Serializable {
    private Timestamp meetingDate;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;

    @Column(name = "meeting_date", nullable = false)
    @Id
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Column(name = "application_type", nullable = false, length = 1)
    @Id
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "application_year", nullable = false)
    @Id
    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_number", nullable = false)
    @Id
    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "department_name", nullable = false, length = 50)
    @Id
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "faculty_name", nullable = false, length = 50)
    @Id
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
        EntityAgendaItemPK that = (EntityAgendaItemPK) o;
        return Objects.equals(meetingDate, that.meetingDate) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meetingDate, applicationType, applicationYear, applicationNumber, departmentName, facultyName);
    }
}
