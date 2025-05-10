package homepage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tasks/load")
public class TaskLoaderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO = new TaskDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            int totalTasks = tasks.size();
            int completedTasks = (int) tasks.stream().filter(Task::isStatus).count();
            
            response.setContentType("application/json");
            response.getWriter().write(
                String.format("{\"tasks\": %s, \"totalTasks\": %d, \"completedTasks\": %d}",
                    tasksToJson(tasks), totalTasks, completedTasks)
            );
        } catch (SQLException e) {
            sendError(response, "Error retrieving tasks");
        }
    }

    private String tasksToJson(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append(String.format(
                "{\"id\":%d,\"name\":\"%s\",\"description\":\"%s\",\"dueDate\":%s,\"status\":%b}",
                task.getId(),
                escapeJson(task.getName()),
                escapeJson(task.getDescription()),
                task.getDueDate() != null ? "\"" + task.getDueDate() + "\"" : "null",
                task.isStatus()
            ));
            if (i < tasks.size() - 1) sb.append(",");
        }
        return sb.append("]").toString();
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