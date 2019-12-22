package server.service;

import server.entity.Task;
import shared.TaskStatus;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebService(endpointInterface = "server.service.TaskService")
public class TaskServiceImpl implements TaskService {
    private static Map<String, Task> taskMap = new ConcurrentHashMap<>();
    private static int count = 0;

    @Override
    public String addTask(Task task) {
        String currentIndex = String.valueOf(count);
        task.setId(count++);
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
    public Task[] getAllAppropriateTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Task value : taskMap.values()) {
            if(value.getStatus() != TaskStatus.CLOSED) {
                System.out.println(value);
                tasks.add(value);
            }
        }
        System.out.println();
        return tasks.toArray(new Task[0]);
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
    public void reopenTask(String taskName, String newDescription) {
        Task task = getTaskByTaskName(taskName);
        task.setDescription(newDescription);
        task.setStatus(TaskStatus.OPEN);
    }

    @Override
    public void setNewStatus(String taskName, TaskStatus status) {
        getTaskByTaskName(taskName).setStatus(status);
    }

    public static Task getTaskByTaskName(String name) {
        for (Task task : taskMap.values()) {
            if (task.toString().equals(name) || task.getText().equals(name) || task.getTrimmedText().equals(name)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void completeTask(String name, String report) {
        Task task = getTaskByTaskName(name);
        task.setReport(report);
        task.setStatus(TaskStatus.COMPLETED);
    }
}
