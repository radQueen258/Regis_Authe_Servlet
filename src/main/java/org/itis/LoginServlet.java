package org.itis;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet ("/login")
public class LoginServlet extends HttpServlet {


    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Accounts?useSSL=false";
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where uemail = ? and upwd = ?");
            preparedStatement.setString(1,uemail);
            preparedStatement.setString(2,upwd);

             ResultSet resultSet = preparedStatement.executeQuery();


//             //Code to print all name s in the database on my page as I log in
//            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery("select * from users");
//
//             while (resultSet.next()) {
//                // System.out.println(resultSet.getString("uname"));
//
//                 result.getString("uname");
//                 resultSet.getString("uemail");
//                 resultSet.getString("umobile");
//             }
//            System.out.println();
//             //end of code-------------------------------


            if (resultSet.next()) {
                session.setAttribute("name",resultSet.getString("uname"));
                dispatcher = request.getRequestDispatcher("indexTab.jsp");
            } else {
                request.setAttribute("status", "Failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request,response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
