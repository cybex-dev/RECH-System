package DAO.Meeting;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "rech_system", catalog = "")
@IdClass(MessageEntityPK.class)
public class MessageEntity {
    private String messageDate;
    private String message;
    private String userEmailSender;
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;
    private String userEmailReceiver;

    @Id
    @Column(name = "message_date")
    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
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
    @Column(name = "user_email_receiver")
    public String getUserEmailReceiver() {
        return userEmailReceiver;
    }

    public void setUserEmailReceiver(String userEmailReceiver) {
        this.userEmailReceiver = userEmailReceiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(messageDate, that.messageDate) &&
                Objects.equals(message, that.message) &&
                Objects.equals(userEmailSender, that.userEmailSender) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment) &&
                Objects.equals(userEmailReceiver, that.userEmailReceiver);
    }

    @Override
    public int hashCode() {

        return Objects.hash(messageDate, message, userEmailSender, applicationType, applicationYear, applicationDepartment, applicationNumber, userEmailReceiver);
    }
}
