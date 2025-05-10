<!DOCTYPE html>
<html>
<head>
  <title>Chronos Calendar</title>
  <link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
  <style>
    :root {
      --primary-clr: #64b6e5;
      --bg-gradient: linear-gradient(135deg, #051f30, #000000);
      --text-color: #f5f5f5;
      --text-muted: rgba(255, 255, 255, 0.7);
      --bg-overlay: rgba(255, 255, 255, 0.07);
    }
    
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: 'Inter', 'Segoe UI', sans-serif;
    }
    
    body {
      min-height: 100vh;
      background: var(--bg-gradient);
      color: var(--text-color);
      display: flex;
      padding-bottom: 30px;
    }
    
    ::-webkit-scrollbar {
      width: 4px;
    }
    ::-webkit-scrollbar-track {
      background: rgba(255, 255, 255, 0.1);
    }
    ::-webkit-scrollbar-thumb {
      background: var(--primary-clr);
      border-radius: 40px;
    }
    
    .fc {
      background: rgba(255, 255, 255, 0.07);
      border-radius: 10px;
      padding: 20px;
      backdrop-filter: blur(10px);
      box-shadow: 0 0 25px rgba(0, 0, 0, 0.15);
      border: 1px solid rgba(255, 255, 255, 0.06);
    }
    
    .fc-header-toolbar {
      color: var(--text-color);
    }
    
    .fc-button {
      background: rgba(255, 255, 255, 0.1) !important;
      border: 1px solid rgba(255, 255, 255, 0.2) !important;
      color: var(--text-color) !important;
    }
    
    .fc-button:hover {
      background: rgba(100, 182, 229, 0.3) !important;
    }
    
    .fc-button-primary:not(:disabled).fc-button-active {
      background: var(--primary-clr) !important;
      border-color: var(--primary-clr) !important;
    }
    
    .fc-daygrid-day {
      background: rgba(255, 255, 255, 0.05);
      border: 1px solid rgba(255, 255, 255, 0.1);
    }
    
    .fc-daygrid-day:hover {
      background: rgba(100, 182, 229, 0.1);
    }
    
    .fc-day-today {
      background: rgba(100, 182, 229, 0.2) !important;
    }
    
    .fc-event {
      background: var(--primary-clr);
      border: none;
      padding: 2px 4px;
      border-radius: 4px;
    }
    
    .fc-event:hover {
      box-shadow: 0 0 10px rgba(100, 182, 229, 0.5);
    }
    
    .fc-toolbar-title {
      color: var(--text-color);
      font-weight: 500;
    }
    
    .fc-col-header-cell {
      color: var(--text-color);
      background: rgba(255, 255, 255, 0.1);
    }
    
    .fc-daygrid-day-number {
      color: var(--text-color);
    }
    
    .fc-daygrid-day.fc-day-today .fc-daygrid-day-number {
      color: white;
      font-weight: bold;
    }
  </style>
