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
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label>Giới tính: </label>
            <div class="form-check">
                <input type="radio" class="form-check-input" id="male" name="gender" value="male" required>
                <label class="form-check-label" for="male">Nam</label>
            </div>
            <div class="form-check">
                <input type="radio" class="form-check-input" id="female" name="gender" value="female" required>
                <label class="form-check-label" for="female">Nữ</label>
            </div>
        </div>
        <div class="form-group">
            <label for="email">Email: </label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="point">Điểm số:</label>
            <input type="number" class="form-control" id="point" name="point" step="0.01" required>
        </div>
        <br>
        <button type="submit" class="btn btn-primary btn-lg">Submit</button>
    </form>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
