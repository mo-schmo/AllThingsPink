package DBProject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

// For File Upload
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Driver Name and DB URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";
	static final String PROJDB_URL = "jdbc:mysql://127.0.0.1:3306/projectdb?";
	static HttpSession session = null;

	// Create table queries
	private static String USERS = "CREATE TABLE Users ( userID int NOT NULL, firstName varchar(50), lastName varchar(50), email varchar(50), gender varchar(50), age int, root boolean NOT NULL, password varchar(255), PRIMARY KEY (userID));";
	private static String ITEM = "CREATE TABLE Item ( itemID int NOT NULL AUTO_INCREMENT, price decimal, name varchar(50), description varchar(255), category varchar(255), seller int NOT NULL, datePosted DATE, PRIMARY KEY (itemID), FOREIGN KEY(seller) REFERENCES Users(userID));";
	private static String REVIEW = "CREATE TABLE Reviews ( reviewID int NOT NULL AUTO_INCREMENT, userID int NOT NULL, itemID int NOT NULL, rating varchar(10), description varchar(500), dateOfReview DATE, PRIMARY KEY (reviewID),  FOREIGN KEY (userID) REFERENCES Users(userID),FOREIGN KEY (itemID) REFERENCES Item(itemID) );";
	private static String FAVE_ITEMS = "CREATE TABLE FavoriteItems(itemID int NOT NULL, userID int NOT NULL, itemName varchar(50), PRIMARY KEY(itemID,userID), FOREIGN KEY(itemID) REFERENCES Item(itemID), FOREIGN KEY(userID) REFERENCES Users(userID))"; 
	private static String FAVE_USERS = "CREATE TABLE FavoriteUsers(adderID int NOT NULL, addedID int NOT NULL, PRIMARY KEY(adderID,addedID), FOREIGN KEY(adderID) REFERENCES Users(userID),FOREIGN KEY(addedID) REFERENCES Users(userID))";
	// Triggers queries
	
	/*
	 * private static String noMoreThanFiveReviews =
	 * "DELIMITER // CREATE TRIGGER NoMoreThanFiveReviews BEFORE INSERT ON Reviews "
	 * + "FOR EACH ROW BEGIN IF (5 = (SELECT count(*) from Reviews R where" +
	 * " R.userID=New.userID AND DATE(dateOfReview)=NEW.dateOfReview)) THEN SIGNAL SQLSTATE '45000'; "
	 * + "END IF; END; // DELIMITER ;";
	 */
	
	  private static String noMoreThanFiveReviews =
	  "CREATE TRIGGER NoMoreThanFiveReviews BEFORE INSERT ON Reviews " +
	  "FOR EACH ROW BEGIN IF (5 = (SELECT count(*) from Reviews R where" +
	  " R.userID=New.userID AND DATE(dateOfReview)=NEW.dateOfReview)) THEN SIGNAL SQLSTATE '45000'; "
	  + "END IF; END;";
	 
	/*
	 * private static String noMoreThanFivePosted =
	 * "DELIMITER // CREATE TRIGGER NoMoreThanFivePosted BEFORE INSERT ON Posted " +
	 * "FOR EACH ROW BEGIN IF (5 = (SELECT count(*) from Posted P where" +
	 * " P.userID=New.userID AND DATE(datePosted)=NEW.datePosted)) THEN SIGNAL SQLSTATE '45000'; "
	 * + "END IF; END; // DELIMITER ;";
	 */
	
	  private static String noMoreThanFivePosted =
	  "CREATE TRIGGER NoMoreThanFivePosted BEFORE INSERT ON Item " +
	  "FOR EACH ROW BEGIN IF (5 = (SELECT count(*) from Item I where" +
	  " I.seller=New.seller AND DATE(datePosted)=NEW.datePosted)) THEN SIGNAL SQLSTATE '45000'; "
	  + "END IF; END;";
	 
	 

	// Insert into tables queries
	private static String INSERT_USER = "INSERT INTO Users (userID, firstName, lastName, email, gender, age, root, password) VALUES(?,?,?,?,?,?,?,?);";
	private static String INSERT_ITEM = "INSERT INTO Item (itemID, price, name, description, category, seller, datePosted) VALUES(?,?,?,?,?,?,?);";
	private static String INSERT_REVIEWS = "INSERT INTO Reviews (reviewID, userID, itemID, rating, description, dateOfReview) VALUES(?,?,?,?,?,?);";
	private static String INSERT_FAVORITE_ITEM = "INSERT INTO FavoriteItems(itemID,userID,itemName) VALUES(?,?,?);";
	
	
	// Select from Users table query, for login()
	private static String LOGIN_QUERY = "SELECT userID,firstName,lastName,gender,age,root FROM Users WHERE email=lower(?) and password=?";

	// Database credentials
	static final String USER = "john";
	static final String PASS = "pass1234";

	public Controller() {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action"); 
		
		switch(action) {
			case "addItem":{
				addUserItem(request, response);
				break;
			}
			case "addFave":{
				addItemtoFave(request,response);
				break;
			}
			case "removeFave":{
				removeItemFromFave(request,response);
				break;
			}
			case "leaveReview":{
				try {
					System.out.println("Calling leaveReview function...");
					leaveReview(request,response);
				} 
				
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute("reviewTrigger", true);
				}
				finally {
					System.out.println("Redirecting to Index.jsp...");
					response.sendRedirect("Index.jsp");
				}
				break;
			}
			case "sortByPrice":{
				//List table by most expensive
				String sortBy = request.getParameter("sortBy");
				List<Item> itemList = null;
				try {
					itemList = Controller.listAllItems(sortBy);
					session.setAttribute("itemList", itemList);
				/* response.sendRedirect("Index.jsp"); */
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					response.sendRedirect("Index.jsp"); 
				/* request.getRequestDispatcher("Index.jsp").forward(request,response); */
				}
				break;
			}
			case "users":{
				List<User> userList = null;
				try {
					userList = Controller.listAllUsers();
					session.setAttribute("userList", userList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					response.sendRedirect("users.jsp"); 
				}
				break;
			}
			case "addFaveUser":{
				addFaveUser(request,response);
				break;
			}
			case "removeFaveUser":{
				removeFaveUser(request,response);
				break;
			}
			case "sortByTwoPosted":{
				// For Task 3.2
				List<User> userList = null;
				String category1 = request.getParameter("category1");
				String category2 = request.getParameter("category2");
				try {
					userList = Controller.sortByTwoPosted(category1, category2);
					session.setAttribute("userList", userList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					response.sendRedirect("users.jsp"); 
				}
				break;
			}
			case "findFavoritedUsers":{
				//For Task 3.5
				List<User> userList = null;
				String userX = request.getParameter("userX");
				String userY = request.getParameter("userY");
				try {
					userList = Controller.findFavoritedUsers(userX, userY);
					session.setAttribute("userList", userList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					response.sendRedirect("users.jsp"); 
				}
				break;
			}
			case "logout":{
				try {
					logoutUser(request,response);
					break;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
		}
		
	}

	private static List<User> findFavoritedUsers(String userX, String userY) throws SQLException{
		List<User> listUsers = new ArrayList<User>();
		Connection connect = null;
		PreparedStatement ps = null;
		int currentUser = (int) session.getAttribute("userID");
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			//Display all users except the current user that is logged in. 
			String query = "Select userID, firstName, lastName from users U,(select addedID from (select addedID, count(*) AS counter from ("
					+"select addedID from favoriteusers F, ("
							+"select userID from users where email=? or email=?"
		               +")AS A where F.adderID=A.userID"
					+") AS X group by addedID"
				+") AS Y where Y.counter = 2"
		    +") AS Z where U.userID=Z.addedID;";
			ps = connect.prepareStatement(query);
			ps.setString(1, userX);
			ps.setString(2, userY);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int userID = resultSet.getInt("userID");
				String fName = resultSet.getString("firstName");
				String lName = resultSet.getString("lastName");
				/* String imageURL = resultSet.getString("imageURL"); */

				User user = new User(userID, fName, lName);
				listUsers.add(user);
			}
			return listUsers;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		return listUsers;
	}

	private static List<User> sortByTwoPosted(String c1, String c2) throws SQLException {
		List<User> listUsers = new ArrayList<User>();
		Connection connect = null;
		PreparedStatement ps = null;
		int currentUser = (int) session.getAttribute("userID");
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			//Display all users except the current user that is logged in. 
			String query = "Select userID, firstName, lastName FROM Users U,(Select seller FROM (Select seller, category, count(*) AS counter, datePosted from item I group by I.seller,I.datePosted) AS I where (I.category=? AND I.counter >= 2) OR (I.category=? AND I.counter >= 2))AS S WHERE S.seller = U.userID";;
			
			ps = connect.prepareStatement(query);
			ps.setString(1, c1);
			ps.setString(2, c2);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int userID = resultSet.getInt("userID");
				String fName = resultSet.getString("firstName");
				String lName = resultSet.getString("lastName");
				/* String imageURL = resultSet.getString("imageURL"); */

				User user = new User(userID, fName, lName);
				listUsers.add(user);
			}
			return listUsers;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		return listUsers;
	}

	private void removeFaveUser(HttpServletRequest request, HttpServletResponse response) {
		Connection connect = null;
		Statement statement = null;
		int userID = (int) (session.getAttribute("userID"));
		int tempID = Integer.parseInt(request.getParameter("tempID"));
		
		  
		
		String sql = "DELETE FROM FavoriteUsers WHERE adderID = ? AND addedID = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, userID);	//user adding to the table, obtain from session info
			ps.setInt(2, tempID);	//user being added to the table, obtain using getParameter()
			ps.execute();
			ps.close();
			System.out.println("User deleted from favorites...");
			


		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
			try {
				response.sendRedirect("users.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void addFaveUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection connect = null;
		Statement statement = null;
		int userID = (int) (session.getAttribute("userID"));
		int tempID = Integer.parseInt(request.getParameter("tempID"));
		
		  
		
		String sql = "INSERT INTO FavoriteUsers(adderID,addedID) VALUES(?,?)";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, userID);	//user adding to the table, obtain from session info
			ps.setInt(2, tempID);	//user+ being added to the table, obtain using getParameter()
			ps.execute();
			ps.close();
			System.out.println("User inserted into favorites...");
			


		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
			try {
				response.sendRedirect("users.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void leaveReview(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement ps = null;
		Connection connection = getConnection();
		
		String description = request.getParameter("description");
		String rating = request.getParameter("rating");
		int itemID = Integer.parseInt(request.getParameter("itemID"));
		int userID = (int) session.getAttribute("userID");
		
		//Get current date and convert to sql 
		Calendar dt = Calendar.getInstance();
		java.sql.Date dateOfReview = new java.sql.Date(dt.getTime().getTime());
		
		Reviews review = new Reviews(userID,itemID,rating,description);
		
		String insertQuery = "INSERT INTO Reviews (userID,itemID,rating,description,dateOfReview) VALUES (?,?,?,?,?)";
		ps = connection.prepareStatement(insertQuery);
		ps.setInt(1, review.getUserID());
		ps.setInt(2, review.getItemID());
		ps.setString(3, review.getRating());
		ps.setString(4, review.getDescription());
		ps.setDate(5, dateOfReview);
		if(ps.executeUpdate() > 0) {
			System.out.println("Review added sucessfully...");
			session.setAttribute("reviewTrigger", false);
		}
		else {
			System.out.println("Review was not added...");
		}
		
	}

	private void removeItemFromFave(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection connect = null;
		Statement statement = null;
		int userID = (int) (session.getAttribute("userID"));
		int itemID = Integer.parseInt(request.getParameter("itemID"));
		
		  
		
		String getItemName = "SELECT name FROM Item WHERE itemID = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			PreparedStatement ps = connect.prepareStatement(getItemName);
			ps.setInt(1, itemID);
			ResultSet rs = ps.executeQuery();
			
			//Get name of item query
			String itemName = null;
			if(rs.next()) {
				itemName = rs.getString("name");
			}
			
			FavoriteItems faveItem = new FavoriteItems(itemID,userID,itemName);
			
			//Execute INSERT_FAVORITE_ITEM query
			ps = connect.prepareStatement("DELETE FROM FavoriteItems WHERE userID=? AND itemName=?");
			ps.setInt(1, faveItem.getUserID());
			ps.setString(2, faveItem.getItemName());
			ps.execute();
			ps.close();
			System.out.println("Item deleted from favorites:" + faveItem.getItemName());
			
			System.out.println(session.getId());
			/*
			 * List<Item> itemList = null; try { itemList =
			 * Controller.listAllItems("sortByCategory"); } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			  
			/*
			 * session.setAttribute("userID", request.getParameter("userID"));
			 * session.setAttribute("userName", request.getParameter("userName"));
			 * session.setAttribute("root", request.getParameter("root"));
			 * session.setAttribute("itemList", itemList);
			 */
			

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
			try {
				response.sendRedirect("Index.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		/* session = request.getSession(false); */
		if(session != null ) {
			session.invalidate();
			response.sendRedirect("Login.jsp");
		}
		else {
			response.sendRedirect("Login.jsp");
		}
	}

	private void addItemtoFave(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		Connection connect = null;
		Statement statement = null;
		int userID = (int) (session.getAttribute("userID"));
		int itemID = Integer.parseInt(request.getParameter("itemID"));
		
		  
		
		String getItemName = "SELECT name FROM Item WHERE itemID = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			PreparedStatement ps = connect.prepareStatement(getItemName);
			ps.setInt(1, itemID);
			ResultSet rs = ps.executeQuery();
			
			//Get name of item query
			String itemName = null;
			if(rs.next()) {
				itemName = rs.getString("name");
			}
			
			FavoriteItems faveItem = new FavoriteItems(itemID,userID,itemName);
			
			//Execute INSERT_FAVORITE_ITEM query
			ps = connect.prepareStatement(INSERT_FAVORITE_ITEM);
			ps.setInt(1, faveItem.getItemID());
			ps.setInt(2, faveItem.getUserID());
			ps.setString(3, faveItem.getItemName());
			ps.execute();
			ps.close();
			System.out.println("Item inserted into favorites...");
			
			System.out.println(session.getId());
			/*
			 * List<Item> itemList = null; try { itemList =
			 * Controller.listAllItems("sortByCategory"); } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			  
			/*
			 * session.setAttribute("userID", request.getParameter("userID"));
			 * session.setAttribute("userName", request.getParameter("userName"));
			 * session.setAttribute("root", request.getParameter("root"));
			 * session.setAttribute("itemList", itemList);
			 */
			

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
			try {
				response.sendRedirect("Index.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public static void addUserItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		Double price = Double.parseDouble(request.getParameter("price"));
		int seller = (int) session.getAttribute("userID");
		Connection connect = null;
		PreparedStatement statement = null;
		
		//Get current date for datePosted of Item
		Calendar dt = Calendar.getInstance();
		java.sql.Date datePosted = new java.sql.Date(dt.getTime().getTime());
		
		Item item = new Item(title, description, category, price, seller);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			String query = "INSERT INTO Item (price, name, description, category, seller, datePosted) VALUES(?,?,?,?,?,?)";
			
			 

			statement = connect.prepareStatement(query);
			statement.setDouble(1, item.getPrice());
			statement.setString(2, item.getName());
			statement.setString(3, item.getDescription());
			statement.setString(4, item.getCategory());
			statement.setInt(5, item.getSeller());
			statement.setDate(6, datePosted);
			statement.execute();
			System.out.println("Item added to database...");
			
			System.out.println(session.getId());

			  List<Item> itemList = null; 
			  try { 
				  itemList = Controller.listAllItems("category"); 
			  }
			  catch (SQLException e) { 
				  // TODO Auto-generated catch block
				  e.printStackTrace(); 
			  }
			  
			  
			  session.setAttribute("itemList", itemList);
			 
			 response.sendRedirect("Index.jsp"); 

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
	}

	public static void init_db() throws SQLException {
		Connection connect = null;
		Statement statement = null;

		if (connect == null || connect.isClosed()) {
			try {
				// Register JDBC driver
				Class.forName(JDBC_DRIVER);
				// Open up a connection
				connect = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
				System.out.println(connect);

				// Execute a query
				statement = connect.createStatement();
				// Create the new database
				String sql = "CREATE DATABASE IF NOT EXISTS projectdb";
				statement.executeUpdate(sql);

				// connect to newly created projectdb database
				connect = (Connection) DriverManager.getConnection(PROJDB_URL, USER, PASS);

				PreparedStatement drop = connect.prepareStatement("drop table if exists Reviews");
				drop.execute();
				drop.close();
				drop = connect.prepareStatement("drop table if exists FavoriteUsers");
				drop.execute();
				drop.close();
				drop = connect.prepareStatement("drop table if exists FavoriteItems");
				drop.execute();
				drop.close();
				drop = connect.prepareStatement("drop table if exists Item");
				drop.execute();
				drop.close();
				drop = connect.prepareStatement("drop table if exists Users");
				drop.execute();
				drop.close();
				System.out.println("Dropped all tables");
				
				
				// Create Tables
				PreparedStatement stmt = connect.prepareStatement(USERS);
				stmt.execute();
				System.out.println("Created Users table");
				
				stmt = connect.prepareStatement(FAVE_USERS);
				stmt.execute();
				System.out.println("Created favorite usrs table");
				stmt.close();

				PreparedStatement stmt1 = connect.prepareStatement(ITEM);
				stmt1.execute();
				System.out.println("Created Item table");
				stmt1.close();
		
				PreparedStatement stmt2 = connect.prepareStatement(REVIEW);
				stmt2.execute();
				System.out.println("Created Reviews table");
				stmt2.close();

				
				
				PreparedStatement stmt6 = connect.prepareStatement(FAVE_ITEMS);
				stmt6.execute();
				System.out.println("Created FavoriteItems table");
				stmt6.close();
				
				// set auto_increment initial value
				PreparedStatement stmt4 = connect.prepareStatement("ALTER TABLE Item AUTO_INCREMENT = 1000");
				stmt4.execute();
				stmt4.close();
				System.out.println("Item table auto incremented from 1000");
				
				PreparedStatement stmt5 = connect.prepareStatement("ALTER TABLE Reviews AUTO_INCREMENT = 3000");
				stmt5.execute();
				stmt5.close();
				System.out.println("Reviews table auto incremented from 3000");


				
				
				//Execute Trigger Statements
				  PreparedStatement reviewTrigger =
				  connect.prepareStatement(noMoreThanFiveReviews); 
				  reviewTrigger.execute();
				  PreparedStatement postedTrigger =
				  connect.prepareStatement(noMoreThanFivePosted); 
				  postedTrigger.execute();
				 

				User user[] = new User[10];
				Item item[] = new Item[10];
				Reviews review[] = new Reviews[10];

				user[0] = new User(1, "Hawraa", "Banoon", "root@email.com", "Female", 20, true, "pass1234");
				user[1] = new User(2, "Mohammed", "Hamza", "email1@email.com", "Male", 20, false, "pass1234");
				user[2] = new User(3, "John", "Doe", "email2@email.com", "Male", 30, false, "pass1234");
				user[3] = new User(4, "Bon", "Herr", "email3@email.com", "Female", 24, false, "pass1234");
				user[4] = new User(5, "Eric", "Shun", "email4@email.com", "Male", 26, false, "pass1234");
				user[5] = new User(6, "Harry", "Pooted", "email5@email.com", "Male", 32, false, "pass1234");
				user[6] = new User(7, "Hermoine", "Gronger", "email6@email.com", "Female", 18, false, "pass1234");
				user[7] = new User(8, "Mike", "Crotch", "email7@email.com", "Male", 96, false, "pass1234");
				user[8] = new User(9, "Snor", "Lax", "email8@email.com", "Male", 24, false, "pass1234");
				user[9] = new User(10, "Peek", "Atchu", "email9@email.com", "Female", 34, false, "pass1234");

				for (int i = 0; i < 10; i++) {
					insertUser(connect, user[i]);
				}
				
				item[0] = new Item(1000, 50, "Shades", "Pink shades, pinkiest of them all", "Luxury", 1, "2019-10-12");
				item[1] = new Item(1001, 400, "Gucci Flipflops", "In one word, this item is very gucci", "Luxury", 1, "2019-10-12");
				item[2] = new Item(1002, 25, "Lip Gloss", "Like pink? This is for you.", "Beauty", 2, "2019-07-22");
				item[3] = new Item(1003, 100000, "Howie Bananas","This isn't your typical banana... it's pink (warning: not meant for eating)", "Food", 2, "2019-07-22");
				item[4] = new Item(1004, 95.48, "Peach", "This is naturally pink", "Food", 2, "2019-07-24");
				item[5] = new Item(1005, 5.98, "Pink Stapler", "One of kind, hand-made", "Office", 3, "2018-01-01");
				item[6] = new Item(1006, 12.23, "Eiffel Tower", "Pink eiffel tower", "Places", 4, "2019-01-07");
				item[7] = new Item(1007, 49.11, "Folders", "Pretty pink folders with flowers", "Office", 5, "2017-05-01");
				item[8] = new Item(1008, 48.07, "Phone Case", "Pink phone case", "Accessory", 6, "2019-10-12");
				item[9] = new Item(1009, 58.81, "Eye Liner", "Pretty stuff", "Beauty", 7, "2019-07-22");
				for (int j = 0; j < 10; j++) {
					insertItem(connect, item[j]);
				}

				

				review[0] = new Reviews(3000, 1, 1000, "Poor", "This is good", "2019-10-12");
				review[1] = new Reviews(3001, 2, 1001, "Poor", "This is better", "2019-10-12");
				review[2] = new Reviews(3002, 3, 1002, "Fair", "This is the best", "2019-10-12");
				review[3] = new Reviews(3003, 4, 1003, "Excellent", "C'est bon", "2019-10-12");
				review[4] = new Reviews(3004, 5, 1004, "Poor", "C'est le mieux", "2019-10-12");
				review[5] = new Reviews(3005, 6, 1005, "Fair", "Bala", "2019-10-12");
				review[6] = new Reviews(3006, 7, 1006, "Excellent", "Bala bala", "2019-10-12");
				review[7] = new Reviews(3007, 8, 1007, "Good", "Bien", "2019-10-12");
				review[8] = new Reviews(3008, 9, 1008, "Good", "Le mieux", "2019-10-12");
				review[9] = new Reviews(3009, 10, 1009, "Poor", "This sucks", "2019-10-12");
				for (int k = 0; k < 10; k++) {
					insertReview(connect, review[k]);
				}

				System.out.println("Database initialized successfully...");
				
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
			} catch (Exception e) {
				// Handle errors for Class.forName
				e.printStackTrace();
			} finally {
				System.out.println("Closing opened resources...");
				// finally block used to close resources
				try {
					if (statement != null) {
						statement.close();
						System.out.println("Statement variable closed");
					}
				} catch (SQLException se2) {
				} // nothing we can do
				try {
					if (connect != null) {
						connect.close();
						System.out.println("Connect variable closed");
					}
				} catch (SQLException se) {
					se.printStackTrace();
				} // end finally try
			}
		}

	}

	private static void insertUser(Connection connect, User user) throws SQLException {
		PreparedStatement stmt = connect.prepareStatement(INSERT_USER);
		stmt.setInt(1, user.getUserID());
		stmt.setString(2, user.getFirstName());
		stmt.setString(3, user.getLastName());
		stmt.setString(4, user.getEmail());
		stmt.setString(5, user.getGender());
		stmt.setInt(6, user.getAge());
		stmt.setBoolean(7, user.isRoot());
		stmt.setString(8, user.getPassword());
		stmt.execute();

	}

	private static void insertItem(Connection connect, Item item) throws SQLException, Exception {
		PreparedStatement stmt = connect.prepareStatement(INSERT_ITEM);
		stmt.setInt(1, item.getItemID());
		stmt.setDouble(2, item.getPrice());
		stmt.setString(3, item.getName());
		stmt.setString(4, item.getDescription());
		stmt.setString(5, item.getCategory());
		stmt.setInt(6,item.getSeller());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt = sdf.parse(item.getDatePosted());
		java.sql.Date datePosted = new Date(dt.getTime());
		stmt.setDate(7, datePosted);
		stmt.execute();
	}

	protected static void insertReview(Connection connect, Reviews review) throws SQLException, Exception {
		PreparedStatement stmt = connect.prepareStatement(INSERT_REVIEWS);
		stmt.setInt(1, review.getReviewID());
		stmt.setInt(2, review.getUserID());
		stmt.setInt(3, review.getItemID());
		stmt.setString(4, review.getRating());
		stmt.setString(5, review.getDescription());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt = sdf.parse(review.getDateOfReview());
		java.sql.Date reviewDate = new Date(dt.getTime());
		stmt.setDate(6, reviewDate);
		stmt.execute();
	}

	public static void callLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		User user = Controller.login(email, password);

		// ========================================================================================
		//
		// https://www.youtube.com/watch?v=4GfAhuKNCdM - video for validating users
		// using sessions
		//
		// =========================================================================================
		if (user != null) {
			session = request.getSession(true);
			String id = session.getId();
			System.out.println(id);
			/* session.setMaxInactiveInterval(5); */
			List<Item> itemList = null;
			try {
				itemList = Controller.listAllItems("category");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("userID", user.getUserID());
			session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
			session.setAttribute("itemList", itemList);
			if(user.isRoot())
				session.setAttribute("root", user.isRoot());
			/*
			 * RequestDispatcher dispatch = request.getRequestDispatcher("Index.jsp");
			 * dispatch.forward(request, response);
			 */
			response.sendRedirect("Index.jsp");
		} else {
			session.setAttribute("LoginFailed", true);
			/*
			 * RequestDispatcher dispatch = request.getRequestDispatcher("Login.jsp");
			 * dispatch.forward(request, response);
			 */
			response.sendRedirect("Login.jsp");
		}
	}

	public static User login(String email, String password) {
		Connection connect = null;
		Statement statement = null;

		try {
			Class.forName(JDBC_DRIVER);
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);

			PreparedStatement stmt = connect.prepareStatement(LOGIN_QUERY);
			stmt.setString(1, email);
			stmt.setString(2, password);

			// Store the result from executing the LOGIN_QUERY
			// Users Table Schema: {int userID, str fName, str lName,
			// str email, str gender, int age, bool root, str password}
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				int userID = result.getInt(1);
				String fName = result.getString(2);
				String lName = result.getString(3);
				String gender = result.getString(4);
				int age = result.getInt(5);
				boolean root = result.getBoolean(6);

				return new User(userID, fName, lName, email, gender, age, root, password);
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		return null;
	}

	public static User register(String fName, String lName, String email, String gender, int age, String password) {
		Connection connect = null;
		Statement statement = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);

			// Check if email passed in already exists in the DB
			String query = "SELECT COUNT(email) FROM Users WHERE email=?";
			PreparedStatement stmt = connect.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet result = stmt.executeQuery();
			int count = -1;
			while (result.next()) {
				count = result.getInt(1);
			}
			if (count > 0 || count == -1) {
				// Cannot have duplicate emails
				return null;
			} else {
				result.close();
				stmt.close();

				// Need to determine id for newUser
				statement = connect.createStatement();
				ResultSet result2 = statement.executeQuery("SELECT COUNT(userID) from Users");
				int userID = -1;
				while (result2.next()) {
					userID = result2.getInt(1);
				}
				userID = userID + 1;

				// Insert User
				User user = new User(userID, fName, lName, email, gender, age, false, password);
				insertUser(connect, user);
				return user;
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		return null;
	}

	public static List<Item> listAllItems(String sortBy) throws SQLException {
		List<Item> listItems = new ArrayList<Item>();
		Connection connect = null;
		Statement statement = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			String query = "SELECT * from item";
			if(sortBy.equals("category"))
				query = "SELECT * from Item as I order by I.category";
			else if(sortBy.equals("price"))
				query = "SELECT * from Item as I order by I.price asc";
			
			statement = (Statement) connect.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				int itemID = resultSet.getInt("itemID");
				double price = resultSet.getDouble("price");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String category = resultSet.getString("category");
				int seller = resultSet.getInt("seller");
				/* String imageURL = resultSet.getString("imageURL"); */
				String datePosted = resultSet.getDate("datePosted").toString();
				Item item = new Item(itemID, price, name, description, category, seller, datePosted);
				listItems.add(item);
			}
			return listItems;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		return listItems;
	}
	public static List<User> listAllUsers() throws SQLException {
		List<User> listUsers = new ArrayList<User>();
		Connection connect = null;
		Statement statement = null;
		int currentUser = (int) session.getAttribute("userID");


		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(PROJDB_URL, USER, PASS);
			
			//Display all users except the current user that is logged in. 
			String query = "SELECT userID,firstName,lastName from users WHERE NOT(userID = "+currentUser+")";
			
			statement = (Statement) connect.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				int userID = resultSet.getInt("userID");
				String fName = resultSet.getString("firstName");
				String lName = resultSet.getString("lastName");
				/* String imageURL = resultSet.getString("imageURL"); */

				User user = new User(userID, fName, lName);
				listUsers.add(user);
			}
			return listUsers;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		return listUsers;
	}
	
	
    public static Connection getConnection() throws SQLException {
		return DriverManager
                .getConnection("jdbc:mysql://localhost:3306/projectdb?" + "user=john&password=pass1234");
	}

}
