package calendar;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DeleteEventServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String DB_URL = "jdbc:mysql://localhost:3306/Chronosdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "kads@17o9";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM events WHERE id = ?")) {
            
            stmt.setInt(1, Integer.parseInt(request.getParameter("id")));
            stmt.executeUpdate();
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect("index.jsp");
    }
}