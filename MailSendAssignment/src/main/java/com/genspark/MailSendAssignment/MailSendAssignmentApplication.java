package com.genspark.MailSendAssignment;

import com.genspark.MailSendAssignment.Entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MailSendAssignmentApplication {
	private static SessionFactory sessionFactory;


	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		MailSendAssignmentApplication.sessionFactory = sessionFactory;
	}

	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		MailSendAssignmentApplication.setSessionFactory(cfg.buildSessionFactory());
		SpringApplication.run(MailSendAssignmentApplication.class, args);

	}

}
