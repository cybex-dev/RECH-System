package dao.UserSystem;

import io.ebean.Finder;
import io.ebean.Model;
import models.UserSystem.UserType;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Person", schema = "rech_system")
public class EntityPerson extends Model {
    private String userEmail;
    private String userPasswordHash;
    private String userTitle;
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

    public static Finder<String, dao.UserSystem.EntityPerson> find = new Finder<>(dao.UserSystem.EntityPerson.class);

    public static boolean authenticate(String email, String password) {
        EntityPerson entityPerson = find.byId(email);
        if (entityPerson == null)
            return false;
        return BCrypt.checkpw(password, entityPerson.getUserPasswordHash());
    }

    public static String getPersonType(String email) {
        EntityPerson entityPerson = find.byId(email);
        if (entityPerson == null)
            return "";
        return entityPerson.getPersonType();
    }

    @Id
    @Column(name = "user_email", nullable = false, length = 100)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "user_password_hash", nullable = false, length = 100)
    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }

    @Basic
    @Column(name = "user_title", nullable = true, length = 50)
    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    @Basic
    @Column(name = "user_firstname", nullable = true, length = 50)
    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    @Basic
    @Column(name = "user_lastname", nullable = true, length = 50)
    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    @Basic
    @Column(name = "user_gender", nullable = true, length = 50)
    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Basic
    @Column(name = "current_degree_level", nullable = true, length = 20)
    public String getCurrentDegreeLevel() {
        return currentDegreeLevel;
    }

    public void setCurrentDegreeLevel(String currentDegreeLevel) {
        this.currentDegreeLevel = currentDegreeLevel;
    }

    @Basic
    @Column(name = "contact_number_mobile", nullable = true, length = 15)
    public String getContactNumberMobile() {
        return contactNumberMobile;
    }

    public void setContactNumberMobile(String contactNumberMobile) {
        this.contactNumberMobile = contactNumberMobile;
    }

    @Basic
    @Column(name = "person_type", nullable = true, length = 10)
    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    @Basic
    @Column(name = "contact_office_telephone", nullable = true, length = 15)
    public String getContactOfficeTelephone() {
        return contactOfficeTelephone;
    }

    public void setContactOfficeTelephone(String contactOfficeTelephone) {
        this.contactOfficeTelephone = contactOfficeTelephone;
    }

    @Basic
    @Column(name = "office_address", nullable = true, length = 40)
    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    @Basic
    @Column(name = "department_name", nullable = false, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "faculty_name", nullable = false, length = 50)
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
                Objects.equals(userTitle, that.userTitle) &&
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

        return Objects.hash(userEmail, userPasswordHash, userTitle, userFirstname, userLastname, userGender, currentDegreeLevel, contactNumberMobile, personType, contactOfficeTelephone, officeAddress, departmentName, facultyName);
    }

    public static dao.UserSystem.EntityPerson getPersonById(String userEmail){
        return find.byId(userEmail);
    }

    public UserType userType(){
        return UserType.valueOf(personType);
    }

    public static String getRCD() {
        return find.all().stream().filter(entityPerson -> entityPerson.userType().equals(UserType.RCD)).map(dao.UserSystem.EntityPerson::getUserEmail).findFirst().orElse("");
    }

    public static String getRTI(String facultyName) {
        return find.all().stream()
                .filter(entityPerson -> entityPerson.facultyName.equals(facultyName) && entityPerson.userType().equals(UserType.FacultyRTI))
                .map(dao.UserSystem.EntityPerson::getUserEmail)
                .findFirst()
                .orElse("");
    }

    public static String getHod(String departmentName) {
        return find.all().stream()
                .filter(entityPerson -> entityPerson.departmentName.equals(departmentName) && entityPerson.userType().equals(UserType.DepartmentHead))
                .map(dao.UserSystem.EntityPerson::getUserEmail)
                .findFirst()
                .orElse("");
    }

}
