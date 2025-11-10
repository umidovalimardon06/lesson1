<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="uz.umidovalimardon.lesson1.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if(user == null) {
        response.sendRedirect("/login");
        return;
    }
%>

<h1>Welcome <%= user.getFirstName() %>!</h1>

<h2>User Information:</h2>
<p><strong>First Name:</strong> <%= user.getFirstName() %></p>
<p><strong>Last Name:</strong> <%= user.getLastName() %></p>
<p><strong>Username:</strong> <%= user.getUsername() %></p>
<p><strong>Email:</strong> <%= user.getEmail() %></p>

<br>
<a href="/calc">Go to Calculator</a>
</body>
</html>