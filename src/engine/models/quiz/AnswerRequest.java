package engine.models.quiz;

import java.util.ArrayList;
import java.util.List;

public class AnswerRequest {
    private List<Integer> answer = new ArrayList<>();

    public AnswerRequest() {
    }

    public AnswerRequest(List<Integer> answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
