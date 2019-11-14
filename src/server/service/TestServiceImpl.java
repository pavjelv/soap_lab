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
            value.addQuestion(new Question("AAAAAAAAA"));
            result[i++] = value;
        }
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
    public void updateTest(Test test) {
        getTestByName(test.getName()).addQuestions(Arrays.asList(test.getAllQuestions()));
    }

    @Override
    public String addQuestion(String testId, Question question) {
        return testMap.get(testId).addQuestion(question);
    }

    @Override
    public void addOptions(String testId, String questionId, Option [] options) {
        testMap.get(testId).getQuestion(questionId).addOptions(options);
    }
}
