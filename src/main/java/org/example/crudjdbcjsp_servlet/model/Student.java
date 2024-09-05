package org.example.crudjdbcjsp_servlet.model;

public class Student {
    private int id;
    private String name;
    private boolean gender;
    private String email;
    private double point;

    public Student() {
    }

    public Student(int id, String name, boolean gender, String email, double point) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.point = point;
    }

    public Student(String name, boolean gender, String email, double point) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.point = point;
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
