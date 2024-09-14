package org.example.crudjdbcjsp_servlet.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Student {
    private int id;
    private String name;
    private boolean gender;
    private Date dob;
    private String email;
    private double point;
    private CGClass cgClass;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Student() {
    }

    public Student(String name, boolean gender, Date dob, String email, double point, CGClass cgClass) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.point = point;
        this.cgClass = cgClass;
    }

    public Student(int id, String name, boolean gender, Date dob, String email, double point, CGClass cgClass) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.point = point;
        this.cgClass = cgClass;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getDateFormat() throws ParseException {
        return dateFormat.format(dob);
    }

    public void setDateFormat(Date dob) {
        this.dob = Date.valueOf(dateFormat.format(dob));
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

    public CGClass getCgClass() {
        return cgClass;
    }

    public void setCgClass(CGClass cgClass) {
        this.cgClass = cgClass;
    }
}
