package authentication;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String usernamefnt = request.getParameter("username");  
        String passwordfnt = request.getParameter("password");  

        System.out.println("Username Entered: " + usernamefnt);
        System.out.println("Password Entered: " + passwordfnt);

        LoginDAO dao = new LoginDAO();
        UserBean u1 = null;
        
        try {
            u1 = dao.validateUser(usernamefnt, passwordfnt);
            
            if (u1 != null && u1.getUsername() != null && u1.getPassword() != null) {
                response.sendRedirect("homepage.html");
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid Username or Password!');");
                out.println("window.location.href='login.html';");
                out.println("</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("<h3 style='color:red;'>Database Error! Please try again later.</h3>");
        }
    }
}
