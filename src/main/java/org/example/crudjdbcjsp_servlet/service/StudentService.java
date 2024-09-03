package org.example.crudjdbcjsp_servlet.service;

import org.example.crudjdbcjsp_servlet.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student findById(int id);
//
    void save(Student student);
//
//    void update(int id, Student student);
//
    boolean delete(int id);
}
