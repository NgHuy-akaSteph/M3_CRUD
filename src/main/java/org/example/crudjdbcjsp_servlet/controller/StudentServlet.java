package org.example.crudjdbcjsp_servlet.controller;

import org.example.crudjdbcjsp_servlet.model.CGClass;
import org.example.crudjdbcjsp_servlet.model.Student;
import org.example.crudjdbcjsp_servlet.service.StudentService;
import org.example.crudjdbcjsp_servlet.service.StudentServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="StudentServlet", urlPatterns = {"/"})
public class StudentServlet extends HttpServlet {

    private StudentService studentService;

    public void init() {
        studentService = new StudentServiceImpl();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
                default:
                    selectAll(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    insertStudent(request, response);
                    break;
                case "edit":
                    editStudent(request, response);
                    break;
                default:
                    selectAll(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


    // Select all students
    private void selectAll(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Student> list = studentService.findAll();
        request.setAttribute("list", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("student-list.jsp");
        requestDispatcher.forward(request, response);
    }


    // Show create form to insert new student
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CGClass> list = studentService.findAllClass();
        request.setAttribute("list", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("create-new-student.jsp");
        requestDispatcher.forward(request, response);
    }

    // Insert student
    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        boolean gender = request.getParameter("gender").equals("male");
        String email = request.getParameter("email");
        double point = Double.parseDouble(request.getParameter("point"));
        int classId = Integer.parseInt(request.getParameter("classId"));
        studentService.save(new Student(name, gender, email, point, new CGClass(classId)));
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete student
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if(studentService.delete(id)) {
            List<Student> list = studentService.findAll();
            request.setAttribute("list", list);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("student-list.jsp");
            requestDispatcher.forward(request, response);
        } else {
            System.err.println("Delete failed!");
        }
    }

    // Show edit form to update student
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("student", studentService.findById(id));
        request.setAttribute("list", studentService.findAllClass());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit-student.jsp");
        requestDispatcher.forward(request, response);
    }

    // Update student
    private void editStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        boolean gender = request.getParameter("gender").equals("male");
        int classId = Integer.parseInt(request.getParameter("classId"));
        String email = request.getParameter("email");
        double point = Double.parseDouble(request.getParameter("point"));
        boolean isUpdated = studentService.update(new Student(id, name,gender, email, point, new CGClass(classId)));
        if(isUpdated) {
            List<Student> list = studentService.findAll();
            request.setAttribute("list", list);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("student-list.jsp");
            requestDispatcher.forward(request, response);
        } else {
            System.err.println("Update failed!");
        }
    }


}
