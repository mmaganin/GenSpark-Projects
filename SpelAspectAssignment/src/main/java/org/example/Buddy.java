package org.example;

public class Buddy {
    private String name;

    public Buddy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Buddy{" +
                "name='" + name + '\'' +
                '}';
    }
}
