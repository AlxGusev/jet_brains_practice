package com.example.demo.servise;

import com.example.demo.message.Feedback;
import com.example.demo.model.Quiz;
import com.example.demo.model.Result;
import com.example.demo.model.User;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository repository;
    private final UserService userService;
    private final ResultRepository resultRepository;
    private final String QUIZ_NOT_FOUND = "Quiz not found";
    private final String USER_NOT_FOUND = "User not found";
    private final String ANSWER = "answer";
    private final String NO_KEY = "there is no 'answer' key";


    @Autowired
    public QuizService(QuizRepository repository, UserService userService, ResultRepository resultRepository) {
        this.repository = repository;
        this.userService = userService;
        this.resultRepository = resultRepository;
    }

    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return repository.findAll(paging);
    }

    public Quiz addQuiz(Quiz quizToAdd, Principal principal) {
        Optional<User> optionalUser = userService.findUserByEmail(principal.getName());

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }
        quizToAdd.setUser(optionalUser.get());
        return repository.save(quizToAdd);
    }

    public Quiz getQuizById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND));
    }

    public Page<Result> getCompletedQuizzes(Integer pageNo, Integer pageSize, Principal principal) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());

        return resultRepository.findAllByCompletedBy(principal.getName(), paging);
    }

    public void deleteQuiz(Long id, Principal principal) {

        Optional<User> user = userService.findUserByEmail(principal.getName());

        if (user.isPresent()) {
            if (getQuizById(id).getUser() == user.get()) {
                repository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND);
        }
    }

    public String solveQuiz(Long id, Map<String, List<Integer>> jsonAnswer, Principal principal) {

        Optional<User> user = userService.findUserByEmail(principal.getName());

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        if (!jsonAnswer.containsKey(ANSWER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_KEY);
        }
        Quiz quiz = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND));

        if (quiz.checkAnswer(jsonAnswer.get(ANSWER))) {
            Result result = new Result(quiz.getId(), user.get().getEmail());
            resultRepository.save(result);
            return Feedback.CORRECT_ANSWER.toString();
        } else {
            return Feedback.WRONG_ANSWER.toString();
        }
    }
}
