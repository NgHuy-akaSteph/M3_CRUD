package org.example.crudjdbcjsp_servlet.service;

import org.example.crudjdbcjsp_servlet.model.CGClass;
import org.example.crudjdbcjsp_servlet.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student findById(int id);

    List<Student> findByName(String name);

    void save(Student student);

    boolean update(Student student);

    boolean delete(int id);

    List<CGClass> findAllClass();

    boolean emailExists(String email) throws SQLException;
}
