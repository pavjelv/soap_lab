package server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test implements Serializable, Printable {
    private String name;
    private Map<String,Question> questions = new HashMap<>();
    private Question[] questionTransport = new Question[5];
    private transient int questionsCount = 0;

    public Test() {

    }

    public Test(String name) {
        this.name = name;
    }

    public String addQuestion (Question question) {
        if(questionsCount == questionTransport.length) {
            repack();
        }
        questionTransport[questionsCount++] = question;
        questions.put(question.getQuestionText(), question);
        return question.getQuestionText();
    }

    public boolean addQuestions (List<Question> questions) {
        for (Question question : questions) {
            if(questionsCount == this.questionTransport.length) {
                repack();
            }
            this.questionTransport[questionsCount++] = question;
            this.questions.put(question.getQuestionText(), question);
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

    public Map<String, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, Question> questions) {
        this.questions = questions;
    }

    public Question[] getQuestionTransport() {
        return questionTransport;
    }

    public void setQuestionTransport(Question[] questionTransport) {
        this.questionTransport = questionTransport;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    @Override
    public String getText() {
        return name;
    }

    @Override
    public String toString() {
        return "Test: " + name + " questions: " + questionsCount;
    }

    @Override
    public String getTrimmedText() {
        return name;
    }
}
