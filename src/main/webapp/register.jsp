<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Register</h1>
<%
    String error = request.getParameter("error");
    if("password".equals(error)) {
%>
<p style="color: red;">Passwords don't match!</p>
<%
} else if("username".equals(error)) {
%>
<p style="color: red;">Username already exists!</p>
<%
} else if("email".equals(error)) {
%>
<p style="color: red;">Email already exists!</p>
<%
    }
%>

<form method="post" action="/register">
    <label>First Name:</label><br>
    <input type="text" name="firstName" required><br><br>

    <label>Last Name:</label><br>
    <input type="text" name="lastName" required><br><br>

    <label>Username:</label><br>
    <input type="text" name="username" required><br><br>

    <label>Email:</label><br>
    <input type="email" name="email" required><br><br>

    <label>Password:</label><br>
    <input type="password" name="password" required><br><br>

    <label>Re-Password:</label><br>
    <input type="password" name="repassword" required><br><br>

    <button type="submit">Register</button>
</form>

<br>
<a href="/login">Already have account? Login</a>
</body>
</html>