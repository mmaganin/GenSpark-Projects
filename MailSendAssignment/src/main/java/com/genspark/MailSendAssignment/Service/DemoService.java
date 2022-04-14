package com.genspark.MailSendAssignment.Service;

import com.genspark.MailSendAssignment.Dao.DemoDao;
import com.genspark.MailSendAssignment.Dao.DemoDaoFrame;
import com.genspark.MailSendAssignment.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService implements DemoServiceFrame{
    @Autowired
    private DemoDaoFrame empDb;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setFrom("michaelmags33@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        System.out.println("Mail Sent Successfully");

    }

    @Override
    public List<Employee> getEmployees() {
        return empDb.getEmployees();
    }

    @Override
    public Employee getEmployee(int id) {
        Employee emp = null;
        List<Employee> emps = empDb.getEmployee(id);

        if(emps.size() == 1){
            emp = emps.get(0);
        }

        return emp;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        sendEmail(employee.getEmail(), "Registered in Database",
                "This email is associated with a new employee in the database");
        return empDb.addEmployee(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        int result = empDb.updateEmployee(employee);

        if(result == 1){
            return employee;
        }

        return null;
    }

    @Override
    public String deleteEmployee(int id) {
        Employee employee = getEmployee(id);
        int result = empDb.deleteEmployee(id);

        if(result == 1){
            sendEmail("michaelmags33@gmail.com", "Removed employee from Database",
                    "The employee named " + employee.getName() + " associated with email "  + employee.getEmail() + " has been removed from the database");
            return "Delete Success";
        }

        return "Delete Failed";
    }
}
