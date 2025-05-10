// ==============================================
// COMMON UTILITIES
// ==============================================
function showAlert(message, type = "info") {
    const container = document.getElementById('alerts-container') || 
                     document.body.appendChild(document.createElement('div'));
    container.id = 'alerts-container';

    const alert = document.createElement("div");
    alert.className = `chronos-nano-alert chronos-nano-${type}`;
    
    // Create the micro icon
    const icon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    icon.setAttribute("viewBox", "0 0 24 24");
    icon.className = "chronos-nano-icon";

    // Assign different icons based on alert type
    switch (type) {
        case "success":
            icon.innerHTML = `<path fill="none" stroke="currentColor" stroke-width="1.5" d="M5 13l4 4L19 7"></path>`;
            break;
        case "info":
            icon.innerHTML = `
                <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="1.5" fill="none"></circle>
                <path fill="none" stroke="currentColor" stroke-width="1.5" d="M12 8v4m0 4h.01"></path>
            `;
            break;
        case "warning":
            icon.innerHTML = `
                <path fill="none" stroke="currentColor" stroke-width="1.5" d="M12 3l9 16H3z"></path>
                <path fill="none" stroke="currentColor" stroke-width="1.5" d="M12 9v4m0 4h.01"></path>
            `;
            break;
        case "error":
            icon.innerHTML = `<path fill="none" stroke="currentColor" stroke-width="1.5" d="M6 6l12 12m0-12L6 18"></path>`;
            break;
    }
    
    // Nano message
    const msg = document.createElement("span");
    msg.textContent = message;
    
    alert.append(icon, msg);
    container.prepend(alert);

    // Ultra-short display time
    setTimeout(() => {
        alert.style.opacity = '0';
        setTimeout(() => alert.remove(), 200);
    }, 3000);

    alert.addEventListener('click', () => alert.remove());
}

function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return `⏰ ${date.toLocaleString()}`;
}

// ==============================================
// TODO WIDGET
// ==============================================

document.addEventListener("DOMContentLoaded", () => {
    initTodoWidget();
    initTimerWidget();
});

