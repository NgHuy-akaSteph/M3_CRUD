package org.example.crudjdbcjsp_servlet.service;

import org.example.crudjdbcjsp_servlet.common.BaseRepository;
import org.example.crudjdbcjsp_servlet.model.CGClass;
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

    private static final String FIND_ALL_CLASS = "SELECT * FROM class";
    private static final String FIND_ALL = "CALL GetAllStudents();";
    private static final String FIND_BY_ID = "CALL GetStudentById(?)";
    private static final String INSERT_SQL = "INSERT INTO student(student_name, student_gender, student_email, student_point,class_id) " + "VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM student WHERE student_id = ?";
    private static final String UPDATE_SQL = "UPDATE student SET student_name = ?, student_gender = ?, student_email = ?, student_point = ?, class_id = ? WHERE student_id = ?";

    @Override
    public List<Student> findAll() {
        Connection connection = baseRepository.getConnection();
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                int id = result.getInt("student_id");
                String name = result.getString("student_name");
                boolean gender = result.getBoolean("student_gender");
                String email = result.getString("student_email");
                double point = result.getDouble("student_point");
                int classId = result.getInt("class_id");
                String className = result.getString("class_name");
                list.add(new Student(id, name, gender, email, point,new CGClass(classId, className)));
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
            CallableStatement callableStatement = connection.prepareCall(FIND_BY_ID);
            callableStatement.setInt(1, theId);
            ResultSet result = callableStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("student_id");
                String name = result.getString("student_name");
                boolean gender = result.getBoolean("student_gender");
                String email = result.getString("student_email");
                double point = result.getDouble("student_point");
                int classId = result.getInt("class_id");
                String className = result.getString("class_name");
                student = new Student(id, name, gender, email, point, new CGClass(classId, className));
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
            preparedStatement.setInt(5, student.getCgClass().getId());
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
            statement.setInt(5, student.getCgClass().getId());
            statement.setInt(6, student.getId());
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

    @Override
    public List<CGClass> findAllClass() {
        Connection connection = baseRepository.getConnection();
        List<CGClass> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CLASS);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                int id = result.getInt("class_id");
                String name = result.getString("class_name");
                list.add(new CGClass(id, name));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }


}
