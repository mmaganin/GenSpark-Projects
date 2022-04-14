package com.genspark.MailSendAssignment.Dao;

import com.genspark.MailSendAssignment.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemoDaoFrame extends JpaRepository<Employee, Integer> {
//    List<Employee> getEmployees();
//    List<Employee> getEmployee(int id);
//    Employee addEmployee(Employee employee);
//    int updateEmployee(Employee employee);
//    int deleteEmployee(int id);
}
