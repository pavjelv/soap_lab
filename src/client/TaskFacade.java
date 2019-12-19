package client;

import server.entity.Task;
import server.service.TaskService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public final class TaskFacade {

    private static TaskService service;

    static {
        try {
            URL testServiceURL = new URL("http://localhost:8888/test?wsdl");
            QName name = new QName("http://service.server/", "TaskServiceImplService");
            Service taskConnectService = Service.create(testServiceURL, name);
            service = taskConnectService.getPort(TaskService.class);
        } catch (Throwable e) {
            System.out.println("Error when trying to connect server" + e.getMessage());
        }
    }

    public static void addTask(Task task) {
        service.addTask(task);
    }

    public static void createAndAddTask(String name, int price, Long duration) {
        Task task = new Task(name, price, duration);
        service.addTask(task);
    }

    public static Task[] getAllTasks() {
        return service.getAllTasks();
    }

    public static ArrayList<String> retrieveAllTaskText() {
        ArrayList<String> result = new ArrayList<>();
        Arrays.stream(service.getAllTasks()).forEach(task -> result.add(task.getText()));
        return result;
    }

    public static Task getTaskByName(String name) {
        return service.getTaskByName(name);
    }

    public static void removeTask(String name) {
        service.removeTask(name);
    }
}