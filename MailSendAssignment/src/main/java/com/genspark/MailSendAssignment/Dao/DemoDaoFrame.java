package com.genspark.MailSendAssignment.Dao;

import com.genspark.MailSendAssignment.Entity.Employee;

import java.util.List;

public interface DemoDaoFrame {
    List<Employee> getEmployees();
    List<Employee> getEmployee(int id);
    Employee addEmployee(Employee employee);
    int updateEmployee(Employee employee);
    int deleteEmployee(int id);
}
