package homepage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/ChronosDB";
    private static final String USER = "root";
    private static final String PASS = "kads@17o9";

    // Get all tasks from database
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                tasks.add(extractTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks: " + e.getMessage());
            throw e;
        }
        
        return tasks;
    }

    // Get single task by ID
    public Task getTaskById(int id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractTaskFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching task by ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Add new task to database
    public int addTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (name, description, due_date, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setTaskParameters(stmt, task);
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Failed to get generated task ID");
            }
        } catch (SQLException e) {
            System.err.println("Error adding task: " + e.getMessage());
            throw e;
        }
    }

    // Update only task status (for toggle functionality)
    public void updateTaskStatus(Task task) throws SQLException {
        String sql = "UPDATE tasks SET status=? WHERE id=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, task.isStatus());
            stmt.setInt(2, task.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating task status: " + e.getMessage());
            throw e;
        }
    }

    // Delete task by ID
    public void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting task: " + e.getMessage());
            throw e;
        }
    }

    // Helper method to extract task from ResultSet
    private Task extractTaskFromResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setName(rs.getString("name"));
        task.setDescription(rs.getString("description"));
        
        Timestamp dueDate = rs.getTimestamp("due_date");
        if (dueDate != null) {
            task.setDueDate(dueDate.toLocalDateTime());
        }
        
        task.setStatus(rs.getBoolean("status"));
        return task;
    }

    // Helper method to set prepared statement parameters
    private void setTaskParameters(PreparedStatement stmt, Task task) throws SQLException {
        stmt.setString(1, task.getName());
        stmt.setString(2, task.getDescription());
        stmt.setTimestamp(3, task.getDueDate() != null ? 
            Timestamp.valueOf(task.getDueDate()) : null);
        stmt.setBoolean(4, task.isStatus());
    }
}