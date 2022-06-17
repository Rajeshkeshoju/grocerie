package com.grocerie.auth;

import com.grocerie.include.DatabaseConnection;
import com.grocerie.include.Hash;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        response.getWriter().println("Hello login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            Connection conn = DatabaseConnection.getConnection();

            if(conn == null){
                System.out.println("Connection not established");
                return;
            }

            String username = request.getParameter("Username");
            String password  = request.getParameter("Password");

            password = Hash.hashPassword(password);

            String query = "select * from users where Username='" + username + "' and Password='"+ password + "'";
            PreparedStatement p = conn.prepareStatement(query);

            p.setMaxRows(1);
            ResultSet rs = p.executeQuery();

            if(!rs.next()) {
                response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
