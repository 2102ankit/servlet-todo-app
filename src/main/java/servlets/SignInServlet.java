package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.DataStore;
import utils.Person;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

	private final DataStore dataStore = DataStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Person user = dataStore.getUser(email);
        if (user != null && user.verifyPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("/Todo/todos");  // Redirect to todo page after successful login
        }else if(user!=null) {
        	request.setAttribute("error", "Invalid credentials! Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
        }
        else {
            request.setAttribute("error", "User Not Signed Up! Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
        }
    }
}
