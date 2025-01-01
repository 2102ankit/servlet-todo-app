<%@page import="utils.Person"%>
<%@page import="utils.Todo"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Your TODOs</title>
    <style>
    /* Reset default styles */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Arial', sans-serif;
}

body {
    background-color: #f5f7fa;
    color: #2c3e50;
    line-height: 1.6;
    padding: 2rem;
}

/* Header Styles */
h1 {
    color: #34495e;
    margin-bottom: 1.5rem;
    font-size: 2rem;
    font-weight: 600;
    border-bottom: 2px solid #e2e8f0;
    padding-bottom: 0.5rem;
}

/* Form Styles */
form {
    background: #ffffff;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 2rem;
    max-width: 600px;
}

input[type="text"],
input[type="email"],
input[type="password"],
textarea {
    width: 100%;
    padding: 0.75rem;
    margin: 0.5rem 0 1rem;
    border: 1px solid #cbd5e0;
    border-radius: 4px;
    font-size: 1rem;
    transition: border-color 0.3s ease;
}

input[type="text"]:focus,
input[type="email"]:focus,
input[type="password"]:focus,
textarea:focus {
    outline: none;
    border-color: #4a90e2;
    box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
}

textarea {
    min-height: 100px;
    resize: vertical;
}

/* Button Styles */
button {
    background-color: #2c5282;
    color: white;
    padding: 0.75rem 1.5rem;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #2b6cb0;
}

/* Link Styles */
a {
    color: #4a90e2;
    text-decoration: none;
    transition: color 0.3s ease;
}

a:hover {
    color: #2c5282;
    text-decoration: underline;
}

/* Error Message Styles */
p[style*="color: red"] {
    background-color: #fff5f5;
    color: #c53030 !important;
    padding: 0.75rem;
    border-radius: 4px;
    margin-bottom: 1rem;
    border-left: 4px solid #fc8181;
}

/* Todo Container Styles */
#todosContainer {
    display: grid;
    gap: 1rem;
    margin-top: 1.5rem;
}

#todosContainer > div {
    background: white;
    padding: 1.5rem;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    border-left: 4px solid #4a90e2;
}

#todosContainer p {
    margin-bottom: 0.5rem;
}

#todosContainer p:last-child {
    margin-bottom: 0;
    color: #718096;
    font-size: 0.9rem;
}

/* Navigation Links */
.nav-links {
    margin-top: 2rem;
    padding-top: 1rem;
    border-top: 1px solid #e2e8f0;
}

/* Responsive Design */
@media (max-width: 768px) {
    body {
        padding: 1rem;
    }
    
    form {
        padding: 1.5rem;
    }
    
    h1 {
        font-size: 1.75rem;
    }
    
    input[type="text"],
    input[type="email"],
    input[type="password"],
    textarea {
        padding: 0.5rem;
    }
}

/* Sign In/Up specific styles */
.auth-page {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 80vh;
}

.auth-page form {
    width: 100%;
    max-width: 400px;
}

.auth-page p {
    text-align: center;
    margin-top: 1rem;
}
    
    </style>

<%@ page import="java.util.*, java.util.stream.Collectors, utils.*"%>
<%
// Java logic: Retrieve and cast the 'todos' attribute from the request
List<Todo> todosList = (List<Todo>) request.getAttribute("todos");
if (todosList == null) {
	todosList = new ArrayList<>();
}
// Create the JSON string manually for the todos list
StringBuilder jsonBuilder = new StringBuilder("[");
for (int i = 0; i < todosList.size(); i++) {
	Todo todo = todosList.get(i);
	jsonBuilder.append("{").append("\"title\":\"").append(todo.getTitle()).append("\",").append("\"content\":\"")
	.append(todo.getContent()).append("\",").append("\"created\":\"").append(todo.getDateModified())
	.append("\"").append("}");
	if (i < todosList.size() - 1) {
		jsonBuilder.append(",");
	}
}
jsonBuilder.append("]");

// Pass the serialized JSON string to the JavaScript code
String todosJson = jsonBuilder.toString();
%>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Deserialize the 'todos' JSON string into a JavaScript object
    const todos = <%=todosJson%>;

    console.log(todos);  // Log the todos array to check its structure

    const container = document.getElementById('todosContainer');
    container.innerHTML = '';  // Clear existing todos
    
    if (Array.isArray(todos)) {
        todos.forEach(todo => {
            console.log(todo);  // Log individual todo object for inspection

            let div = document.createElement('div');
            
            // Ensure 'created' is a valid date string and format it
            const createdDate = new Date(todo.created);
            const formattedDate = isNaN(createdDate) ? 'Invalid date' : createdDate.toLocaleString();

            div.innerHTML = "<p>Title : "+ todo.title + "</p>" 
            + "<p>Content : " + todo.content + "</p>"
            + "<p>Date Modified : " + todo.created.slice(0,10) + "</p>";
            container.appendChild(div);
        });
    } else {
        container.innerHTML = '<p>No TODOs found.</p>';
    }
});

</script>

</head>
<body>
<h1>Welcome, <%= session.getAttribute("user") != null ? ((Person) session.getAttribute("user")).getName() : "Guest" %></h1>

    
	<h1>Add New TODOs</h1>
	<!-- Display error message if available -->
    <% String errorMessage = (String) request.getAttribute("error");
       if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <p style="color: red;"><%= errorMessage %></p>
    <% } %>
	<form method="POST" action="/Todo/todos">
		Title: <input type="text" name="title" required><br>
		Content:
		<textarea name="content" required></textarea>
		<br>
		<button type="submit">Add TODO</button>
	</form>

	<h1>Your TODOs</h1>
	<div id="todosContainer"></div>

	<a href='/Todo/logout'>Log Out</a>
</body>
</html>
