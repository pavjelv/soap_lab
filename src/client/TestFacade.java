package client;

import server.entity.Question;
import server.entity.Test;
import server.service.TestService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public final class TestFacade {

    private static TestService service;

    static {
        try {
            URL testServiceURL = new URL("http://localhost:8888/test?wsdl");
            QName name = new QName("http://service.server/", "TestServiceImplService");
            Service testConnectService = Service.create(testServiceURL, name);
            service = testConnectService.getPort(TestService.class);
        } catch (Throwable e) {
            System.out.println("Error when trying to connect server" + e.getMessage());
        }
    }

    public static void addTest(Test test) {
        service.addTest(test);
    }

    public static void createAndAddTest(String name) {
        Test test = new Test(name);
        service.addTest(test);
    }

    public static Test[] getAllTests() {
        return service.getAllTests();
    }

    public static ArrayList<String> retrieveAllTestNames() {
        ArrayList<String> result = new ArrayList<>();
        Stream<Test> stream = Arrays.stream(service.getAllTests());
        stream.forEach(test -> result.add(test.getName()));
        return result;
    }

    public static Test getTestByName(String name) {
        return service.getTestByName(name);
    }

    public static void updateTest(Test test) {
        service.updateTest(test);
    }

    public static Question[] getQuestions(Test test) {
        return service.getQuestions(test.getName());
    }

    public static void addQuestions(Test test, Question[] questions) {
        service.addQuestions(questions, test.getName());
    }

    public static void addQuestion(Test test, Question question) {
        service.addQuestion(question, test.getName());
    }
}
