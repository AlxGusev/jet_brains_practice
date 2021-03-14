package com.example.demo.controller;

import com.example.demo.model.Quiz;
import com.example.demo.servise.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/quizzes")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz quizToAdd, Principal principal) {
        return quizService.addQuiz(quizToAdd, principal);
    }

    @PostMapping("/quizzes/{id}/solve")
    public String solveQuiz(@PathVariable @Min(1) int id, @RequestBody Map<String, List<Integer>> answer) {
        return quizService.solveQuiz(id, answer);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable int id, Principal principal) {
        quizService.deleteQuiz(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
