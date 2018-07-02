package DAO.ApplicationSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CoworkersEntityPK implements Serializable {
    private String personUserEmail;
    private String personDepartmentFacultyName;
    private String personDepartmentDepartmentName;
    private String personFacultyFacultyName;
    private String ethicsApplicationApplicationType;
    private int ethicsApplicationApplicationYear;
    private String ethicsApplicationApplicationDepartment;
    private short ethicsApplicationApplicationNumber;

    @Column(name = "Person_user_email")
    @Id
    public String getPersonUserEmail() {
        return personUserEmail;
    }

    public void setPersonUserEmail(String personUserEmail) {
        this.personUserEmail = personUserEmail;
    }

    @Column(name = "Person_Department_faculty_name")
    @Id
    public String getPersonDepartmentFacultyName() {
        return personDepartmentFacultyName;
    }

    public void setPersonDepartmentFacultyName(String personDepartmentFacultyName) {
        this.personDepartmentFacultyName = personDepartmentFacultyName;
    }

    @Column(name = "Person_Department_department_name")
    @Id
    public String getPersonDepartmentDepartmentName() {
        return personDepartmentDepartmentName;
    }

    public void setPersonDepartmentDepartmentName(String personDepartmentDepartmentName) {
        this.personDepartmentDepartmentName = personDepartmentDepartmentName;
    }

    @Column(name = "Person_Faculty_faculty_name")
    @Id
    public String getPersonFacultyFacultyName() {
        return personFacultyFacultyName;
    }

    public void setPersonFacultyFacultyName(String personFacultyFacultyName) {
        this.personFacultyFacultyName = personFacultyFacultyName;
    }

    @Column(name = "Ethics_Application_application_type")
    @Id
    public String getEthicsApplicationApplicationType() {
        return ethicsApplicationApplicationType;
    }

    public void setEthicsApplicationApplicationType(String ethicsApplicationApplicationType) {
        this.ethicsApplicationApplicationType = ethicsApplicationApplicationType;
    }

    @Column(name = "Ethics_Application_application_year")
    @Id
    public int getEthicsApplicationApplicationYear() {
        return ethicsApplicationApplicationYear;
    }

    public void setEthicsApplicationApplicationYear(int ethicsApplicationApplicationYear) {
        this.ethicsApplicationApplicationYear = ethicsApplicationApplicationYear;
    }

    @Column(name = "Ethics_Application_application_department")
    @Id
    public String getEthicsApplicationApplicationDepartment() {
        return ethicsApplicationApplicationDepartment;
    }

    public void setEthicsApplicationApplicationDepartment(String ethicsApplicationApplicationDepartment) {
        this.ethicsApplicationApplicationDepartment = ethicsApplicationApplicationDepartment;
    }

    @Column(name = "Ethics_Application_application_number")
    @Id
    public short getEthicsApplicationApplicationNumber() {
        return ethicsApplicationApplicationNumber;
    }

    public void setEthicsApplicationApplicationNumber(short ethicsApplicationApplicationNumber) {
        this.ethicsApplicationApplicationNumber = ethicsApplicationApplicationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoworkersEntityPK that = (CoworkersEntityPK) o;
        return ethicsApplicationApplicationYear == that.ethicsApplicationApplicationYear &&
                ethicsApplicationApplicationNumber == that.ethicsApplicationApplicationNumber &&
                Objects.equals(personUserEmail, that.personUserEmail) &&
                Objects.equals(personDepartmentFacultyName, that.personDepartmentFacultyName) &&
                Objects.equals(personDepartmentDepartmentName, that.personDepartmentDepartmentName) &&
                Objects.equals(personFacultyFacultyName, that.personFacultyFacultyName) &&
                Objects.equals(ethicsApplicationApplicationType, that.ethicsApplicationApplicationType) &&
                Objects.equals(ethicsApplicationApplicationDepartment, that.ethicsApplicationApplicationDepartment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personUserEmail, personDepartmentFacultyName, personDepartmentDepartmentName, personFacultyFacultyName, ethicsApplicationApplicationType, ethicsApplicationApplicationYear, ethicsApplicationApplicationDepartment, ethicsApplicationApplicationNumber);
    }
}
