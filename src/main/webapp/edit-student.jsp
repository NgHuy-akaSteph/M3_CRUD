<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thông tin của học viên mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container mt-5">
    <h1>Thông tin của học viên mới</h1>
    <form action="?action=edit" method="post">
        <div class="form-group">
            <input type="hidden" id="id" name="id" value="${student.id}">
        </div>
        <div class="form-group">
            <label for="name">Họ và tên:</label>
            <input type="text" class="form-control" id="name" name="name" value="${student.name}" required>
        </div>
        <div class="form-group">
            <label>Giới tính: </label>
            <div class="form-check">
                <input type="radio" class="form-check-input" id="male" name="gender" value="male" ${student.gender ? 'checked' : ''} required>
                <label class="form-check-label" for="male">Nam</label>
            </div>
            <div class="form-check">
                <input type="radio" class="form-check-input" id="female" name="gender" value="female" ${!student.gender ? 'checked' : ''} required>
                <label class="form-check-label" for="female">Nữ</label>
            </div>
        </div>
        <div class="form-group">
            <label for="classId">Lớp học</label>
            <select class="form-select" name="classId" id="classId" required>
                <c:forEach var="cgclass" items="${list}">
                    <option value="${cgclass.id}" ${cgclass.id == student.cgClass.id ? 'selected' : ''}>${cgclass.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="email">Email: </label>
            <input type="email" class="form-control" id="email" name="email" value="${student.email}" required>
        </div>
        <div class="form-group">
            <label for="point">Điểm số:</label>
            <input type="number" class="form-control" id="point" name="point" value="${student.point}" step="0.01" required>
        </div>
        <br>
        <button type="submit" class="btn btn-primary btn-lg">Save</button>
    </form>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
