package engine.controllers;

import engine.models.quiz.AnswerRequest;
import engine.models.quiz.QuizRequest;
import engine.services.WebQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class WebQuizController {
    private final WebQuizService webQuizService;

    @Autowired
    public WebQuizController(WebQuizService webQuizService) {
        this.webQuizService = webQuizService;
    }

    @GetMapping("")
    public ResponseEntity<?> getQuizzes() {
        return webQuizService.getListOfQuizzes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable("id") Long id) {
        return webQuizService.getQuiz(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addQuiz(@RequestBody @Valid QuizRequest quizRequest) {
        return webQuizService.addQuiz(quizRequest);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<?> answerQuiz(@PathVariable("id") Long id, @RequestBody AnswerRequest answerRequest) {
        return webQuizService.answerQuiz(id, answerRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id") Long id) {
        return webQuizService.deleteQuiz(id);
    }

}