function initTodoWidget() {
    const openBtn = document.getElementById("modal-button");
    const modal = document.getElementById("modal");
    const taskForm = document.getElementById("task-form");
    const cancelBtn = document.getElementById("cancel-modal");
    const taskList = document.getElementById("task-list");
    const progressBar = document.getElementById("progress");
    const progressText = document.getElementById("numbers");
    const modalTitle = document.getElementById("modal-title");
    
    let tasks = [];

    loadTasks();
    setupTodoEventListeners();

    function loadTasks() {
        fetch('tasks/load')
            .then(response => {
                if (!response.ok) throw new Error('Network error');
                return response.json();
            })
            .then(data => {
                tasks = data.tasks || [];
                renderTasks();
                updateProgress(data.totalTasks || 0, data.completedTasks || 0);
            })
            .catch(error => {
                console.error('Error loading tasks:', error);
                showAlert('Error loading tasks', 'error');
            });
    }

    function saveTask(taskData) {
        const formData = new URLSearchParams();
        formData.append('name', taskData.name);
        formData.append('description', taskData.description);
        if (taskData.dueDate) formData.append('dueDate', taskData.dueDate);

        fetch('tasks/add', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: formData
        })
        .then(response => {
            if (!response.ok) throw new Error('Save failed');
            return response.json();
        })
        .then(data => {
            if (data.success) {
                loadTasks();
                showAlert('Task added successfully', 'success');
            } else {
                throw new Error(data.error || 'Operation failed');
            }
        })
        .catch(error => {
            console.error('Error saving task:', error);
            showAlert('Error saving task: ' + error.message, 'error');
        });
    }

    function toggleTaskComplete(taskId) {
        fetch('tasks/toggle', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `id=${taskId}`
        })
        .then(response => {
            if (!response.ok) throw new Error('Toggle failed');
            return response.json();
        })
        .then(data => {
            if (data.success) {
                loadTasks();
                showAlert('Task status updated!', 'success');
            } else {
                throw new Error(data.error || 'Operation failed');
            }
        })
        .catch(error => {
            console.error('Error toggling task:', error);
            showAlert('Error toggling task: ' + error.message, 'error');
        });
    }

    function deleteTask(taskId) {
        if (!confirm("Are you sure you want to delete this task?")) return;
        
        fetch(`tasks/delete?id=${taskId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) throw new Error('Delete failed');
            return response.json();
        })
        .then(data => {
            if (data.success) {
                loadTasks();
                showAlert('Task deleted successfully', 'success');
            } else {
                throw new Error(data.error || 'Operation failed');
            }
        })
        .catch(error => {
            console.error('Error deleting task:', error);
            showAlert('Error deleting task: ' + error.message, 'error');
        });
    }

    function renderTasks() {
        taskList.innerHTML = '';
        tasks.forEach(task => {
            const taskItem = document.createElement("li");
            taskItem.className = `taskItem ${task.status ? 'completed' : ''}`;
            taskItem.dataset.id = task.id;

            const taskDiv = document.createElement("div");
            taskDiv.className = "task";

            // Checkbox
            const checkboxContainer = document.createElement("div");
            checkboxContainer.className = "task-checkbox-container";
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.className = "task-checkbox";
            checkbox.checked = task.status;
            checkbox.addEventListener("change", () => toggleTaskComplete(task.id));
            checkboxContainer.appendChild(checkbox);
            taskDiv.appendChild(checkboxContainer);

            // Task details
            const detailsDiv = document.createElement("div");
            detailsDiv.className = "task-details";
            
            const nameDiv = document.createElement("div");
            nameDiv.className = "task-name";
            nameDiv.textContent = task.name;
            detailsDiv.appendChild(nameDiv);

            if (task.description) {
                const descDiv = document.createElement("div");
                descDiv.className = "task-desc";
                descDiv.textContent = task.description;
                detailsDiv.appendChild(descDiv);
            }

            if (task.dueDate) {
                const dateDiv = document.createElement("div");
                dateDiv.className = "task-date";
                dateDiv.textContent = formatDate(task.dueDate);
                detailsDiv.appendChild(dateDiv);
            }

            taskDiv.appendChild(detailsDiv);

            // Delete button
            const deleteBtn = document.createElement("button");
            deleteBtn.className = "delete-task";
            deleteBtn.title = "Delete Task";
            deleteBtn.innerHTML = '<i class="fa-solid fa-trash"></i>';
            deleteBtn.addEventListener("click", (e) => {
                e.stopPropagation();
                deleteTask(task.id);
            });
            taskDiv.appendChild(deleteBtn);

            taskItem.appendChild(taskDiv);
            taskList.appendChild(taskItem);
        });
    }

    function updateProgress(totalTasks, completedTasks) {
        const progressPercent = totalTasks > 0 ? (completedTasks / totalTasks) * 100 : 0;
        progressBar.style.width = `${progressPercent}%`;
        progressText.textContent = `${completedTasks}/${totalTasks}`;
    }

    function handleFormSubmit(e) {
        e.preventDefault();
        
        const taskName = document.getElementById("task-name").value.trim();
        const description = document.getElementById("description").value.trim();
        const dueDate = document.getElementById("due-date").value;

        if (!taskName) {
            showAlert("Please enter a task name!", "error");
            return;
        }

        saveTask({
            name: taskName,
            description,
            dueDate: dueDate ? dueDate.replace(' ', 'T') : null
        });
        closeModal();
    }

    function closeModal() {
        modal.classList.remove("open");
        taskForm.reset();
    }

    function setupTodoEventListeners() {
        openBtn.addEventListener("click", () => {
            modalTitle.textContent = "Add New Task";
            taskForm.reset();
            modal.classList.add("open");
        });

        cancelBtn.addEventListener("click", closeModal);
        taskForm.addEventListener("submit", handleFormSubmit);

        modal.addEventListener("click", (e) => {
            if (e.target === modal) {
                closeModal();
            }
        });

        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape" && modal.classList.contains("open")) {
                closeModal();
            }
        });
    }
}

// ==============================================
// TIMER WIDGET (UNCHANGED)
// ==============================================

function initTimerWidget() {
    const timerDisplay = document.getElementById("timerDisplay");
    const breakDisplay = document.getElementById("breakDisplay");
    const startTimerBtn = document.getElementById("startTimer");
    const pauseTimerBtn = document.getElementById("pauseTimer");
    const resetTimerBtn = document.getElementById("resetTimer");
    const startBreak5Btn = document.getElementById("startBreak5");
    const startBreak10Btn = document.getElementById("startBreak10");
    const pauseBreakBtn = document.getElementById("pauseBreak");
    const resetBreakBtn = document.getElementById("resetBreak");
    const timerStatus = document.getElementById("timerStatus");
    const breakStatus = document.getElementById("breakStatus");
    const timerProgressBar = document.getElementById("timerProgressBar");
    const breakProgressBar = document.getElementById("breakProgressBar");

    let timerInterval;
    let breakInterval;
    let timerSeconds = 25 * 60;
    let breakSeconds = 5 * 60;
    let isTimerRunning = false;
    let isBreakRunning = false;
    const originalTimerSeconds = 25 * 60;
    const originalBreakSeconds = 5 * 60;

    updateTimerDisplay();
    updateBreakDisplay();
    setupTimerEventListeners();

    function startTimer() {
        if (isTimerRunning) return;
        
        if (isBreakRunning) {
            pauseBreak();
        }
        
        isTimerRunning = true;
        timerStatus.textContent = "Focusing...";
        showAlert("Timer started! Let's focus!", "success");
        
        timerInterval = setInterval(() => {
            timerSeconds--;
            updateTimerDisplay();
            updateTimerProgress();
            
            if (timerSeconds <= 0) {
                clearInterval(timerInterval);
                isTimerRunning = false;
                timerStatus.textContent = "Focus session completed!";
                showAlert("Focus session completed! Time for a break.", "success");
                startBreak(5 * 60);
            }
        }, 1000);
    }

    function pauseTimer() {
        clearInterval(timerInterval);
        isTimerRunning = false;
        timerStatus.textContent = "Paused";
        showAlert("Timer paused", "info");
    }

    function resetTimer() {
        clearInterval(timerInterval);
        isTimerRunning = false;
        timerSeconds = originalTimerSeconds;
        updateTimerDisplay();
        updateTimerProgress();
        timerStatus.textContent = "Ready to start";
        showAlert("Timer reset", "info");
    }

    function startBreak(seconds) {
        if (isBreakRunning) return;
        
        if (isTimerRunning) {
            pauseTimer();
        }
        
        breakSeconds = seconds;
        isBreakRunning = true;
        breakStatus.textContent = "On break...";
        showAlert("Break started! Enjoy your time!", "success");
        
        breakInterval = setInterval(() => {
            breakSeconds--;
            updateBreakDisplay();
            updateBreakProgress();
            
            if (breakSeconds <= 0) {
                clearInterval(breakInterval);
                isBreakRunning = false;
                breakStatus.textContent = "Break completed!";
                showAlert("Break completed! Ready to focus again.", "success");
            }
        }, 1000);
    }

    function pauseBreak() {
        clearInterval(breakInterval);
        isBreakRunning = false;
        breakStatus.textContent = "Break paused";
        showAlert("Break paused", "info");
    }

    function resetBreak() {
        clearInterval(breakInterval);
        isBreakRunning = false;
        breakSeconds = originalBreakSeconds;
        updateBreakDisplay();
        updateBreakProgress();
        breakStatus.textContent = "Ready";
        showAlert("Break reset", "info");
    }

    function updateTimerDisplay() {
        const minutes = Math.floor(timerSeconds / 60);
        const seconds = timerSeconds % 60;
        timerDisplay.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    }

    function updateBreakDisplay() {
        const minutes = Math.floor(breakSeconds / 60);
        const seconds = breakSeconds % 60;
        breakDisplay.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    }

    function updateTimerProgress() {
        const progressPercent = (timerSeconds / originalTimerSeconds) * 100;
        timerProgressBar.style.width = `${progressPercent}%`;
    }

    function updateBreakProgress() {
        const progressPercent = (breakSeconds / originalBreakSeconds) * 100;
        breakProgressBar.style.width = `${progressPercent}%`;
    }

    function setupTimerEventListeners() {
        startTimerBtn.addEventListener("click", startTimer);
        pauseTimerBtn.addEventListener("click", pauseTimer);
        resetTimerBtn.addEventListener("click", resetTimer);
        startBreak5Btn.addEventListener("click", () => startBreak(5 * 60));
        startBreak10Btn.addEventListener("click", () => startBreak(10 * 60));
        pauseBreakBtn.addEventListener("click", pauseBreak);
        resetBreakBtn.addEventListener("click", resetBreak);
    }
}