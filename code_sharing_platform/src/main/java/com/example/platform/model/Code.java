package com.example.platform.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@JsonPropertyOrder({"date", "code", "time", "views"})
public class Code {

    @Id
    @JsonIgnore
    private UUID id = UUID.randomUUID();
    private String code;
    private LocalDateTime date = LocalDateTime.now();
    private int time;
    private int views;
    @JsonIgnore
    private boolean hasTimeLimit = false;
    @JsonIgnore
    private boolean hasViewLimit = false;

    public Code() {
    }

    public Code(String code, int views) {
        this.code = code;
        this.views = views;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonGetter("date")
    public String getDate() {
        final String DATE_FORMATTER = "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return date.format(formatter);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void updateViews() {
        this.views--;
    }

    @JsonProperty("time")
    public int getTimeLeft() {
        return Math.max(time - (LocalTime.now().toSecondOfDay() - date.toLocalTime().toSecondOfDay()), 0);
    }

    public boolean isHasTimeLimit() {
        return hasTimeLimit;
    }

    public void setHasTimeLimit(boolean hasTimeLimit) {
        this.hasTimeLimit = hasTimeLimit;
    }

    public boolean isHasViewLimit() {
        return hasViewLimit;
    }

    public void setHasViewLimit(boolean hasViewLimit) {
        this.hasViewLimit = hasViewLimit;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", views=" + views +
                '}';
    }
}
