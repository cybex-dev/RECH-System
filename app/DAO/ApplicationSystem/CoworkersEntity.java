package DAO.ApplicationSystem;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coworkers", schema = "rech_system", catalog = "")
@IdClass(CoworkersEntityPK.class)
public class CoworkersEntity {
    private String personUserEmail;
    private String personDepartmentFacultyName;
    private String personDepartmentDepartmentName;
    private String personFacultyFacultyName;
    private String ethicsApplicationApplicationType;
    private int ethicsApplicationApplicationYear;
    private String ethicsApplicationApplicationDepartment;
    private short ethicsApplicationApplicationNumber;
    private String affiliation;

    @Id
    @Column(name = "Person_user_email")
    public String getPersonUserEmail() {
        return personUserEmail;
    }

    public void setPersonUserEmail(String personUserEmail) {
        this.personUserEmail = personUserEmail;
    }

    @Id
    @Column(name = "Person_Department_faculty_name")
    public String getPersonDepartmentFacultyName() {
        return personDepartmentFacultyName;
    }

    public void setPersonDepartmentFacultyName(String personDepartmentFacultyName) {
        this.personDepartmentFacultyName = personDepartmentFacultyName;
    }

    @Id
    @Column(name = "Person_Department_department_name")
    public String getPersonDepartmentDepartmentName() {
        return personDepartmentDepartmentName;
    }

    public void setPersonDepartmentDepartmentName(String personDepartmentDepartmentName) {
        this.personDepartmentDepartmentName = personDepartmentDepartmentName;
    }

    @Id
    @Column(name = "Person_Faculty_faculty_name")
    public String getPersonFacultyFacultyName() {
        return personFacultyFacultyName;
    }

    public void setPersonFacultyFacultyName(String personFacultyFacultyName) {
        this.personFacultyFacultyName = personFacultyFacultyName;
    }

    @Id
    @Column(name = "Ethics_Application_application_type")
    public String getEthicsApplicationApplicationType() {
        return ethicsApplicationApplicationType;
    }

    public void setEthicsApplicationApplicationType(String ethicsApplicationApplicationType) {
        this.ethicsApplicationApplicationType = ethicsApplicationApplicationType;
    }

    @Id
    @Column(name = "Ethics_Application_application_year")
    public int getEthicsApplicationApplicationYear() {
        return ethicsApplicationApplicationYear;
    }

    public void setEthicsApplicationApplicationYear(int ethicsApplicationApplicationYear) {
        this.ethicsApplicationApplicationYear = ethicsApplicationApplicationYear;
    }

    @Id
    @Column(name = "Ethics_Application_application_department")
    public String getEthicsApplicationApplicationDepartment() {
        return ethicsApplicationApplicationDepartment;
    }

    public void setEthicsApplicationApplicationDepartment(String ethicsApplicationApplicationDepartment) {
        this.ethicsApplicationApplicationDepartment = ethicsApplicationApplicationDepartment;
    }

    @Id
    @Column(name = "Ethics_Application_application_number")
    public short getEthicsApplicationApplicationNumber() {
        return ethicsApplicationApplicationNumber;
    }

    public void setEthicsApplicationApplicationNumber(short ethicsApplicationApplicationNumber) {
        this.ethicsApplicationApplicationNumber = ethicsApplicationApplicationNumber;
    }

    @Basic
    @Column(name = "affiliation")
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoworkersEntity that = (CoworkersEntity) o;
        return ethicsApplicationApplicationYear == that.ethicsApplicationApplicationYear &&
                ethicsApplicationApplicationNumber == that.ethicsApplicationApplicationNumber &&
                Objects.equals(personUserEmail, that.personUserEmail) &&
                Objects.equals(personDepartmentFacultyName, that.personDepartmentFacultyName) &&
                Objects.equals(personDepartmentDepartmentName, that.personDepartmentDepartmentName) &&
                Objects.equals(personFacultyFacultyName, that.personFacultyFacultyName) &&
                Objects.equals(ethicsApplicationApplicationType, that.ethicsApplicationApplicationType) &&
                Objects.equals(ethicsApplicationApplicationDepartment, that.ethicsApplicationApplicationDepartment) &&
                Objects.equals(affiliation, that.affiliation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personUserEmail, personDepartmentFacultyName, personDepartmentDepartmentName, personFacultyFacultyName, ethicsApplicationApplicationType, ethicsApplicationApplicationYear, ethicsApplicationApplicationDepartment, ethicsApplicationApplicationNumber, affiliation);
    }
}
