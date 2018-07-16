package DAO.MessageSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EntityMessagePK implements Serializable {
    private String messageDate;
    private String userEmailSender;
    private String userEmailReceiver;
    private Integer applicationId;

    @Column(name = "message_date")
    @Id
    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    @Column(name = "user_email_sender")
    @Id
    public String getUserEmailSender() {
        return userEmailSender;
    }

    public void setUserEmailSender(String userEmailSender) {
        this.userEmailSender = userEmailSender;
    }

    @Column(name = "user_email_receiver")
    @Id
    public String getUserEmailReceiver() {
        return userEmailReceiver;
    }

    public void setUserEmailReceiver(String userEmailReceiver) {
        this.userEmailReceiver = userEmailReceiver;
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
