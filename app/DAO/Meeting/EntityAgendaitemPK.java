package DAO.Meeting;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EntityAgendaitemPK implements Serializable {
    private Timestamp meetingDate;
    private Integer applicationId;

    @Column(name = "meeting_date")
    @Id
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Column(name = "application_id")
    @Id
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
        EntityAgendaitemPK that = (EntityAgendaitemPK) o;
        return Objects.equals(meetingDate, that.meetingDate) &&
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meetingDate, applicationId);
    }
}
