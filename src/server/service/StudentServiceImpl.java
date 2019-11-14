package server.service;

import server.entity.Student;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@WebService(endpointInterface = "server.service.StudentService")
public class StudentServiceImpl implements StudentService {
    private static Map<Integer,Student> students = new HashMap<Integer,Student>();

    @Override
    public boolean addStudent(Student p) {
        if(students.get(p.getId()) != null) return false;
        students.put(p.getId(), p);
        return true;
    }

    @Override
    public boolean deleteStudent(int id) {
        if(students.get(id) == null) return false;
        students.remove(id);
        return true;
    }

    @Override
    public Student getStudent(int id) {
        return students.get(id);
    }

    @Override
    public Student[] getAllStudents() {
        Set<Integer> ids = students.keySet();
        Student[] p = new Student[ids.size()];
        int i=0;
        for(Integer id : ids){
            p[i] = students.get(id);
            i++;
        }
        return p;
    }

}
