package models.UserSystem;

import dao.UserSystem.EntityPerson;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class DetailedRegistrationForm {

    @Constraints.Required
    private String userTitle;

    @Constraints.Required
    private String firstname;

    @Constraints.Required
    private String lastname;

    @Constraints.Required
    private String mobile;

    @Constraints.Required
    private String degreeLevel;

    @Constraints.Required
    private String department;

    @Constraints.Required
    private String faculty;

    public DetailedRegistrationForm() {
    }

    public DetailedRegistrationForm(@Constraints.Required String userTitle, @Constraints.Required String firstname, @Constraints.Required String lastname, @Constraints.Required String mobile, @Constraints.Required String degreeLevel, @Constraints.Required String department, @Constraints.Required String faculty) {
        this.userTitle = userTitle;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.degreeLevel = degreeLevel;
        this.department = department;
        this.faculty = faculty;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(String degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
