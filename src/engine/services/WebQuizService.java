package engine.services;

import engine.models.QuizModel;
import engine.models.QuizRequest;
import engine.models.QuizResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebQuizService {
    private final List<QuizModel> list = new ArrayList<>();
    private int id = 0;

    public ResponseEntity<?> addQuiz(QuizRequest request) {
        id++;
        QuizModel quiz = new QuizModel();
        quiz.setId(id);
        quiz.setTitle(request.getTitle());
        quiz.setText(request.getText());
        quiz.setOptions(request.getOptions());
        quiz.setAnswer(request.getAnswer());
        list.add(quiz);

        return ResponseEntity.ok(quiz);
    }

    public ResponseEntity<?> getListOfQuizzes() {
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<?> getQuiz(int id) {
        if (list.size() < id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list.get(id - 1));
    }

    public ResponseEntity<?> answerQuiz(int id, int[] answer) {
        QuizResponse response = new QuizResponse(false, "Wrong answer! Please, try again.");
        if () {
            response.setSuccess(true);
            response.setFeedback("Congratulations, you're right!");
        }
        return ResponseEntity.ok(response);
    }

}