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

//    public TestFacade() {
//
//        Test test = new Test("TEST");
//        test.addQuestion(new Question("QQQQ", "12222"));
//        test.addQuestion(new Question("ddddd", "12233"));
//
//        Test secondTest = new Test("TEST1");
//        secondTest.addQuestion(new Question("QQQQ1", "1"));
//
//        System.out.println("Test add: " + testService.addTest(test));
//        System.out.println("Second test add: " + testService.addTest(secondTest));
//
//        Test[] list = testService.getAllTests();
//        for (Test t : list) {
//            System.out.println(t.toString());
//        }
//    }

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
}
