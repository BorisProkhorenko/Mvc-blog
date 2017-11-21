package blog.forms;

import javax.validation.constraints.NotNull;

public class PostCreationForm {
    @NotNull
    private String text;
    @NotNull
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
