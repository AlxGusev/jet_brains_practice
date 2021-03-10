package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Quiz {

    public static AtomicInteger idCounter = new AtomicInteger();
    private int id;

    @NotBlank(message = "title can not be empty")
    private String title;

    @NotBlank(message = "text can not be empty")
    private String text;

    @NotEmpty
    @Size(min = 2, message = "must consists of at least 2 option")
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer = new ArrayList<>();

    public Quiz() {}

    public Quiz(String title, String text, List<String> list, List<Integer> answer) {
        this.id = createID();
        this.title = title;
        this.text = text;
        this.options = list;
        this.answer = answer;
    }

    public static int createID() {
        return idCounter.incrementAndGet();
    }

    public int getId() {
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

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + options +
                ", answer=" + answer +
                '}';
    }
}

