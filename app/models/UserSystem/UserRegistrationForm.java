package models.UserSystem;

import controllers.UserSystem.Secured;
import dao.UserSystem.EntityPerson;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.swing.text.html.parser.Entity;
import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

public class UserRegistrationForm implements Constraints.Validatable<List<ValidationError>> {
    @Constraints.Email
    @Constraints.Required
    private String email;

    @Constraints.Required
    @Constraints.MinLength(8)
    private String password;

    @Constraints.Required
    @Constraints.MinLength(8)
    private String confirm_password;

    @Constraints.Required
    private String title;

    @Constraints.Required
    private String firstname;

    @Constraints.Required
    private String lastname;

    @Constraints.Required
    private String gender;

    @Constraints.Required
    private String mobile;

    @Constraints.Required
    private String degreeLevel;

    @Constraints.Required
    private String department;

    @Constraints.Required
    private String faculty;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String title, String email, String password, String confirm_password, String firstname, String lastname, String gender, String mobile, String degreeLevel, String department, String facutly) {
        this.title = title;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();

        if (EntityPerson.getPersonById(email) == null) {
            errors.add(new ValidationError("email", "Email address already exists"));
        }

        if (!password.equals(confirm_password)) {
            errors.add(new ValidationError("confirm_password", "Passwords do not match"));
        }

        return (errors.size() > 0 ? errors : null);
    }
}
