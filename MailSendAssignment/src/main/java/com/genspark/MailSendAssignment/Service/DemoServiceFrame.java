package com.genspark.MailSendAssignment.Service;

import com.genspark.MailSendAssignment.Entity.Employee;

import java.util.List;

public interface DemoServiceFrame {
    List<Employee> getEmployees();
    Employee getEmployee(int id);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    String deleteEmployee(int id);
    void sendEmail(String s, String test_mail, String hello_world);
}
