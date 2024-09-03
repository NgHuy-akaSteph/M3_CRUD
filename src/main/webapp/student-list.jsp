<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Students Management Application</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

<div class="container">
    <h1>Quản lý học viên Codegym</h1>
    <hr>
    <a href="?action=create" class="btn btn-primary">Thêm học viên</a>
    <br><br>
    <table class="table table-bordered table-striped text-center align-middle">
        <thead>
            <tr>
                <th>STT</th>
                <th>Họ và tên</th>
                <th>Giới tính</th>
                <th>Email</th>
                <th>Điểm số</th>
                <th colspan="2">Tùy chọn</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach  var="student" items="${list}">
                <tr>
                    <td><c:out value="${student.id}"/></td>
                    <td><c:out value="${student.name}"/></td>
                    <td>
                        <c:if test="${student.gender==true}">
                            <c:out value="Nam"/>
                        </c:if>
                        <c:if test="${student.gender==false}">
                            <c:out value="Nữ"/>
                        </c:if>
                    </td>
                    <td><c:out value="${student.email}"/></td>
                    <td><c:out value="${student.point}"/></td>
                    <td>
                        <a href="?action=edit&id=${student.id}" class="btn btn-warning">Edit</a>
                        <a href="?action=delete&id=${student.id}" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
