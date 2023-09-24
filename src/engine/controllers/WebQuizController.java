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

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable("id") Long id) {
        return webQuizService.getQuiz(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addQuiz(@RequestBody @Valid QuizRequest quizRequest,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return webQuizService.addQuiz(quizRequest, userDetails);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<?> answerQuiz(
            @PathVariable("id") Long id,
            @RequestBody AnswerRequest answerRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        return webQuizService.answerQuiz(id, answerRequest, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id") Long id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return webQuizService.deleteQuiz(id, userDetails);
    }

    @GetMapping(value = "", params = "page")
    public ResponseEntity<?> getQuizzesPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        return webQuizService.getQuizzesPage(page);
    }

    @GetMapping(value = "/completed", params = "page")
    public ResponseEntity<?> getPageOfCompletedQuizzes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @AuthenticationPrincipal UserDetails userDetails) {
        return webQuizService.getPageOfCompletedQuizzes(page, userDetails);
    }
}