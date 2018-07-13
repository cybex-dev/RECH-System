package DAO.Meeting;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "agendaitem", schema = "rech_system", catalog = "")
@IdClass(AgendaitemEntityPK.class)
public class AgendaitemEntity {
    private String resolution;
    private Byte applicationStatus;
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;
    private Timestamp meetingDate;

    @Basic
    @Column(name = "resolution")
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Basic
    @Column(name = "application_status")
    public Byte getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Byte applicationStatus) {
        this.applicationStatus = applicationStatus;
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
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_department")
    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    @Id
    @Column(name = "application_number")
    public short getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(short applicationNumber) {
        this.applicationNumber = applicationNumber;
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
        AgendaitemEntity that = (AgendaitemEntity) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(resolution, that.resolution) &&
                Objects.equals(applicationStatus, that.applicationStatus) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment) &&
                Objects.equals(meetingDate, that.meetingDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(resolution, applicationStatus, applicationType, applicationYear, applicationDepartment, applicationNumber, meetingDate);
    }
}
