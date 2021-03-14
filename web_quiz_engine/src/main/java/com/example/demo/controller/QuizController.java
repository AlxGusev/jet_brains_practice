package com.example.demo.controller;

import com.example.demo.model.Quiz;
import com.example.demo.model.Result;
import com.example.demo.servise.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Quiz> list = quizService.getAllQuizzes(page, pageSize, sortBy);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/quizzes/completed")
    public ResponseEntity<Page<Result>> getCompletedQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Principal principal) {
        Page<Result> results = quizService.getCompletedQuizzes(page, pageSize, principal);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/quizzes")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz quizToAdd, Principal principal) {
        return quizService.addQuiz(quizToAdd, principal);
    }

    @PostMapping("/quizzes/{id}/solve")
    public String solveQuiz(@PathVariable @Min(1) Long id, @RequestBody Map<String, List<Integer>> answer, Principal principal) {
        return quizService.solveQuiz(id, answer, principal);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable Long id, Principal principal) {
        quizService.deleteQuiz(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
