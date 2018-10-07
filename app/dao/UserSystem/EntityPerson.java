package dao.UserSystem;

import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ReviewSystem.EntityReviewerApplications;
import io.ebean.Finder;
import io.ebean.Model;
import models.UserSystem.UserType;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "person", schema = "rech_system")
public class EntityPerson extends Model {
    private String userEmail;
    private String userPasswordHash;
    private String userFirstname;
    private String userLastname;
    private String userTitle;
    private String currentDegreeLevel;
    private String contactNumberMobile;
    private String personType;
    private String contactOfficeTelephone;
    private String officeAddress;
    private String departmentName;
    private String facultyName;
    private String officeCampus;
    private Boolean isVerified;

    public static Finder<String, dao.UserSystem.EntityPerson> find = new Finder<>(dao.UserSystem.EntityPerson.class);

    public static List<EntityPerson> findAllReviewers() {
        return find.all().stream()
                .filter(entityPerson -> !entityPerson.userType().equals(UserType.PrimaryInvestigator) ||
                        !entityPerson.userType().equals(UserType.PrimaryResponsiblePerson) ||
                                !entityPerson.userType().equals(UserType.RCD))
                .collect(Collectors.toList());
    }

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
        if (userLastname == null){
            setUserLastname("");
            save();
        }
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    @Basic
    @Column(name = "user_title")
    public String getUserTitle() {
        if (userTitle == null)
            return "";
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
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
        if (contactNumberMobile == null){
            setContactNumberMobile("");
            save();
        }
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
        if (contactOfficeTelephone == null){
            setContactOfficeTelephone("");
            save();
        }
        return contactOfficeTelephone;
    }

    public void setContactOfficeTelephone(String contactOfficeTelephone) {
        this.contactOfficeTelephone = contactOfficeTelephone;
    }

    @Basic
    @Column(name = "office_campus")
    public String getOfficeCampus() {
        if (officeCampus == null){
            setOfficeCampus("");
            save();
        }
        return officeCampus;
    }

    public void setOfficeCampus(String officeCampus) {
        this.officeCampus = officeCampus;
    }

    @Basic
    @Column(name = "office_address")
    public String getOfficeAddress() {
        if (officeAddress == null){
            setOfficeAddress("");
            save();
        }
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

    @Basic
    @Column(name = "is_verified")
    public Boolean getIsVerified() {
        if (isVerified == null) {
            setIsVerified(false);
            save();
            return false;
        }
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
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
                Objects.equals(userTitle, that.userTitle) &&
                Objects.equals(currentDegreeLevel, that.currentDegreeLevel) &&
                Objects.equals(contactNumberMobile, that.contactNumberMobile) &&
                Objects.equals(personType, that.personType) &&
                Objects.equals(contactOfficeTelephone, that.contactOfficeTelephone) &&
                Objects.equals(officeAddress, that.officeAddress) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(isVerified, that.isVerified) &&
                Objects.equals(officeCampus, that.officeCampus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userEmail, userPasswordHash, userFirstname, userLastname, userTitle, currentDegreeLevel, contactNumberMobile, personType, contactOfficeTelephone, officeAddress, departmentName, facultyName, officeCampus, isVerified);
    }

    public static dao.UserSystem.EntityPerson getPersonById(String userEmail){
        return find.byId(userEmail);
    }

    public UserType userType(){
        return UserType.parse(personType);
    }

    public static String getRCD() {
        return find.all().stream().filter(entityPerson -> entityPerson.userType().equals(UserType.RCD)).map(dao.UserSystem.EntityPerson::getUserEmail).findFirst().orElse("");
    }

    public static String getRTI(String facultyName) {
        return find.all().stream()
                .filter(entityPerson -> (entityPerson.facultyName != null) && (entityPerson.facultyName.equals(facultyName) && entityPerson.userType().equals(UserType.FacultyRTI)))
                .map(dao.UserSystem.EntityPerson::getUserEmail)
                .findFirst()
                .orElse(null);
    }

    public static String getHod(String departmentName) {
        return find.all().stream()
                .filter(entityPerson -> (entityPerson.departmentName != null) && (entityPerson.departmentName.equals(departmentName) && entityPerson.userType().equals(UserType.DepartmentHead)))
                .map(EntityPerson::getUserEmail)
                .findFirst()
                .orElse(null);
    }

    public static boolean authenticate(String email, String password) {
        dao.UserSystem.EntityPerson entityPerson = find.byId(email);
        if (entityPerson == null)
            return false;
        return BCrypt.checkpw(password, entityPerson.getUserPasswordHash());
    }

    public boolean authenticate(String password) {
        return authenticate(userEmail, password);
    }

    public static String getPersonType(String email) {
        dao.UserSystem.EntityPerson entityPerson = find.byId(email);
        if (entityPerson == null)
            return "";
        return entityPerson.getPersonType();
    }

    public String getFullName() {
        return userTitle.concat(" ").concat(userFirstname).concat(" ").concat(userLastname);
    }
}
