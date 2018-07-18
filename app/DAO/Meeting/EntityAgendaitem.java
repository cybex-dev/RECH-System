package DAO.Meeting;

import DAO.ApplicationSystem.EntityEthicsApplication;
import io.ebean.Finder;
import models.ApplicationSystem.ApplicationStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "agendaitem", schema = "rech_system", catalog = "")
@IdClass(EntityAgendaitemPK.class)
public class EntityAgendaitem {
    private String resolution;
    private Short applicationStatus;
    private Timestamp meetingDate;
    private Integer applicationId;

    public static Finder<EntityAgendaitemPK, EntityAgendaitem> find = new Finder<>(EntityAgendaitem.class);

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

    @Id
    @Column(name = "meeting_date", nullable = false)
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Id
    @Column(name = "application_id", nullable = false)
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

    public ApplicationStatus status(){
        return ApplicationStatus.parse(applicationStatus);
    }

    public static List<DAO.Meeting.EntityAgendaitem> getAllApplicationStatuses(EntityEthicsApplication entityEthicsApplication) {
        return getAllApplicationStatuses(entityEthicsApplication.getApplicationId());
    }

    public static List<DAO.Meeting.EntityAgendaitem> getAllApplicationStatuses(int applicationId) {
        return find.all().stream().filter(entityAgendaitem -> entityAgendaitem.applicationId.equals(applicationId)).collect(Collectors.toList());
    }
}
