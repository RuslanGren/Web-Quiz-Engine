package engine.models;

import java.util.List;

public class QuizRequest {
    private String title = "The Java Logo";
    private String text = "What is depicted on the Java logo?";
    private List<String> options = List.of("Robot","Tea leaf","Cup of coffee","Bug");

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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
