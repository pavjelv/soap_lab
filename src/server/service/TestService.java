package server.service;

import server.entity.Option;
import server.entity.Question;
import server.entity.Test;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TestService {
    @WebMethod
    public String addTest(Test test);

    @WebMethod
    public Test[] getAllTests();

    @WebMethod
    public Test getTest(String id);

    @WebMethod
    public Test getTestByName(String name);

    @WebMethod
    public void addOptions(String testId, String questionId, Option [] options);

    @WebMethod
    public void updateTest (Test test);

    @WebMethod
    public Question[] getQuestions(String testName);

    @WebMethod
    public void addQuestions(Question[] questions, String testName);

    @WebMethod
    public void addQuestion(Question question, String testName);
}
