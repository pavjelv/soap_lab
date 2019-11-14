package server.entity;

import java.io.Serializable;

public class Question implements Serializable {

    private String questionText;
    private String testId;
    private Option[] options;
    private boolean correctlyAnswered;
    private int optionIndex = 0;

    public Question (String questionText, String testId) {
        this.questionText = questionText;
        this.testId = testId;
        options = new Option[10];
    }

    public Question (String questionText) {
        this.questionText = questionText;
        options = new Option[10];
    }

    private void repack() {
        Option[] newOptions = new Option[options.length * 2];
        for (int i = 0; i < options.length; i++) {
            newOptions[i] = options[i];
        }
        options = newOptions;
    }

    public void addOption (Option option) {
        if(optionIndex == options.length) {
            repack();
        }
        options[optionIndex++] = option;
    }

    public void addOptions (Option[] options) {
        for (int i = 0; i < options.length; i++) {
            if(optionIndex == this.options.length) {
                repack();
            }
            this.options[optionIndex++] = options[i];
        }
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getTestId() {
        return testId;
    }

    public Option[] getOptions() {
        return options;
    }

    public boolean isCorrectlyAnswered() {
        return correctlyAnswered;
    }

    public void setCorrectlyAnswered(boolean correctlyAnswered) {
        this.correctlyAnswered = correctlyAnswered;
    }

}