package server.service;

import server.entity.Option;
import server.entity.Question;
import server.entity.Test;

import javax.jws.WebService;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebService(endpointInterface = "server.service.TestService")
public class TestServiceImpl implements TestService {
    private static Map<String, Test> testMap = new ConcurrentHashMap<>();
    private static int count = 0;

    @Override
    public String addTest(Test test) {
        String currentIndex = String.valueOf(count++);
        testMap.put(currentIndex, test);
        return currentIndex;
    }

    @Override
    public Test[] getAllTests() {
        Test[] result = new Test[testMap.size()];
        int i = 0;
        for (Test value : testMap.values()) {
            System.out.println(value);
            result[i++] = value;
        }
        System.out.println();
        return result;
    }

    @Override
    public Test getTest(String id) {
        return testMap.get(id);
    }

    @Override
    public Test getTestByName(String name) {
        for (Test test : testMap.values()) {
            if (test.getName().equals(name)) {
                return test;
            }
        }
        return null;
    }

    @Override
    public Question[] getQuestions(String testName) {
        Test existingTest = getTestByName(testName);
        System.out.println("Fetched test " + existingTest);
        Question[] returnQuestions =  getTestByName(testName).getAllQuestions();
        for (Question question : returnQuestions) {
            if(question != null) {
                System.out.println("Existing question for " + testName + " " + question.getQuestionText());
            }
        }
        System.out.println();
        return returnQuestions;
    }

    @Override
    public void updateTest(Test test) {
        getTestByName(test.getName()).addQuestions(Arrays.asList(test.getAllQuestions()));
    }

    @Override
    public void addOptions(String testId, String questionId, Option [] options) {
        testMap.get(testId).getQuestion(questionId).addOptions(options);
    }

    @Override
    public void addQuestions(Question[] questions, String testName) {
        getTestByName(testName).addQuestions(Arrays.asList(questions));
    }

    @Override
    public void addQuestion(Question question, String testName) {
        getTestByName(testName).addQuestion(question);
    }

    @Override
    public void addOption(Question question, Option option) {
        getTestByName(question.getTestId()).getQuestion(question.getQuestionText()).addOption(option);
    }

    @Override
    public Option[] retrieveAllOptions(Question question) {
        return getTestByName(question.getTestId()).getQuestion(question.getQuestionText()).getOptions();
    }
}
