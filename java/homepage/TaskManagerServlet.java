package homepage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/tasks/add", "/tasks/delete", "/tasks/toggle"})
public class TaskManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO = new TaskDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        try {
            if (path.endsWith("/toggle")) {
                handleToggle(request, response);
            } else if (path.endsWith("/add")) {
                handleAdd(request, response);
            }
        } catch (Exception e) {
            sendError(response, "Error processing request: " + e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            taskDAO.deleteTask(id);
            response.getWriter().write("{\"success\":true}");
        } catch (Exception e) {
            sendError(response, "Error deleting task: " + e.getMessage());
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        Task task = new Task();
        task.setName(request.getParameter("name"));
        task.setDescription(request.getParameter("description"));
        
        String dueDate = request.getParameter("dueDate");
        if (dueDate != null && !dueDate.isEmpty()) {
            task.setDueDate(LocalDateTime.parse(dueDate.replace(" ", "T")));
        }
        
        int id = taskDAO.addTask(task);
        response.getWriter().write(String.format("{\"success\":true, \"id\":%d}", id));
    }

    private void handleToggle(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Task task = taskDAO.getTaskById(id);
        
        if (task != null) {
            task.setStatus(!task.isStatus());
            taskDAO.updateTaskStatus(task);
            response.getWriter().write("{\"success\":true}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\":\"Task not found\"}");
        }
    }

    private String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(String.format("{\"error\":\"%s\"}", escapeJson(message)));
    }
}