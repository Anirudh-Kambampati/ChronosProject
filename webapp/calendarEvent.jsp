<%@ page import="java.sql.*, java.util.*" %>
<div style="max-width: 900px; margin: 20px auto; background: rgba(255, 255, 255, 0.07); padding: 20px; border-radius: 12px; backdrop-filter: blur(10px); box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1); border: 1px solid rgba(255, 255, 255, 0.06);">
    <h3 style="color: #f5f5f5; margin-bottom: 15px; font-size: 1.5rem;">Your Events</h3>
    <table style="width: 100%; border-collapse: collapse; color: #f5f5f5;">
        <tr>
            <th style="background: #007bff; padding: 12px; text-align: left;">Title</th>
            <th style="background: #007bff; padding: 12px; text-align: left;">Description</th>
            <th style="background: #007bff; padding: 12px; text-align: left;">Date</th>
            <th style="background: #007bff; padding: 12px; text-align: left;">Action</th>
        </tr>
        
        <%
            String jdbcURL = "jdbc:mysql://localhost:3306/Chronosdb";
            String jdbcUsername = "root";
            String jdbcPassword = "kads@17o9";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM events ORDER BY event_date");

                while(rs.next()) {
        %>
            <tr style="transition: all 0.3s ease;">
                <td style="padding: 12px; border-bottom: 1px solid rgba(255, 255, 255, 0.1);"><%= rs.getString("title") %></td>
                <td style="padding: 12px; border-bottom: 1px solid rgba(255, 255, 255, 0.1);"><%= rs.getString("description") %></td>
                <td style="padding: 12px; border-bottom: 1px solid rgba(255, 255, 255, 0.1);"><%= rs.getDate("event_date") %></td>
                <td style="padding: 12px; border-bottom: 1px solid rgba(255, 255, 255, 0.1);">
                    <a href="DeleteEventServlet?id=<%= rs.getInt("id") %>" 
                       style="color: #dc3545; text-decoration: none; transition: all 0.3s ease;"
                       onmouseover="this.style.color='#bd2130'; this.style.textDecoration='underline'"
                       onmouseout="this.style.color='#dc3545'; this.style.textDecoration='none'"
                       onclick="return confirm('Are you sure you want to delete this event?');">
                        Delete
                    </a>
                </td>
            </tr>
        <%
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch(Exception e) {
                out.println("<tr><td colspan='4' style='padding: 12px; color: #dc3545;'>No events found or error loading events.</td></tr>");
            }
        %>
    </table>
</div>

<style>
:root {
    --primary-clr: #64b6e5;
    --bg-gradient: linear-gradient(135deg, #051f30, #000000);
    --text-color: #f5f5f5;
}
</style>