package DAO.MessageSystem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "rech_system", catalog = "")
@IdClass(EntityMessagePK.class)
public class EntityMessage {
    private Timestamp messageDate;
    private String message;
    private String userEmailSender;
    private String userEmailReceiver;
    private Integer applicationId;

    @Id
    @Column(name = "message_date", nullable = false, length = 45)
    public Timestamp getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }

    @Basic
    @Column(name = "message", nullable = true, length = -1)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Id
    @Column(name = "user_email_sender", nullable = false, length = 100)
    public String getUserEmailSender() {
        return userEmailSender;
    }

    public void setUserEmailSender(String userEmailSender) {
        this.userEmailSender = userEmailSender;
    }

    @Id
    @Column(name = "user_email_receiver", nullable = false, length = 100)
    public String getUserEmailReceiver() {
        return userEmailReceiver;
    }

    public void setUserEmailReceiver(String userEmailReceiver) {
        this.userEmailReceiver = userEmailReceiver;
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
