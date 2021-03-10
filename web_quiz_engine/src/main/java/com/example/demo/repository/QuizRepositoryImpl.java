package com.example.demo.repository;

import com.example.demo.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizRepositoryImpl {

    List<Quiz> listOfQuizzes = new ArrayList<>();

    public List<Quiz> getListOfQuizzes() {
        return listOfQuizzes;
    }

    public void addToListOfQuizzes(Quiz quiz) {
        listOfQuizzes.add(quiz);
    }

    public Quiz getQuizById(int id) {
        for (Quiz q : listOfQuizzes) {
            if (id == q.getId()) {
                return q;
            }
        }
        return null;
    }
}
