package client;

import server.service.ScheduleService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Map;

public final class ScheduleFacade {

    private static ScheduleService service;

    static {
        try {
            URL testServiceURL = new URL("http://localhost:8888/schedule?wsdl");
            QName name = new QName("http://service.server/", "ScheduleServiceImplService");
            Service scheduleConnectService = Service.create(testServiceURL, name);
            service = scheduleConnectService.getPort(ScheduleService.class);
        } catch (Throwable e) {
            System.out.println("Error when trying to connect server" + e.getMessage());
        }
    }

    public static boolean createOrder(String name, Long startDate) {
        return service.scheduleOrder(name, startDate);
    }

    public static String[] getSchedule() {
        return service.getSchedule();
    }

    public static String[] getAvailableHours() {
        return service.getAvailableHours();
    }
}
