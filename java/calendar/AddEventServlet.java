package calendar;import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AddEventServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private final String DB_URL = "jdbc:mysql://localhost:3306/Chronosdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "kads@17o9";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO events (title, description, event_date) VALUES (?, ?, ?)")) {
            
            stmt.setString(1, request.getParameter("title"));
            stmt.setString(2, request.getParameter("description"));
            stmt.setDate(3, Date.valueOf(request.getParameter("event_date")));
            
            stmt.executeUpdate();
            out.print("success");
            
        } catch (SQLException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}