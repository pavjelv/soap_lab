package server.service;

import server.entity.Student;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface StudentService {

    @WebMethod
    public boolean addStudent(Student p);

    @WebMethod
    public boolean deleteStudent(int id);

    @WebMethod
    public Student getStudent(int id);

    @WebMethod
    public Student[] getAllStudents();
}