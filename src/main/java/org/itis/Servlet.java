package org.itis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class Servlet extends HttpServlet {
   private static final String DB_USERNAME = "postgres";
   private static final String DB_PASSWORD = "postgres";
   private static final String DB_URL = "jdbc:postgresql://localhost:5432/Accounts?useSSL=false";
   private static final long serialVersionUID = 1L;

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      HttpSession session = request.getSession();
      session.invalidate();
      response.sendRedirect("login.jsp");
   }
}
