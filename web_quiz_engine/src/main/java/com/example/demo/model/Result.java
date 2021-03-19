package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long ResultId;

    private Long id;

    private LocalDateTime completedAt;

    @JsonIgnore
    private String completedBy;

    public Result() {
    }

    public Result(Long id, String completedBy) {
        this.id = id;
        this.completedBy = completedBy;
        completedAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    @Override
    public String toString() {
        return "Result{" +
                "completedAt=" + completedAt +
                ", completedBy='" + completedBy + '\'' +
                '}';
    }
}
