package com.genspark.SpringBootAssignment1.Service;

import com.genspark.SpringBootAssignment1.Entity.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService implements DemoServiceFrame{
    List<Employee> employeeList;

    public DemoService() {
        employeeList = new ArrayList<>();

        employeeList.add(new Employee(0, "Michael", "Java Training 1", 10, "GenSpark 0"));
        employeeList.add(new Employee(1, "George", "Java Training 2", 11, "GenSpark 1"));
        employeeList.add(new Employee(2, "Lucas", "Java Training 3", 12, "GenSpark 2"));

    }

    @Override
    public List<Employee> getEmployees() {
        return employeeList;
    }

    @Override
    public Employee getEmployee(int id) {
        for(var employee : employeeList){
            if(employee.getId() == id){
                return employee;
            }
        }

        return null;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        employeeList.add(employee);

        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        for(var e : employeeList){
            if(e.getId() == employee.getId()){
                e.setCompany(employee.getCompany());
                e.setDepartment(employee.getDepartment());
                e.setHourlyWage(employee.getHourlyWage());
                e.setName(employee.getName());
                return e;
            }
        }

        return null;
    }

    @Override
    public String deleteEmployee(int id) {
        for(var e : employeeList){
            if(e.getId() == id){
                employeeList.remove(e);

                return "Delete Success";
            }
        }

        return "Delete Failed";
    }
}
