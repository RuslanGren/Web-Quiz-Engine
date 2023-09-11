package engine.controllers;

import engine.models.QuizRequest;
import engine.models.QuizResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class WebQuizController {
    @GetMapping("")
    public ResponseEntity<?> getQuiz() {
        return new ResponseEntity<>(new QuizRequest(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> guessQuiz(@RequestParam int answer) {
        QuizResponse response = new QuizResponse();
        if (answer == 2) {
            response.setSuccess(true);
            response.setFeedback("Congratulations, you're right!");
        } else {
            response.setSuccess(false);
            response.setFeedback("Wrong answer! Please, try again.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}