package com.p;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Signup")
public class Signup extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/signup";
    String dbUser = "root";
    String dbPass = "nk3125";
    String query = "INSERT INTO signup VALUES (?, ?, ?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String fn = req.getParameter("fn");
        String em = req.getParameter("em");
        String pw = req.getParameter("pw");  // user's password

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(url, dbUser, dbPass);
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, fn);
            pstmt.setString(2, em);
            pstmt.setString(3, pw);  // store user password (ideally hash it)

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                // Signup success – redirect to login page
                resp.sendRedirect("login.html");
            } else {
                out.println("<h3 style='color:red'>Signup failed. Please try again.</h3>");
                out.println("<a href='signup.html'>Go back to Signup</a>");
            }

            pstmt.close();
            c.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Database error: " + e.getMessage() + "</h3>");
            out.println("<a href='signup.html'>Go back to Signup</a>");
        }
    }
}