package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DataStore;
import utils.Person;
import utils.Todo;

@WebServlet("/todos")
public class TodoServlet extends HttpServlet {

	private final DataStore dataStore = DataStore.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("/Todo/signin"); // Redirect if user is not logged in
		} else {
			String responseType = request.getParameter("type");

			Person user = (Person) session.getAttribute("user");
			List<Todo> todos = dataStore.getTodos(user);

			if ("json".equals(responseType)) {
				// Set content type to JSON
				response.setContentType("application/json");

				// Create JSON array
				JSONArray jsonArray = new JSONArray();

				// Populate JSON array with todos
				for (Todo todo : todos) {
					JSONObject jsonTodo = new JSONObject();
					jsonTodo.put("title", todo.getTitle());
					jsonTodo.put("content", todo.getContent());
					jsonTodo.put("created", todo.getDateModified().toString()); // Assuming `created` is `java.util.Date`
					jsonArray.put(jsonTodo);
				}

				// Write JSON response
				response.getWriter().write(jsonArray.toString());
			} else {
				// Forward to JSP view
				request.setAttribute("todos", todos);
				request.getRequestDispatcher("/WEB-INF/views/todos.jsp").forward(request, response);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("/");
			return;
		}

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		// Trim leading and trailing whitespaces
		title = title != null ? title.trim() : null;
		content = content != null ? content.trim() : null;

		if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
			request.setAttribute("error", "Title and Content cannot be empty.");
			request.getRequestDispatcher("/WEB-INF/views/todos.jsp").forward(request, response);
			return;
		}

		Person user = (Person) session.getAttribute("user");
		Todo todo = new Todo(title, content);
		dataStore.addTodo(user, todo);

		// Redirect to GET to avoid duplicate submissions
	    response.sendRedirect("/Todo/todos");
	}
}
