package DBProject;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public RegistrationServlet() {
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		int age = Integer.parseInt(request.getParameter("age"));
		
		User newUser = Controller.register(firstName, lastName, email, gender, age, password);
		
		//Sending message to page and redirecting:https://stackoverflow.com/questions/14632252/servlet-redirect-to-same-page-with-error-message/14638621
		if(newUser != null) {
			//If successful display message and go to login.jsp
			request.setAttribute("successMessage", "Registration successful");
			request.setAttribute("errorMessage", null);
			RequestDispatcher dispatch = request.getRequestDispatcher("Login.jsp");
			dispatch.forward(request, response);
		}
		else {
			//If unsuccessufl display message and go to Registration.jsp
			request.setAttribute("successMessage", null);
			request.setAttribute("errorMessage", "Registration unsuccessful");
			RequestDispatcher dispatch = request.getRequestDispatcher("Registration.jsp");
			dispatch.forward(request, response);
		}
		
	}
}
