package engine.models.quiz;

public class QuizResponse {
    public final static QuizResponse CORRECT_ANSWER = new QuizResponse(true, "Congratulations, you're right!");
    public final static QuizResponse WRONG_ANSWER = new QuizResponse(false, "Wrong answer! Please, try again.");

    private boolean success;
    private String feedback;

    public QuizResponse() {
    }

    public QuizResponse(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
