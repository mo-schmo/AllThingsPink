package DBProject;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		User user = UserDAO.login(email, password);
		
		//========================================================================================
		//
		//https://www.youtube.com/watch?v=4GfAhuKNCdM - video for validating users using sessions
		//
		//=========================================================================================
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userID", user.getUserID());
			session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
			RequestDispatcher dispatch = request.getRequestDispatcher("Index.jsp");
			dispatch.forward(request, response);
		}
		else {
			request.setAttribute("LoginFailed", "Login failed.");
			RequestDispatcher dispatch = request.getRequestDispatcher("Login.jsp");
			dispatch.forward(request, response);
//			response.sendRedirect("Login.jsp");
		}
	}
}
