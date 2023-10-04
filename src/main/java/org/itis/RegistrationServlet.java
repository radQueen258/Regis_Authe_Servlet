package org.itis;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Accounts";
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass");
        String umobile = request.getParameter("contact");
        RequestDispatcher dispatcher = null;
        Connection connection = null;



        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (uname,uemail,upwd,umobile)values (?,?,?,?) ");
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, uemail);
            preparedStatement.setString(3, upwd);
            preparedStatement.setString(4, umobile);

            int rowCount = preparedStatement.executeUpdate();
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request,response);

            if (rowCount > 0) {
                request.setAttribute("status", "Success");
            } else {
                request.setAttribute("status", "Failed");
            }


            //Code to print all name s in the database on my page as I log in
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users");

            List<Users> usersList = new ArrayList<>();

            while (result.next()) {

                Users user = new Users();

                user.setName(result.getString("uname"));
                user.setEmail(result.getString("uemail"));
                user.setPassword(result.getString("upwd"));
                user.setMobile(result.getString("umobile"));
                usersList.add(user);
            }
            request.setAttribute("usersList", usersList);
            dispatcher = request.getRequestDispatcher("indexTab.jsp");
            dispatcher.forward(request, response);
            //System.out.println();
            //end of code-------------------------------


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

}
