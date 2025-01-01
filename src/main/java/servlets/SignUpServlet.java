package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.DataStore;
import utils.Person;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet{
	
	private final DataStore dataStore = DataStore.getInstance();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//check if user already exists
		if(dataStore.getUser(email)!=null) {
			request.setAttribute("error", "User with this email already exists! Please try again with a different email");
			request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
		}
		
		//new user
		Person newUser = new Person(name,email, password);
		dataStore.addUser(email, newUser);
		response.sendRedirect("/Todo/signin");
		
	}
}