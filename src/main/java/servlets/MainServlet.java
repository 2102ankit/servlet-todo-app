package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/")
public class MainServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		//get user session if already signed up
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user")==null) {
			request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
		}else {
			response.sendRedirect("/Todo/todos");
		}
	}
}