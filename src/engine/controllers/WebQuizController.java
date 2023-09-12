package engine.controllers;

import engine.models.QuizRequest;
import engine.services.WebQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getQuiz(@PathVariable("id") int id) {
        return webQuizService.getQuiz(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addQuiz(@RequestBody QuizRequest quizRequest) {
        return webQuizService.addQuiz(quizRequest);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<?> answerQuiz(@PathVariable("id") int id, @RequestParam int answer) {
        return webQuizService.answerQuiz(id, answer);
    }

}