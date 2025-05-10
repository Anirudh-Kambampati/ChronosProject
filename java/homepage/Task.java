package homepage;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private boolean status;

    // Constructors
    public Task() {}

    public Task(String name, String description, LocalDateTime dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public int getId() { 
    	return id; 
    }
    
    public void setId(int id) { 
    	this.id = id; 
    }
    
    public String getName() { 
    	return name; 
    }
    
    public void setName(String name) { 
    	this.name = name; 
    }
    
    public String getDescription() { 
    	return description; 
    }
    
    public void setDescription(String description) { 
    	this.description = description;
    }
    
    public LocalDateTime getDueDate() { 
    	return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) { 
    	this.dueDate = dueDate; 
    }
    
    public boolean isStatus() { 
    	return status; 
    }
    
    public void setStatus(boolean status) { 
    	this.status = status; 
    }
    
    
}