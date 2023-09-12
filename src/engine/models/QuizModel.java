package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class QuizModel {
    private int id;
    private String title;
    private String text;
    private List<String> options;
    @JsonIgnore
    private int answer;

    public QuizModel() {
    }

    public QuizModel(int id, String title, String text, List<String> options, int answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
