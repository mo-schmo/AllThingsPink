package DBProject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class reviewServlet
 */
@WebServlet("/reviewServlet")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		User user = Controller.login(email, password);
	
		session.setAttribute("userID", user.getUserID());
		String mitemID = request.getParameter("itemID");
		int itemID = Integer.parseInt(mitemID);
		String muserID = request.getParameter("userID");
		int userID = Integer.parseInt(muserID);
		String rating = request.getParameter("rating");
		String description = request.getParameter("description");
		Reviews review = new Reviews(0, userID, itemID, rating, null, description);
		try {
			Controller.insertReview(Controller.getConnection(), review);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("Review has been saved.", true);
		RequestDispatcher dispatch = request.getRequestDispatcher("Index.jsp");
		dispatch.forward(request, response);
		response.sendRedirect("Login.jsp");
	}

}
