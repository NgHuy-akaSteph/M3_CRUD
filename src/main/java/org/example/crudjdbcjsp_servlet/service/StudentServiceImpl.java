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
    private static final String INSERT_SQL = "INSERT INTO student(student_name, student_gender, student_dob, student_email, student_point,class_id) " + "VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM student WHERE student_id = ?";
    private static final String UPDATE_SQL = "UPDATE student SET student_name = ?, student_gender = ?, student_dob = ?, student_email = ?, student_point = ?, class_id = ? WHERE student_id = ?";
    private static final String FIND_BY_NAME = "CALL GetStudentByName(?)";

    @Override
    public List<Student> findAll() {
        Connection connection = baseRepository.getConnection();
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet result = preparedStatement.executeQuery();
            list = toList(result);
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
            List<Student> list = toList(result);
            if(!list.isEmpty()) {
                student = list.get(0);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return student;
    }

    // Find student by name
    @Override
    public List<Student> findByName(String studentName) {
        Connection connection = baseRepository.getConnection();
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, "%" + studentName + "%");
            ResultSet result = preparedStatement.executeQuery();
            list = toList(result);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    // Insert student to database
    @Override
    public void save(Student student) {
        Connection connection = baseRepository.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)){
            preparedStatement.setString(1, student.getName());
            preparedStatement.setBoolean(2, student.isGender());
            preparedStatement.setDate(3, new Date(student.getDob().getTime()));
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setDouble(5, student.getPoint());
            preparedStatement.setInt(6, student.getCgClass().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update student
    @Override
    public boolean update(Student student) {
        boolean rowUpdated = false;
        Connection connection = baseRepository.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, student.getName());
            statement.setBoolean(2, student.isGender());
            statement.setDate(3, new Date(student.getDob().getTime()));
            statement.setString(4, student.getEmail());
            statement.setDouble(5, student.getPoint());
            statement.setInt(6, student.getCgClass().getId());
            statement.setInt(7, student.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowUpdated;
    }

    // Delete student from database
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

    // Find all class
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


    // Convert ResultSet to List<Student>
    private List<Student> toList(ResultSet result) throws SQLException {
        List<Student> list = new ArrayList<>();
        while(result.next()) {
            int id = result.getInt("student_id");
            String name = result.getString("student_name");
            boolean gender = result.getBoolean("student_gender");
            Date dob = result.getDate("student_dob");
            String email = result.getString("student_email");
            double point = result.getDouble("student_point");
            int classId = result.getInt("class_id");
            String className = result.getString("class_name");
            list.add(new Student(id, name, gender, dob, email, point,new CGClass(classId, className)));
        }
        return list;
    }

    // Check if email exists
    @Override
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM student WHERE student_email = ?";
        Connection connection = baseRepository.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
