package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {
    @Value("#{'GenSpark academy'}")
    private String school;
    @Value("#{'Java'}")
    private String langLearned;

    @Value("#{student.langLearned.equals('Java') ? 'Java Developer' : 'Other dev'}")
    private String role;

    @Value("#{T(org.example.Student).getRandomID()}")
    private int id;

    @Value("#{new org.example.Buddy('my buddy')}")
    private Buddy buddy;


    public static int getRandomID(){
        int max = 100;
        int min = 0;
        int randNum = (int) (Math.random() * (max - min + 1) + min);
        return randNum;
    }

    public String getSchool() {
        return school;
    }

    public String getLangLearned() {
        return langLearned;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "Student{" +
                "school='" + school + '\'' +
                ", langLearned='" + langLearned + '\'' +
                ", role='" + role + '\'' +
                ", id=" + id +
                ", buddy=" + buddy +
                '}';
    }
}
