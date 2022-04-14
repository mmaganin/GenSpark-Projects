package com.genspark.SpringBootAssignment1.Service;

import com.genspark.SpringBootAssignment1.Entity.Employee;

import java.util.List;

public interface DemoServiceFrame {
    List<Employee> getEmployees();
    Employee getEmployee(int id);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    String deleteEmployee(int id);
}
