package dao.Meeting;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityAgendaItemPK implements Serializable {
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;
    private Timestamp meetingDate;

    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

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
        EntityAgendaItemPK that = (EntityAgendaItemPK) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(meetingDate, that.meetingDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationYear, applicationNumber, applicationType, departmentName, facultyName, meetingDate);
    }
}
