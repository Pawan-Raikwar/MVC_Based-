package in.sp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import in.sp.dbcon.DbConnection;

@WebServlet("/regForm")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        
        String myname = req.getParameter("name1");
        String myemail = req.getParameter("email1");
        String mypass = req.getParameter("pass1");
        String mycity = req.getParameter("city1");
        
        try {
            Connection con = DbConnection.getConnection();
            
            String inser_sql_query = "INSERT INTO register VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(inser_sql_query);
            ps.setString(1, myname);
            ps.setString(2, myemail);  // Fixed parameter index
            ps.setString(3, mypass);   // Fixed parameter index
            ps.setString(4, mycity);   // Fixed parameter index
            
            int count = ps.executeUpdate();  // Removed duplicate executeUpdate()
            
            if(count > 0) {
                out.println("<h3 style='color:green'>Registered Successfully</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                rd.include(req, resp);
            } else {
                out.println("<h3 style='color:red'>User not registered due to some error</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/register.html");
                rd.include(req, resp);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("/register.html");
            rd.include(req, resp);
        }
    }
}