</head>
<body>
  <div style="position: fixed; top: 0; left: 0; width: 70px; height: 100%; transition: width 0.4s cubic-bezier(0.4, 0, 0.2, 1); scrollbar-width: none; overflow: hidden auto; background: rgba(255, 255, 255, 0.07); -ms-overflow-style: none; padding: 30px 10px 30px 0; backdrop-filter: blur(10px); box-shadow: 0 0 25px rgba(0, 0, 0, 0.15); border-right: 1px solid rgba(255, 255, 255, 0.06); z-index: 100;" 
       onmouseover="this.style.width='220px'" 
       onmouseout="this.style.width='70px'">
    <ul style="padding-top: 20px;">
      <li style="list-style: none; border-radius: 0 12px 12px 0; transition: all 0.3s ease; margin-bottom: 10px; padding-left: 15px; position: relative;">
        <a href="homepage.html" style="text-decoration: none; color: rgba(255, 255, 255, 0.85); display: flex; align-items: center; font-size: 14px; font-weight: 500; letter-spacing: 0.5px; padding: 12px 0;">
          <span class="material-symbols-outlined" style="display: flex; align-items: center; justify-content: center; padding: 10px; font-size: 20px; margin-right: 15px; border-radius: 10px; background: rgba(255, 255, 255, 0.08); transition: all 0.3s ease;">home</span>
          <span>Home</span>
        </a>
      </li>
      <li style="list-style: none; border-radius: 0 12px 12px 0; transition: all 0.3s ease; margin-bottom: 10px; padding-left: 15px; position: relative;">
        <a href="index.jsp" style="text-decoration: none; color: rgba(255, 255, 255, 0.85); display: flex; align-items: center; font-size: 14px; font-weight: 500; letter-spacing: 0.5px; padding: 12px 0;">
          <span class="material-symbols-outlined" style="display: flex; align-items: center; justify-content: center; padding: 10px; font-size: 20px; margin-right: 15px; border-radius: 10px; background: rgba(255, 255, 255, 0.08); transition: all 0.3s ease;">dashboard</span>
          <span>Calendar</span>
        </a>
      </li>
      <li style="list-style: none; border-radius: 0 12px 12px 0; transition: all 0.3s ease; margin-bottom: 10px; padding-left: 15px; position: relative;">
        <a href="notes.html" style="text-decoration: none; color: rgba(255, 255, 255, 0.85); display: flex; align-items: center; font-size: 14px; font-weight: 500; letter-spacing: 0.5px; padding: 12px 0;">
          <span class="material-symbols-outlined" style="display: flex; align-items: center; justify-content: center; padding: 10px; font-size: 20px; margin-right: 15px; border-radius: 10px; background: rgba(255, 255, 255, 0.08); transition: all 0.3s ease;">note_add</span>
          <span>Notes</span>
        </a>
      </li>
      <li style="list-style: none; border-radius: 0 12px 12px 0; transition: all 0.3s ease; margin-bottom: 10px; padding-left: 15px; position: relative;">
        <a href="#" style="text-decoration: none; color: rgba(255, 255, 255, 0.85); display: flex; align-items: center; font-size: 14px; font-weight: 500; letter-spacing: 0.5px; padding: 12px 0;">
          <span class="material-symbols-outlined" style="display: flex; align-items: center; justify-content: center; padding: 10px; font-size: 20px; margin-right: 15px; border-radius: 10px; background: rgba(255, 255, 255, 0.08); transition: all 0.3s ease;">settings</span>
          <span>Settings</span>
        </a>
      </li>
      <li style="list-style: none; border-radius: 0 12px 12px 0; transition: all 0.3s ease; margin-bottom: 10px; padding-left: 15px; position: relative;">
        <a href="#" style="text-decoration: none; color: rgba(255, 255, 255, 0.85); display: flex; align-items: center; font-size: 14px; font-weight: 500; letter-spacing: 0.5px; padding: 12px 0;">
          <span class="material-symbols-outlined" style="display: flex; align-items: center; justify-content: center; padding: 10px; font-size: 20px; margin-right: 15px; border-radius: 10px; background: rgba(255, 255, 255, 0.08); transition: all 0.3s ease;">person</span>
          <span>Account</span>
        </a>
      </li>
      <li style="list-style: none; border-radius: 0 12px 12px 0; transition: all 0.3s ease; margin-bottom: 10px; padding-left: 15px; position: relative;">
        <a href="#" style="text-decoration: none; color: rgba(255, 255, 255, 0.85); display: flex; align-items: center; font-size: 14px; font-weight: 500; letter-spacing: 0.5px; padding: 12px 0;">
          <span class="material-symbols-outlined" style="display: flex; align-items: center; justify-content: center; padding: 10px; font-size: 20px; margin-right: 15px; border-radius: 10px; background: rgba(255, 255, 255, 0.08); transition: all 0.3s ease;">logout</span>
          <span>Logout</span>
        </a>
      </li>
    </ul>
  </div>

  <div style="margin-top: 0px; margin-left: 80px; margin-right: 15px; width: calc(100% - 70px); padding: 5px; transition: all 0.4s ease;">
    <div style="position: relative; width: 1200px; min-height: 850px; margin: 10px; padding: 20px; display: flex; border-radius: 10px; background: rgba(255, 255, 255, 0.07); backdrop-filter: blur(10px); box-shadow: 0 0 25px rgba(0, 0, 0, 0.15); border: 1px solid rgba(255, 255, 255, 0.06);">
      <div style="width: 100%;">
        <h2 style="text-align: center; margin-bottom: 20px; color: var(--text-color);">My Calendar</h2>
        <div id="calendar"></div>
        
        <jsp:include page="calendarEvent.jsp" />
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const calendarEl = document.getElementById('calendar');
      
      const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        selectable: true,
        events: '/LoadEventsServlet',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        
        dateClick: function(info) {
          const title = prompt("Enter event title:");
          const description = prompt("Optional: Enter description:");
          
          if (title) {
            fetch('AddEventServlet', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              },
              body: new URLSearchParams({
                title: title,
                description: description || '',
                event_date: info.dateStr
              })
            })
            .then(response => {
              if (!response.ok) throw new Error("Failed to add event");
              return response.text();
            })
            .then(data => {
              alert("Event added successfully!");
              calendar.refetchEvents();
            })
            .catch(err => {
              console.error(err);
              alert("Failed to add event.");
            });
          }
        }
      });
      
      calendar.render();
    });
  </script>
</body>
</html>