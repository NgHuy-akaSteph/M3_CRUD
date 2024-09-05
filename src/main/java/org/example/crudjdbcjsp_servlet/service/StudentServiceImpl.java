package org.example.crudjdbcjsp_servlet.service;

import org.example.crudjdbcjsp_servlet.common.BaseRepository;
import org.example.crudjdbcjsp_servlet.model.Student;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final BaseRepository baseRepository;

    {
        try {
            baseRepository = new BaseRepository();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_ALL = "SELECT * FROM student";
    private static final String FIND_BY_ID = "SELECT * FROM student WHERE student_id = ?";
    private static final String INSERT_SQL = "INSERT INTO student(student_name, student_gender, student_email, student_point) " + "VALUES(?, ?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM student WHERE student_id = ?";
    private static final String UPDATE_SQL = "UPDATE student SET student_name = ?, student_gender = ?, student_email = ?, student_point = ? WHERE student_id = ?";

    @Override
    public List<Student> findAll() {
        Connection connection = baseRepository.getConnection();
        List<Student> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(FIND_ALL);
            while(result.next()) {
                int id = result.getInt("student_id");
                String name = result.getString("student_name");
                boolean gender = result.getBoolean("student_gender");
                String email = result.getString("student_email");
                double point = result.getDouble("student_point");
                list.add(new Student(id, name, gender, email, point));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Student findById(int theId) {
        Connection connection = baseRepository.getConnection();
        Student student = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, theId);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("student_id");
                String name = result.getString("student_name");
                boolean gender = result.getBoolean("student_gender");
                String email = result.getString("student_email");
                double point = result.getDouble("student_point");
                student = new Student(id, name, gender, email, point);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return student;
    }


    @Override
    public void save(Student student) {
        Connection connection = baseRepository.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)){
            preparedStatement.setString(1, student.getName());
            preparedStatement.setBoolean(2, student.isGender());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setDouble(4, student.getPoint());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Student student) {
        boolean rowUpdated = false;
        Connection connection = baseRepository.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, student.getName());
            statement.setBoolean(2, student.isGender());
            statement.setString(3, student.getEmail());
            statement.setDouble(4, student.getPoint());
            statement.setInt(5, student.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowUpdated;
    }

    @Override
    public boolean delete(int id){
        boolean rowDeleted = false;
        Connection connection = baseRepository.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowDeleted;
    }


}
