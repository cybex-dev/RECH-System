package DAO.UserSystem;

import io.ebean.Finder;
import io.ebean.Model;
import models.UserSystem.UserType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person", schema = "rech_system", catalog = "")
public class EntityPerson extends Model {
    private String userEmail;
    private String userPasswordHash;
    private String userFirstname;
    private String userLastname;
    private String userGender;
    private String currentDegreeLevel;
    private String contactNumberMobile;
    private String personType;
    private String contactOfficeTelephone;
    private String officeAddress;
    private String departmentName;
    private String facultyName;

    public static Finder<String, EntityPerson> find = new Finder<>(EntityPerson.class);

    @Id
    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "user_password_hash")
    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }

    @Basic
    @Column(name = "user_firstname")
    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    @Basic
    @Column(name = "user_lastname")
    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    @Basic
    @Column(name = "user_gender")
    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Basic
    @Column(name = "current_degree_level")
    public String getCurrentDegreeLevel() {
        return currentDegreeLevel;
    }

    public void setCurrentDegreeLevel(String currentDegreeLevel) {
        this.currentDegreeLevel = currentDegreeLevel;
    }

    @Basic
    @Column(name = "contact_number_mobile")
    public String getContactNumberMobile() {
        return contactNumberMobile;
    }

    public void setContactNumberMobile(String contactNumberMobile) {
        this.contactNumberMobile = contactNumberMobile;
    }

    @Basic
    @Column(name = "person_type")
    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    @Basic
    @Column(name = "contact_office_telephone")
    public String getContactOfficeTelephone() {
        return contactOfficeTelephone;
    }

    public void setContactOfficeTelephone(String contactOfficeTelephone) {
        this.contactOfficeTelephone = contactOfficeTelephone;
    }

    @Basic
    @Column(name = "office_address")
    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
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
        EntityPerson that = (EntityPerson) o;
        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userPasswordHash, that.userPasswordHash) &&
                Objects.equals(userFirstname, that.userFirstname) &&
                Objects.equals(userLastname, that.userLastname) &&
                Objects.equals(userGender, that.userGender) &&
                Objects.equals(currentDegreeLevel, that.currentDegreeLevel) &&
                Objects.equals(contactNumberMobile, that.contactNumberMobile) &&
                Objects.equals(personType, that.personType) &&
                Objects.equals(contactOfficeTelephone, that.contactOfficeTelephone) &&
                Objects.equals(officeAddress, that.officeAddress) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userEmail, userPasswordHash, userFirstname, userLastname, userGender, currentDegreeLevel, contactNumberMobile, personType, contactOfficeTelephone, officeAddress, departmentName, facultyName);
    }

    public static EntityPerson getPersonById(String userEmail){
        return find.byId(userEmail);
    }

    public UserType userType(){
        return UserType.valueOf(personType);
    }



    // TODO check if Person should replace pi_id, etc or remain with String


}
