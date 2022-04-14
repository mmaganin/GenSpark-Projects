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
import java.util.Optional;

@Service
public class DemoService implements DemoServiceFrame {
    @Autowired
    private DemoDaoFrame empDb;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("michaelmags33@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        System.out.println("Mail Sent Successfully");

    }

    @Override
    public List<Employee> getEmployees() {
        return empDb.findAll();
    }

    @Override
    public Employee getEmployee(int id) {
        Optional<Employee> emp = empDb.findById(id);
        Employee e = null;
        if (emp.isPresent()) {
            e = emp.get();
        } else {
            throw new RuntimeException("Employee not found");
        }

        return e;
    }

    //POST
    @Override
    public Employee addEmployee(Employee employee) {
        sendEmail(employee.getEmail(), "Registered in Database",
                "This email is associated with a new employee in the database");
        return empDb.save(employee);
    }

    //PUT
    @Override
    public Employee updateEmployee(Employee employee) {
        Employee e = empDb.save(employee);

        return e;
    }

    @Override
    public String deleteEmployee(int id) {
        Employee employee = getEmployee(id);
        empDb.deleteById(id);

        sendEmail("michaelmags33@gmail.com", "Removed employee from Database",
                "The employee named " + employee.getName() + " associated with email " + employee.getEmail() + " has been removed from the database");
        return "Delete Success";


    }
}
