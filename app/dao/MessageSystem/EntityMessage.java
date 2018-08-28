package dao.MessageSystem;

import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "rech_system", catalog = "")
public class EntityMessage extends Model {
    private Timestamp messageDate;
    private String message;
    private String senderEmail;
    private String receiverEmail1;
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;

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

    @Basic
    @Column(name = "sender_email")
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Basic
    @Column(name = "receiver_email")
    public String getReceiverEmail1() {
        return receiverEmail1;
    }

    public void setReceiverEmail1(String receiverEmail1) {
        this.receiverEmail1 = receiverEmail1;
    }

    @Basic
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Basic
    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Basic
    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Basic
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMessage that = (EntityMessage) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(messageDate, that.messageDate) &&
                Objects.equals(message, that.message) &&
                Objects.equals(senderEmail, that.senderEmail) &&
                Objects.equals(receiverEmail1, that.receiverEmail1) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(messageDate, message, senderEmail, receiverEmail1, applicationYear, applicationNumber, applicationType, departmentName, facultyName);
    }
}
