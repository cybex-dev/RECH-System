package models.UserSystem;

import dao.UserSystem.EntityPerson;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to contain user login info
 */
public class UserLoginForm implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Email
    private String email;

    @Constraints.Required
    private String password;

    private Boolean remember;

    public UserLoginForm() {
    }

    public UserLoginForm(String email, String password, Boolean remember) {
        this.email = email;
        this.password = password;
        this.remember = remember;
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

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }


    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();

        if (!EntityPerson.authenticate(email, password)) {
            errors.add(new ValidationError("", "Email or password is incorrect"));
        }

        return (errors.size() > 0) ? errors : null;
    }
}
