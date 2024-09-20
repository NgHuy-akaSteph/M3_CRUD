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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@WebServlet(name="StudentServlet", urlPatterns = {"/"})
public class StudentServlet extends HttpServlet {

    private StudentService studentService;

    public void init() {
        studentService = new StudentServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    createStudent(request, response);
                    break;
                case "edit":
                    editStudent(request, response);
                    break;
                case "filter":
                    filterStudent(request, response);
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
        request.setAttribute("list", studentService.findAll());
        request.setAttribute("listClass", studentService.findAllClass());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("list.jsp");
        requestDispatcher.forward(request, response);
    }


    // Show create form to insert new student
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CGClass> list = studentService.findAllClass();
        request.setAttribute("list", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("create-form.jsp");
        requestDispatcher.forward(request, response);
    }

    // Insert student
    private void createStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        boolean gender = request.getParameter("gender").equals("male");
        String email = request.getParameter("email");
        Date dob = Date.valueOf(request.getParameter("dob"));
        double point = Double.parseDouble(request.getParameter("point"));
        int classId = Integer.parseInt(request.getParameter("classId"));
        if (isNotValidName(name)) {
            request.setAttribute("errorMessage", "Tên chỉ bao gồm chữ cái và khoảng trắng, không chứa kí tự đặc biệt hay số !!!");
            forwardToCreateForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        if (isNotValidDob(dob)) {
            request.setAttribute("errorMessage", "Độ tuổi của bạn không phù hợp !!!");
            forwardToCreateForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        if (studentService.findByEmail(email) != null) {
            request.setAttribute("errorMessage", "Email đã tồn tại !!!");
            forwardToCreateForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        if (point < 0 || point > 10) {
            request.setAttribute("errorMessage", "Điểm số chỉ trong khoảng 0 đến 10 !!!");
            forwardToCreateForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        studentService.save(new Student(name, gender, dob, email, point, new CGClass(classId)));
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void forwardToCreateForm(HttpServletRequest request, HttpServletResponse response, String name, boolean gender, Date dob, String email, double point, int classId) throws ServletException, IOException {
        Student student = new Student(name, gender, dob, email, point, new CGClass(classId));
        request.setAttribute("student", student);
        List<CGClass> classList = studentService.findAllClass();
        request.setAttribute("list", classList);
        request.getRequestDispatcher("create-form.jsp").forward(request, response);
    }

    // Delete student
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if(studentService.delete(id)) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            System.err.println("Delete failed!");
        }
    }

    // Show edit form to update student
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentService.findById(id);
        List<CGClass> classList = studentService.findAllClass();
        request.setAttribute("student", existingStudent);
        request.setAttribute("list", classList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-form.jsp");
        dispatcher.forward(request, response);
    }

    // Update student
    private void editStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        boolean gender = request.getParameter("gender").equals("male");
        Date dob = Date.valueOf(request.getParameter("dob"));
        int classId = Integer.parseInt(request.getParameter("classId"));
        String email = request.getParameter("email");
        double point = Double.parseDouble(request.getParameter("point"));
        if (isNotValidName(name)) {
            request.setAttribute("errorMessage", "Tên chỉ bao gồm chữ cái và khoảng trắng, không chứa kí tự đặc biệt hay số !!!");
            forwardToEditForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        if (isNotValidDob(dob)) {
            request.setAttribute("errorMessage", "Độ tuổi của bạn không phù hợp !!!");
            forwardToEditForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        Student existingStudent = studentService.findByEmail(email);
        if (existingStudent != null && existingStudent.getId() != id) {
            request.setAttribute("errorMessage", "Email đã tồn tại !!!");
            forwardToEditForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        if (point < 0 || point > 10) {
            request.setAttribute("errorMessage", "Điểm số chỉ trong khoảng 0 đến 10 !!!");
            forwardToEditForm(request, response, name, gender, dob, email, point, classId);
            return;
        }

        studentService.update(new Student(id, name, gender, dob, email, point, new CGClass(classId)));
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void forwardToEditForm(HttpServletRequest request, HttpServletResponse response, String name, boolean gender, Date dob, String email, double point, int classId) throws ServletException, IOException {
        Student student = new Student(name, gender, dob, email, point, new CGClass(classId));
        request.setAttribute("student", student);
        List<CGClass> classList = studentService.findAllClass();
        request.setAttribute("list", classList);
        request.getRequestDispatcher("edit-form.jsp").forward(request, response);
    }

    private boolean isNotValidName(String name) {
        return !name.matches("^[\\p{L}\\s]{1,150}$");
    }

    private boolean isNotValidDob(Date dob) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = dob.toLocalDate();
        int age = Period.between(birthDate, currentDate).getYears();
        return !(age >= 15 && age <= 45);
    }

    private void filterStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String className = request.getParameter("className");
        if(className.equals("all")) className = null;//set className = null để không filter theo class

        Date startDate = null;
        Date endDate = null;

        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = Date.valueOf(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = Date.valueOf(endDateStr);
        }
        List<Student> list = studentService.filterStudents(name, startDate, endDate, className);
        request.setAttribute("list", list);
        request.setAttribute("listClass", studentService.findAllClass());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("list.jsp");
        requestDispatcher.forward(request, response);
    }
}


