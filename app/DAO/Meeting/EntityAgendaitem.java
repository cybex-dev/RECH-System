package DAO.Meeting;

import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "agendaitem", schema = "rech_system", catalog = "")
@IdClass(EntityAgendaitemPK.class)
public class EntityAgendaitem extends Model {
    private String resolution;
    private Short applicationStatus;
    private Timestamp meetingDate;
    private Integer applicationId;

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
    public Short getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Short applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Id
    @Column(name = "meeting_date")
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Id
    @Column(name = "application_id")
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityAgendaitem that = (EntityAgendaitem) o;
        return Objects.equals(resolution, that.resolution) &&
                Objects.equals(applicationStatus, that.applicationStatus) &&
                Objects.equals(meetingDate, that.meetingDate) &&
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(resolution, applicationStatus, meetingDate, applicationId);
    }
}
