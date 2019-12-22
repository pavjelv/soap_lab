package server.service;

import server.entity.Task;
import shared.TaskStatus;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TaskService {
    @WebMethod
    public String addTask(Task task);

    @WebMethod
    public Task[] getAllTasks();

    @WebMethod
    public Task getTask(String id);

    @WebMethod
    public Task getTaskByName(String name);

    @WebMethod
    public boolean removeTask (String name);

    @WebMethod
    public void reopenTask (String taskName, String newDescription);

    @WebMethod
    public void setNewStatus (String taskName, TaskStatus status);

    @WebMethod
    public Task[] getAllAppropriateTasks();

    @WebMethod
    public void completeTask(String name, String report);
}
