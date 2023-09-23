package engine.services;

import engine.exception.QuizModelNotFound;
import engine.models.quiz.AnswerRequest;
import engine.models.quiz.QuizModel;
import engine.models.quiz.QuizRequest;
import engine.models.quiz.QuizResponse;
import engine.repository.QuizModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WebQuizService {
    private final QuizModelRepository quizModelRepository;

    @Autowired
    public WebQuizService(QuizModelRepository quizModelRepository) {
        this.quizModelRepository = quizModelRepository;
    }

    public ResponseEntity<?> addQuiz(QuizRequest request) {
        QuizModel quiz = new QuizModel();
        quiz.setTitle(request.getTitle());
        quiz.setText(request.getText());
        quiz.setOptions(request.getOptions());
        quiz.setAnswer(request.getAnswer());

        quizModelRepository.save(quiz);
        return ResponseEntity.ok(quiz);
    }

    public ResponseEntity<?> getListOfQuizzes() {
        return ResponseEntity.ok(quizModelRepository.findAll());
    }

    public ResponseEntity<?> getQuiz(Long id) {
        return ResponseEntity.ok(
                quizModelRepository.findById(id).orElseThrow(QuizModelNotFound::new)
        );
    }

    public ResponseEntity<?> answerQuiz(Long id, AnswerRequest answerRequest) {
        QuizModel quizModel = quizModelRepository.findById(id).orElseThrow(QuizModelNotFound::new);
        QuizResponse response;
        if (!listsAreEqual(quizModel.getAnswer(), answerRequest.getAnswer())) {
            response = QuizResponse.WRONG_ANSWER;
        } else {
            response = QuizResponse.CORRECT_ANSWER;
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteQuiz(Long id) {
        quizModelRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean listsAreEqual(List<?> list1, List<?> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!Objects.equals(list1.get(i), list2.get(i))) {
                return false;
            }
        }

        return true;
    }


}