package com.genspark.MailSendAssignment.Controller;

import com.genspark.MailSendAssignment.Entity.Employee;
import com.genspark.MailSendAssignment.Service.DemoServiceFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController {
    @Autowired
    private DemoServiceFrame demoServiceFrame;

    @GetMapping("/")
    public List<Employee> getEmployees(){
        return this.demoServiceFrame.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id){
        return this.demoServiceFrame.getEmployee(id);
    }

    @PutMapping("/")
    public Employee updateEmployee(@RequestBody Employee employee){
        return this.demoServiceFrame.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id){
        return this.demoServiceFrame.deleteEmployee(id);
    }

    @PostMapping("/")
    public Employee addEmployee(@RequestBody Employee employee){
        return this.demoServiceFrame.addEmployee(employee);
    }


}
