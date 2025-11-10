<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<% if(request.getParameter("error") != null) { %>
<p style="color: red;">Invalid username or password!</p>
<% } %>

<form method="post" action="/login">
    <label>Username:</label><br>
    <input type="text" name="username" required><br><br>

    <label>Password:</label><br>
    <input type="password" name="password" required><br><br>

    <button type="submit">Login</button>
</form>

<br>
<a href="/register">Don't have account? Register</a>
</body>
</html>