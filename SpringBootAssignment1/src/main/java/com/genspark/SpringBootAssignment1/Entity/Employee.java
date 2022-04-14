package com.genspark.SpringBootAssignment1.Entity;

public class Employee {
    private int id;
    private String name;
    private String department;
    private int hourlyWage;
    private String company;

    public Employee() {
    }

    public Employee(int id, String name, String department, int hourlyWage, String company) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.hourlyWage = hourlyWage;
        this.company = company;
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
}
