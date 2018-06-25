package DAO.ApplicationSystem;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "component", schema = "rech_system", catalog = "")
@IdClass(ComponentEntityPK.class)
public class ComponentEntity {
    private short sectionId;
    private short componentId;
    private String question;
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;

    @Id
    @Column(name = "section_id")
    public short getSectionId() {
        return sectionId;
    }

    public void setSectionId(short sectionId) {
        this.sectionId = sectionId;
    }

    @Id
    @Column(name = "component_id")
    public short getComponentId() {
        return componentId;
    }

    public void setComponentId(short componentId) {
        this.componentId = componentId;
    }

    @Basic
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentEntity that = (ComponentEntity) o;
        return sectionId == that.sectionId &&
                componentId == that.componentId &&
                applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(question, that.question) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sectionId, componentId, question, applicationType, applicationYear, applicationDepartment, applicationNumber);
    }
}
