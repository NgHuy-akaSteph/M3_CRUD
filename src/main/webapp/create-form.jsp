<!DOCTYPE html>
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
    <form action="?action=create" method="post">
        <div class="form-group">
            <label for="name">Họ và tên:</label>
            <input type="text" class="form-control" id="name" name="name" value="${student.name}" required>
        </div>
        <div class="form-group">
            <label>Giới tính:
            <div class="form-check">
                <input type="radio" class="form-check-input" id="male" name="gender" value="male" ${student.gender ? 'checked' : ''} required>
                <label class="form-check-label" for="male">Nam</label>
            </div>
            <div class="form-check">
                <input type="radio" class="form-check-input" id="female" name="gender" value="female" ${!student.gender ? 'checked' : ''} required>
                <label class="form-check-label" for="female">Nữ</label>
            </div>
            </label>
        </div>
        <div class="form-group">
            <label for="dob">Ngày sinh:</label>
            <input type="date" class="form-control" id="dob" name="dob"  value="${student.dob}"required>
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
            <input type="number" class="form-control" id="point" name="point" step="0.01" value="${student.point}" required>
        </div>
        <br>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<%-- Modal for email validation error --%>
<div class="modal fade" id="emailErrorModal" tabindex="-1" aria-labelledby="emailErrorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="emailErrorModalLabel">Lỗi</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ${errorMessage}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>
    <% if (request.getAttribute("errorMessage") != null) { %>
    let emailErrorModal = new bootstrap.Modal(document.getElementById('emailErrorModal'));
    emailErrorModal.show();
    <% } %>
</script>
</body>
</html>

