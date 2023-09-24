package engine.services;

import engine.exception.QuizModelNotFound;
import engine.models.quiz.*;
import engine.models.user.User;
import engine.repository.CompletedQuizRepository;
import engine.repository.QuizModelPagingAndSortingRepository;
import engine.repository.QuizModelRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class WebQuizService {
    private final QuizModelRepository quizModelRepository;
    private final UserRepository userRepository;
    private final QuizModelPagingAndSortingRepository quizModelPagingAndSortingRepository;
    private final CompletedQuizRepository completedQuizRepository;

    @Autowired
    public WebQuizService(QuizModelRepository quizModelRepository, UserRepository userRepository,
                          QuizModelPagingAndSortingRepository quizModelPagingAndSortingRepository,
                          CompletedQuizRepository completedQuizRepository) {
        this.quizModelRepository = quizModelRepository;
        this.userRepository = userRepository;
        this.quizModelPagingAndSortingRepository = quizModelPagingAndSortingRepository;
        this.completedQuizRepository = completedQuizRepository;
    }

    public ResponseEntity<?> addQuiz(QuizRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmailIgnoreCase(userDetails.getUsername()).orElseThrow();
        QuizModel quiz = new QuizModel();
        quiz.setTitle(request.getTitle());
        quiz.setText(request.getText());
        quiz.setOptions(request.getOptions());
        quiz.setAnswer(request.getAnswer());
        quiz.setUserId(user.getId());

        quizModelRepository.save(quiz);
        return ResponseEntity.ok(quiz);
    }

    public ResponseEntity<?> getQuiz(Long id) {
        return ResponseEntity.ok(
                quizModelRepository.findById(id).orElseThrow(QuizModelNotFound::new)
        );
    }

    public ResponseEntity<?> answerQuiz(Long id, AnswerRequest answerRequest, UserDetails userDetails) {
        QuizModel quizModel = quizModelRepository.findById(id).orElseThrow(QuizModelNotFound::new);
        Long userId = userRepository.findByEmailIgnoreCase(userDetails.getUsername()).orElseThrow().getId();
        QuizResponse response;
        if (!listsAreEqual(quizModel.getAnswer(), answerRequest.getAnswer())) {
            response = QuizResponse.WRONG_ANSWER;
        } else {
            response = QuizResponse.CORRECT_ANSWER;
            completedQuizRepository.save(new CompletedQuiz(userId, quizModel.getId(), LocalDateTime.now()));
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteQuiz(Long id, UserDetails userDetails) {
        QuizModel quizModel = quizModelRepository.findById(id).orElseThrow(QuizModelNotFound::new);
        User user = userRepository.findById(quizModel.getId()).orElseThrow();
        if (!user.getEmail().equals(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizModelRepository.delete(quizModel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> getQuizzesPage(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<QuizModel> quizModels = quizModelPagingAndSortingRepository.findAll(pageable);
        return ResponseEntity.ok(quizModels);
    }

    public ResponseEntity<?> getPageOfCompletedQuizzes(int page, UserDetails userDetails) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completed_quiz.local_date_time").descending());
        Long userId = userRepository.findByEmailIgnoreCase(userDetails.getUsername()).orElseThrow().getId();
        Page<CompletedQuiz> completedQuizzes = completedQuizRepository.findCompletedQuizByUserId(userId, pageable);
        return ResponseEntity.ok(completedQuizzes);
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