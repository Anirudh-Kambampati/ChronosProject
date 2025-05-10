package authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignupServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SignupDAO dao = new SignupDAO();
        boolean registered = false;
        try {
            registered = dao.registerUser(username, email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (registered) {
        	out.print("<h3 style='color:green;'>Signup Successful! <a href='login.html'>Login</a></h3>");
        } else {
        	 out.print("<h3 style='color:red;'>Signup Failed! <a href='signup.html'>Try Again</a></h3>");
        }

    }
}
