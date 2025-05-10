package authentication;

import java.sql.*;

public class SignupDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/Chronosdb";
    private static final String USER = "root";
    private static final String PASS = "kads@17o9";

    public boolean registerUser(String username, String email, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close resources safely
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
