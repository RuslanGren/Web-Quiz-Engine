package engine.models;

import java.util.ArrayList;

public class AnswerRequest {
    private ArrayList<Integer> answer;

    public AnswerRequest() {
    }

    public AnswerRequest(ArrayList<Integer> answer) {
        this.answer = answer;
    }

    public ArrayList<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<Integer> answer) {
        this.answer = answer;
    }
}
