package com.example.test.myapplication;

/**
 * Created by shant on 1/29/2018.
 */

public class Student {
    private String name;
    private String email;
    private String department;
    private Integer mood;

    public Student(String name, String email, String department, Integer mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

    public String getName() {

        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getMood() {
        return mood;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
