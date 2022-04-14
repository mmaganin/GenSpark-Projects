package com.genspark.MailSendAssignment.Dao;

import com.genspark.MailSendAssignment.Entity.Employee;
import com.genspark.MailSendAssignment.MailSendAssignmentApplication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

public class DemoDao{
    private Session session;
    private Transaction t;
    private SessionFactory sessionFactory;

    public DemoDao(){
        //sessionFactory = MailSendAssignmentApplication.getSessionFactory();
    }

    public void startTransaction(){
        session = sessionFactory.openSession();
        t = session.beginTransaction();
    }

    public void endTransaction(){
        t.commit();
        session.close();
    }

    public List<Employee> getEmployees() {
        startTransaction();

        Query query = session.createQuery("from Employee");
		List<Employee> empList = query.list();

        endTransaction();
        return empList;
    }

    public List<Employee> getEmployee(int id) {
        startTransaction();

        Query query = session.createQuery("from Employee where id=:currId");
		query.setParameter("currId", id);
        List<Employee> empList = query.list();

        endTransaction();
        return empList;
    }

    public Employee addEmployee(Employee employee) {
        startTransaction();
        session.save(employee);

        endTransaction();
        return employee;
    }

    public int updateEmployee(Employee employee) {
        startTransaction();
        Query query = session.createQuery("update Employee " +
                "set name=:name, department=:department, hourlyWage=:hourlyWage, company=:company, email=:email " +
                "where id=:id");
        query.setParameter("name", employee.getName());
        query.setParameter("department", employee.getDepartment());
        query.setParameter("hourlyWage", employee.getHourlyWage());
        query.setParameter("company", employee.getCompany());
        query.setParameter("email", employee.getEmail());
        query.setParameter("id", employee.getId());
        int result = query.executeUpdate();

        endTransaction();
        return result;
    }

    public int deleteEmployee(int id) {
        startTransaction();
        Query query = session.createQuery("delete from Employee where id=:id");
        query.setParameter("id", id);
        int result = query.executeUpdate();

        endTransaction();
        return result;
    }
}
