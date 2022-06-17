package com.grocerie.auth;

import com.grocerie.include.DatabaseConnection;
import com.grocerie.include.Hash;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.UUID;


@WebServlet(name = "Signup", value = "/signup")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("Create account");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            Connection conn = DatabaseConnection.getConnection();

            if(conn!=null) {

                String uniqueUserID = UUID.randomUUID().toString();
                String username = request.getParameter("Username");
                String password = request.getParameter("Password");
                String email = request.getParameter("Email");
                String phone = request.getParameter("Phone");

                password = Hash.hashPassword(password);

                String query = "insert into users(UserID, Username,Password,Email,Phone) values (?, ?, ?, ?, ?)";
                PreparedStatement p = conn.prepareStatement(query);

                p.setString(1, uniqueUserID);
                p.setString(2,username);
                p.setString(3,password);
                p.setString(4,email);
                p.setString(5,phone);

                p.execute();

                response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
