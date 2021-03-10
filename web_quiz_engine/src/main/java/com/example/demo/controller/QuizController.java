package com.example.demo.controller;

import com.example.demo.message.Feedback;
import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
@Validated
public class QuizController {

    private final QuizRepositoryImpl quizRepository;
    private final String QUIZ_NOT_FOUND = "Quiz not found";

    @Autowired
    public QuizController(QuizRepositoryImpl quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping(path = "/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        Quiz quiz = quizRepository.getQuizById(id);
        if (quiz != null) {
            return quiz;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND);
        }
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepository.getListOfQuizzes();
    }

    @PostMapping
    public Quiz createNewQuiz(@Valid @RequestBody Quiz quiz) {
        Quiz newQuiz = new Quiz(quiz.getTitle(), quiz.getText(), quiz.getOptions(), quiz.getAnswer());
        quizRepository.addToListOfQuizzes(newQuiz);
        return newQuiz;
    }

    @PostMapping(path = "/{id}/solve")
    public String solveQuiz(@PathVariable @Min(1) int id, @RequestBody Map<String, List<Integer>> json) {

        List<Integer> answer = json.get("answer");

        Quiz quiz = quizRepository.getQuizById(id);

        if (quiz == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND);
        }

        if (quiz.getAnswer().equals(answer)) {
            return Feedback.CORRECT_ANSWER.toString();
        } else {
            return Feedback.WRONG_ANSWER.toString();
        }
    }
}
