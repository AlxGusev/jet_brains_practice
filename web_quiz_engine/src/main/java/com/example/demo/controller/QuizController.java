package com.example.demo.controller;

import com.example.demo.message.Feedback;
import com.example.demo.model.Quiz;
import com.example.demo.servise.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
@Validated
public class QuizController {

    private final QuizService quizService;
    private final String QUIZ_NOT_FOUND = "Quiz not found";

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable int id) {

        Optional<Quiz> quiz = quizService.getQuizById(id);

        if (quiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND);
        }

        return quiz.get();
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping
    public Quiz createNewQuiz(@Valid @RequestBody Quiz quizToAdd) {
        Quiz quiz = new Quiz(quizToAdd.getTitle(), quizToAdd.getText(), quizToAdd.getOptions(), quizToAdd.getAnswer());
        System.out.println(quiz);
        quizService.addQuiz(quiz);
        return quiz;
    }

    @PostMapping("/{id}/solve")
    public String solveQuiz(@PathVariable @Min(1) int id, @RequestBody Map<String, List<Integer>> jsonAnswer) {

        if (!jsonAnswer.containsKey("answer")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there is no answer key");
        }

        Optional<Quiz> optQuiz = quizService.getQuizById(id);

        if (optQuiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND);
        }

        if (optQuiz.get().checkAnswer(jsonAnswer.get("answer"))) {
            return Feedback.CORRECT_ANSWER.toString();
        } else {
            return Feedback.WRONG_ANSWER.toString();
        }
    }

    @DeleteMapping("/{id}/delete")
    public void deleteQuiz(@PathVariable int id) {
        try {
            quizService.deleteQuiz(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND);
        }
    }
}
