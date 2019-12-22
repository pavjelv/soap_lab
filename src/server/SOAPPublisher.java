package server;

import server.service.TaskServiceImpl;

import javax.xml.ws.Endpoint;

public class SOAPPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/test", new TaskServiceImpl());
        System.out.println("Server is started");
    }
}
