package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "title can not be empty")
    private String title;

    @NotBlank(message = "text can not be empty")
    private String text;

    @NotEmpty
    @Size(min = 2, message = "must consists of at least 2 option")
    @ElementCollection
    private List<String> options = new ArrayList<>();

    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    public Quiz() {}

    public Quiz(String title, String text, List<String> list, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = list;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public boolean checkAnswer(List<Integer> list) {
        return answer.size() == list.size() && answer.containsAll(list);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\"=\"" + id + "\"" +
                ", \"title\"=\"" + title + "\"" +
                ", \"text\"=\"" + text + "\"" +
                ", \"options\"=" + options +
                ", \"answer\"=" + answer +
                '}';
    }
}

