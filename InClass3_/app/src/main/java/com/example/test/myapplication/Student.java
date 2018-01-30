/**
 * Assignment 3
 * File name: Student.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
package com.example.test.myapplication;

import java.io.Serializable;

/**
 * Created by shant on 1/29/2018.
 */

public class Student implements Serializable {
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
                ", department='" + department + '\'' +
                ", mood=" + mood +
                '}';
    }
}
