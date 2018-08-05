package models.UserSystem;

public class UserRegistrationForm {
    private String email;
    private String password;
    private String confirm_password;
    private String title;
    private String firstname;
    private String lastname;
    private String gender;
    private String mobile;
    private String degreeLevel;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String title, String email, String password, String confirm_password, String firstname, String lastname, String gender, String mobile, String degreeLevel) {
        this.title = title;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.mobile = mobile;
        this.degreeLevel = degreeLevel;
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
}
