package DAO.Meeting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "meeting", schema = "rech_system", catalog = "")
public class MeetingEntity {
    private Timestamp meetingDate;

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
        MeetingEntity that = (MeetingEntity) o;
        return Objects.equals(meetingDate, that.meetingDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meetingDate);
    }
}
