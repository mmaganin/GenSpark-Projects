package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student = (Student)context.getBean(Student.class);
        Address address = (Address)context.getBean(Address.class);

        System.out.println("App Context generated from Java configuration file");
        System.out.println();
        System.out.println("Test for constructor injection with lists with Student Class: \n" + student.toString());
        System.out.println();
        System.out.println("Test for setter injection with Address class: \n" + address.toString());
    }

}
