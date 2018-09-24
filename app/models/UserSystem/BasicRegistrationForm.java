package models.UserSystem;

import dao.UserSystem.EntityPerson;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class BasicRegistrationForm implements Constraints.Validatable<List<ValidationError>> {
    @Constraints.Email
    @Constraints.Required
    private String email;

    @Constraints.Required
    @Constraints.MinLength(8)
    private String password;

    @Constraints.Required
    @Constraints.MinLength(8)
    private String confirm_password;

    public BasicRegistrationForm(@Constraints.Email @Constraints.Required String email, @Constraints.Required @Constraints.MinLength(8) String password, @Constraints.Required @Constraints.MinLength(8) String confirm_password) {
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
    }

    public BasicRegistrationForm() {
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

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
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
