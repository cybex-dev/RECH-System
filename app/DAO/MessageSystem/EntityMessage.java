package DAO.MessageSystem;

import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "rech_system", catalog = "")
@IdClass(EntityMessagePK.class)
public class EntityMessage extends Model {
    private Timestamp messageDate;
    private String message;
    private String userEmailSender;
    private String userEmailReceiver;
    private Integer applicationId;

    @Id
    @Column(name = "message_date")
    public Timestamp getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Id
    @Column(name = "user_email_sender")
    public String getUserEmailSender() {
        return userEmailSender;
    }

    public void setUserEmailSender(String userEmailSender) {
        this.userEmailSender = userEmailSender;
    }

    @Id
    @Column(name = "user_email_receiver")
    public String getUserEmailReceiver() {
        return userEmailReceiver;
    }

    public void setUserEmailReceiver(String userEmailReceiver) {
        this.userEmailReceiver = userEmailReceiver;
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
        EntityMessage that = (EntityMessage) o;
        return Objects.equals(messageDate, that.messageDate) &&
                Objects.equals(message, that.message) &&
                Objects.equals(userEmailSender, that.userEmailSender) &&
                Objects.equals(userEmailReceiver, that.userEmailReceiver) &&
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(messageDate, message, userEmailSender, userEmailReceiver, applicationId);
    }
}
