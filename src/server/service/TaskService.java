package server.service;

import server.entity.Task;

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
    public void updateTask (Task task);

    @WebMethod
    public boolean removeTask (String name);

}
