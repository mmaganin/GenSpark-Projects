package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public Phone getPhone(){
        return new Phone("testPhone");
    }

    @Bean
    public Address getAddress(){
        Address address = new Address();
        address.setCity("Chicago");
        address.setCountry("United States");
        address.setState("Illinois");
        address.setZipcode("47096");
        return address;
    }

    @Bean
    public Student getStudent(){
        List<Phone> listPhones = new LinkedList<>();

        listPhones.add(getPhone());
        listPhones.add(getPhone());
        System.out.println(listPhones);
        return new Student(1, "testName", listPhones, getAddress());
    }
}
