package DAO.Meeting;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MessageEntityPK implements Serializable {
    private String messageDate;
    private String userEmailSender;
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;
    private String userEmailReceiver;

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

    @Column(name = "application_type")
    @Id
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "application_year")
    @Id
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_department")
    @Id
    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    @Column(name = "application_number")
    @Id
    public short getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(short applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "user_email_receiver")
    @Id
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
        MessageEntityPK that = (MessageEntityPK) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(messageDate, that.messageDate) &&
                Objects.equals(userEmailSender, that.userEmailSender) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment) &&
                Objects.equals(userEmailReceiver, that.userEmailReceiver);
    }

    @Override
    public int hashCode() {

        return Objects.hash(messageDate, userEmailSender, applicationType, applicationYear, applicationDepartment, applicationNumber, userEmailReceiver);
    }
}
