package server.service;

import server.entity.Task;

import javax.jws.WebService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebService(endpointInterface = "server.service.TaskService")
public class TaskServiceImpl implements TaskService {
    private static Map<String, Task> taskMap = new ConcurrentHashMap<>();
    private static int count = 0;

    @Override
    public String addTask(Task task) {
        String currentIndex = String.valueOf(count++);
        taskMap.put(currentIndex, task);
        return currentIndex;
    }

    @Override
    public Task[] getAllTasks() {
        Task[] result = new Task[taskMap.size()];
        int i = 0;
        for (Task value : taskMap.values()) {
            System.out.println(value);
            result[i++] = value;
        }
        System.out.println();
        return result;
    }

    @Override
    public Task getTask(String id) {
        return taskMap.get(id);
    }

    @Override
    public Task getTaskByName(String name) {
        return getTaskByTaskName(name);
    }

    @Override
    public boolean removeTask(String name) {
        Task selectedTask = getTaskByTaskName(name);
        for (Map.Entry<String, Task> entry : taskMap.entrySet()) {
            if(entry.getValue().equals(selectedTask)) {
                taskMap.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateTask(Task task) {
        //getTaskByName(task.getName()).addQuestions(Arrays.asList(task.getAllQuestions()));
    }

    public static Task getTaskByTaskName(String name) {
        for (Task task : taskMap.values()) {
            if (task.getName().equals(name) || task.getText().equals(name)) {
                return task;
            }
        }
        return null;
    }

    public Map<String, Task> getAllTasksAsMap() {
        return taskMap;
    }
}
