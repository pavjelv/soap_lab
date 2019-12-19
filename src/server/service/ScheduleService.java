package server.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Map;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ScheduleService {

    @WebMethod
    public boolean scheduleOrder(String task, Long date);

    @WebMethod
    public String[] getAvailableHours();

    @WebMethod
    public void removeOrder(Long orderStartTime);

    @WebMethod
    public boolean checkCreationPossibility(Long date);

    @WebMethod
    public String[] getSchedule();
}