package dao.Meeting;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "meeting", schema = "rech_system")
public class EntityMeeting extends Model {
    private Timestamp meetingDate;
    private String announcements;
    private Boolean isComplete;

    public static Finder<Timestamp, EntityMeeting> find = new Finder<>(EntityMeeting.class);

    public static List<EntityAgendaItem> getAllApplications(String meetingId) {
        Timestamp timestamp = Timestamp.valueOf(meetingId + " 23:59:59.0");

        return EntityAgendaItem.find.all()
                .stream()
                .filter(entityAgendaItem -> entityAgendaItem.getMeetingDate().equals(timestamp))
                .collect(Collectors.toList());
    }

    public static List<EntityMeeting> getAllMeetings() {
        return find.all();
    }

    public static EntityMeeting getMeeting(Timestamp meetingId) {
        return find.byId(meetingId);
    }

    public static EntityMeeting getNextMeeting() {
        Timestamp now = Timestamp.from(Instant.now());
        return EntityMeeting.find.all()
                .stream()
                .filter(entityMeeting -> entityMeeting.getMeetingDate().after(now))
                .min((o1, o2) -> {
                    Timestamp t1 = o1.getMeetingDate();
                    Timestamp t2 = o2.getMeetingDate();
                    if (t1.before(t2)) {
                        return -1;
                    } else if (t1.after(t2)) {
                        return 1;
                    } else return 0;
                }).orElse(null);
    }

    public List<EntityAgendaItem> getMeetingCompleteItems(){
        List<EntityAgendaItem> allMeetingItems = EntityAgendaItem.getAllMeetingItems(meetingDate);
        return allMeetingItems.stream().filter(EntityAgendaItem::getIsReviewed).collect(Collectors.toList());
    }

    public List<EntityAgendaItem> getMeetingIncompleteItems(){
        List<EntityAgendaItem> allMeetingItems = EntityAgendaItem.getAllMeetingItems(meetingDate);
        return allMeetingItems.stream().filter(entityAgendaItem -> !entityAgendaItem.getIsReviewed()).collect(Collectors.toList());
    }

    @Id
    @Column(name = "meeting_date")
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Basic
    @Column(name = "announcements")
    public String getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(String announcements) {
        this.announcements = announcements;
    }

    @Basic
    @Column(name = "is_complete")
    public Boolean getIsComplete() {
        if (isComplete == null){
            isComplete = false;
            save();
        }
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMeeting that = (EntityMeeting) o;
        return Objects.equals(meetingDate, that.meetingDate) &&
                Objects.equals(announcements, that.announcements) &&
                Objects.equals(isComplete, that.isComplete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meetingDate, announcements, isComplete);
    }

    public int getNumberOfApplications(){
        return EntityAgendaItem.getAllMeetingItems(meetingDate).size();
    }
}
