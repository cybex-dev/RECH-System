package dao.Meeting;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityAgendaitemPK implements Serializable {
    private Timestamp meetingDate;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;

    public EntityAgendaitemPK() {
    }

    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
        EntityAgendaitemPK that = (EntityAgendaitemPK) o;
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
