package DAO.MessageSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class EntityMessagePK implements Serializable {
    private Timestamp messageDate;
    private String userEmailSender;
    private String userEmailReceiver;
    private Integer applicationId;

    public EntityMessagePK() {
    }

    @Column(name = "message_date", nullable = false, length = 45)
    public Timestamp getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }

    @Column(name = "user_email_sender", nullable = false, length = 100)
    public String getUserEmailSender() {
        return userEmailSender;
    }

    public void setUserEmailSender(String userEmailSender) {
        this.userEmailSender = userEmailSender;
    }

    @Column(name = "user_email_receiver", nullable = false, length = 100)
    public String getUserEmailReceiver() {
        return userEmailReceiver;
    }

    public void setUserEmailReceiver(String userEmailReceiver) {
        this.userEmailReceiver = userEmailReceiver;
    }

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
        EntityMessagePK that = (EntityMessagePK) o;
        return Objects.equals(messageDate, that.messageDate) &&
                Objects.equals(userEmailSender, that.userEmailSender) &&
                Objects.equals(userEmailReceiver, that.userEmailReceiver) &&
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(messageDate, userEmailSender, userEmailReceiver, applicationId);
    }
}
