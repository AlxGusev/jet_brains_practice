package com.example.demo.message;

public enum Feedback {

    CORRECT_ANSWER(true, "Congratulations, you're right!"),
    WRONG_ANSWER(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    Feedback(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "{\"success\":" + success + ",\"feedback\":\"" + feedback +"\"}";
    }
}
