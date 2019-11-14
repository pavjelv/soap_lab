package server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test implements Serializable {

    private String name;
    private Map<String,Question> questions = new HashMap<>();
    private Question[] questionTransport = new Question[5];
    private int questionsCount = 0;

    public Test() {

    }

    public Test(String name) {
        this.name = name;
    }

    public String addQuestion (Question question) {
        if(questionsCount == questionTransport.length) {
            repack();
        }
        questionTransport[questionsCount] = question;
        String currentIndex = String.valueOf(questionsCount++);
        questions.put(currentIndex, question);
        return currentIndex;
    }

    public boolean addQuestions (List<Question> questions) {
        for (Question question : questions) {
            if(questionsCount == this.questionTransport.length) {
                repack();
            }
            this.questionTransport[questionsCount] = question;
            this.questions.put(String.valueOf(questionsCount++), question);
        }
        return true;
    }

    private void repack() {
        Question[] newQuestions = new Question[questionTransport.length * 2];
        for (int i = 0; i < questionTransport.length; i++) {
            questionTransport[i] = questionTransport[i];
        }
        questionTransport = newQuestions;
    }

    public String getName() {
        return name;
    }

    public Question getQuestion(String id) {
        return questions.get(id);
    }

    public Question[] getAllQuestions() {
        return questionTransport;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test: " + name + "questions: " + questionsCount;
    }

}
