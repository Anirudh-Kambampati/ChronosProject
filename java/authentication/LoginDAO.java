package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.sql.PreparedStatement;

public class LoginDAO {
	static final String URL="jdbc:mysql://localhost:3306/ChronosDB";
	static final String USER="root";
	static final String PASS="kads@17o9";
	
	Connection conn=null;
	PreparedStatement stmt=null;
	ResultSet rs=null;
	
	public UserBean validateUser(String usernamefnt, String passwordfnt) throws SQLException {
	    conn = DriverManager.getConnection(URL, USER, PASS);
	    
	    
	    String query = "SELECT * FROM users WHERE username=? AND password=?";
	    
	    stmt = conn.prepareStatement(query);
	    stmt.setString(1, usernamefnt);
	    stmt.setString(2, passwordfnt);
	    rs = stmt.executeQuery();
	    
	    //Initialize Userbean as null
	    UserBean u1 = null;

	    if (rs.next()) { // Check if there is a result
	        u1 = new UserBean();
	        u1.setUsername(rs.getString("username"));
	        u1.setPassword(rs.getString("password"));
	    }
	    
	    // Close resources to prevent memory leaks
	    rs.close();
	    stmt.close();
	    conn.close();
	    
	    return u1;
	}

}

