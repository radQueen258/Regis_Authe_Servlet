<%@ page import="org.itis.Users" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("name") == null) {
        response.sendRedirect("login.jsp");
    }
%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Database Table</title>
</head>
<body>
<table id="database">
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Phone Number</th>
    </tr>
    <%
        List<Users> usersList = (List<Users>) request.getAttribute("usersList");
        if (usersList != null && !usersList.isEmpty()) {
            for (Users user : usersList) {
    %>
    <tr>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getPassword() %></td>
        <td><%= user.getMobile() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">No users found.</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>

