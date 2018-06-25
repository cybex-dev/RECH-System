package DAO.Meeting;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class AgendaitemEntityPK implements Serializable {
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;
    private Timestamp meetingDate;

    @Column(name = "application_type")
    @Id
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "application_year")
    @Id
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_department")
    @Id
    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    @Column(name = "application_number")
    @Id
    public short getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(short applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "meeting_date")
    @Id
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
        AgendaitemEntityPK that = (AgendaitemEntityPK) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment) &&
                Objects.equals(meetingDate, that.meetingDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationType, applicationYear, applicationDepartment, applicationNumber, meetingDate);
    }
}
