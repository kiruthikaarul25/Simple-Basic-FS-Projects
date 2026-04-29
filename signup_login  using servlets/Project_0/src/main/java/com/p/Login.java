
package com.p;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {

	String url = "jdbc:mysql://localhost:3306/signup";
	String dbUser = "root";
	String dbPass = "nk3125";
	String query = "SELECT * FROM signup WHERE email = ? AND pass = ?";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String em = req.getParameter("em");
		String pw = req.getParameter("pw");

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection(url, dbUser, dbPass);
			PreparedStatement pstmt = c.prepareStatement(query);
			pstmt.setString(1, em);
			pstmt.setString(2, pw); // Note: In production, use hashed passwords

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				// Login success – redirect to welcome page or dashboard
				resp.sendRedirect("welome.html");
			} else {
				// Login failed – show error message
				out.println("Invalid");
				out.println("<a href='login.html'>Go back to Login</a>");
			}

			rs.close();
			pstmt.close();
			c.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			out.println("<h3 style='color:red'>Database error: " + e.getMessage() + "</h3>");
		}
	}
}