package com.genspark.MailSendAssignment.Entity;


import javax.persistence.*;

@Entity
@Table(name="tbl_employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String department;
    @Column(name = "hourly_wage")
    private int hourlyWage;
    private String company;
    private String email;

    public Employee() {
    }

    public Employee(String name, String department, int hourlyWage, String company, String email) {
        this.name = name;
        this.department = department;
        this.hourlyWage = hourlyWage;
        this.company = company;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(int hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", hourlyWage=" + hourlyWage +
                ", company='" + company + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
