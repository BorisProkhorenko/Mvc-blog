package blog.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationForm extends LoginForm {
    @NotNull
    @Size(min=1, max=50)
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
