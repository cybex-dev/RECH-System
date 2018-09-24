package models.UserSystem;

import dao.UserSystem.EntityPerson;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class DetailedRegistrationForm {

    @Constraints.Required
    private String title;

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

    public DetailedRegistrationForm(String title, String firstname, String lastname, String gender, String mobile, String degreeLevel, String department, String facutly) {
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.degreeLevel = degreeLevel;
        this.department = department;
        this.faculty = facutly;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
