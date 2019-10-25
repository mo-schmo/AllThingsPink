package DBProject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import java.util.List;
import java.util.Random;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/UserDOA")

public class UserDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Driver Name and DB URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";
	static final String PROJDB_URL = "jdbc:mysql://127.0.0.1:3306/projectdb?";
	
	//Create table queries
	private static String USERS = "CREATE TABLE Users ( userID int NOT NULL, firstName varchar(50), lastName varchar(50), email varchar(50), gender varchar(50), age int, root boolean NOT NULL, password varchar(255), PRIMARY KEY (userID));";
	private static String ITEM = "CREATE TABLE Item ( itemID int NOT NULL, price decimal, name varchar(50), description varchar(255), PRIMARY KEY (itemID) );";
	private static String REVIEW = "CREATE TABLE Reviews ( reviewID int NOT NULL, userID int NOT NULL, itemID int NOT NULL, rating varchar(10), description varchar(500), dateOfReview DATE, PRIMARY KEY (reviewID),  FOREIGN KEY (userID) REFERENCES Users(userID),FOREIGN KEY (itemID) REFERENCES Item(itemID) );";
	
	//Insert into tables queries
	private static String INSERT_USER = "INSERT INTO Users (userID, firstName, lastName, email, gender, age, root, password) VALUES(?,?,?,?,?,?,?,?);";
	private static String INSERT_ITEM = "INSERT INTO Item (itemID, price, name, description) VALUES(?,?,?,?);";
	private static String INSERT_REVIEWS = "INSERT INTO Reviews (reviewID, userID, itemID, rating, description, dateOfReview) VALUES(?,?,?,?,?,?);";
	
	//Select from Users table query, for login()
	private static String LOGIN_QUERY = "SELECT userID,firstName,lastName,gender,age,root FROM Users WHERE email=lower(?) and password=?";
	
	
	
	//Database credentials
	static final String USER = "john";
	static final String PASS = "pass1234";
	
	
	public UserDAO() {
		
	}
	
	
    public static void init_db() throws SQLException {
		Connection connect = null;
		Statement statement = null;
		
        if (connect == null || connect.isClosed()) {
            try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Open up a connection
            connect = (Connection) DriverManager
  			      .getConnection(DB_URL, USER, PASS);
            System.out.println(connect);
            
            //Execute a query
            statement = connect.createStatement();
            //Create the new database
            String sql = "CREATE DATABASE IF NOT EXISTS projectdb";
            statement.executeUpdate(sql);
            
            //connect to newly created projectdb database
            connect = (Connection) DriverManager
  			      .getConnection(PROJDB_URL, USER, PASS);
            
            PreparedStatement drop = connect.prepareStatement("drop table if exists Reviews");
            drop.execute();
            drop = connect.prepareStatement("drop table if exists Users");
            drop.execute();
            drop = connect.prepareStatement("drop table if exists Item");
            drop.execute();
            
            //Create Tables
            PreparedStatement stmt = connect.prepareStatement(USERS);
            stmt.execute();
            PreparedStatement stmt1 = connect.prepareStatement(ITEM);
            stmt1.execute();
            PreparedStatement stmt2 = connect.prepareStatement(REVIEW);
            stmt2.execute();
            
            User user[] = new User[10];
            Item item[] = new Item[10];
            Reviews review[] = new Reviews[10];
            
            item[0] = new Item(1000,  954.33, "Item1", "This is an item");
            item[1] = new Item(1001, 53.15, "Item2", "This is also an item");
            item[2] = new Item(1002, 4.12, "Item3", "This is also also an item");
            item[3] = new Item(1003, 5.19, "Item4", "This is also also also an item");
            item[4] = new Item(1004, 95.48, "Item5", "This is also also also also an item");
            item[5] = new Item(1005, 5.98, "Item6", "This is an item");
            item[6] = new Item(1006, 12.23, "Item7", "This is an item");
            item[7] = new Item(1007, 49.11, "Item8", "This is an item");
            item[8] = new Item(1008, 48.07, "Item9", "This is an item");
            item[9] = new Item(1009, 58.81, "Item10", "This is an item");
            for (int j = 0; j < 10; j++) {
            	insertItem(connect, item[j]);
            }
            
            user[0] = new User (1, "Hawraa", "Banoon", "root@email.com", "Female", 20, true, "pass1234");
            user[1] = new User (2, "Mohammed", "Hamza", "email1@email.com", "Male", 20, false, "pass1234");
            user[2] = new User (3, "John", "Doe", "email2@email.com", "Male", 30, false, "pass1234");
            user[3] = new User (4, "Bon", "Herr", "email3@email.com", "Female", 24, false, "pass1234");
            user[4] = new User (5, "Eric", "Shun", "email4@email.com", "Male", 26, false, "pass1234");
            user[5] = new User (6, "Harry", "Pooted", "email5@email.com", "Male", 32, false, "pass1234");
            user[6] = new User (7, "Hermoine", "Gronger", "email6@email.com", "Female", 18, false, "pass1234");
            user[7] = new User (8, "Mike", "Crotch", "email7@email.com", "Male", 96, false, "pass1234");
            user[8] = new User (9, "Snor", "Lax", "email8@email.com", "Male", 24, false, "pass1234");
            user[9] = new User (10, "Peek", "Atchu", "email9@email.com", "Female", 34, false, "pass1234");
            
            for (int i = 0; i < 10; i++) {
            	insertUser(connect, user[i]);
            }
            
            
            
            review[0] =	new Reviews(3000, 1, 1000, "Fair", "This is good", "10/12/2019");
            review[1] =	new Reviews(3001, 1, 1000, "Fair", "This is better", "10/12/2019");
            review[2] =	new Reviews(3002, 1, 1000, "Fair", "This is the best","10/12/2019");
            review[3] =	new Reviews(3003, 1, 1000, "Fair", "C'est bon", "10/12/2019");
            review[4] =	new Reviews(3004, 1, 1000, "Fair", "C'est le mieux","10/12/2019");
            review[5] =	new Reviews(3005, 1, 1000, "Fair", "Bala","10/12/2019");
            review[6] =	new Reviews(3006, 1, 1000, "Fair", "Bala bala","10/12/2019");
            review[7] =	new Reviews(3007, 1, 1000, "Fair", "Bien","10/12/2019");
            review[8] =	new Reviews(3008, 1, 1000, "Fair", "Le mieux","10/12/2019");
            review[9] =	new Reviews(3009, 1, 1000, "Fair", "This sucks","10/12/2019");
            for (int k = 0; k < 10; k++) {
            	insertReview(connect, review[k]);
            }
            
            
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
             }catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
             }finally{
                //finally block used to close resources
                try{
                   if(statement!=null)
                      statement.close();
                }catch(SQLException se2){
                }// nothing we can do
                try{
                   if(connect!=null)
                      connect.close();
                }catch(SQLException se){
                   se.printStackTrace();
                }//end finally try
             }
        }

    }
    
    
    private static void insertUser(Connection connect, User user) throws SQLException {
    	PreparedStatement stmt = connect.prepareStatement(INSERT_USER);
    	stmt.setInt(1,user.getUserID());
    	stmt.setString(2, user.getFirstName());
    	stmt.setString(3, user.getLastName());
    	stmt.setString(4, user.getEmail());
    	stmt.setString(5, user.getGender());
    	stmt.setInt(6, user.getAge());
    	stmt.setBoolean(7, user.isRoot());
    	stmt.setString(8, user.getPassword());
    	stmt.execute();
    	
    }
    
    private static void insertItem(Connection connect, Item item) throws SQLException {
    	PreparedStatement stmt = connect.prepareStatement(INSERT_ITEM);
    	stmt.setInt(1, item.getItemID());
    	stmt.setDouble(2, item.getPrice());
    	stmt.setString(3, item.getName());
    	stmt.setString(4, item.getDescription());
    	stmt.execute();
    }
    
    private static void insertReview(Connection connect, Reviews review) throws SQLException, Exception {
    	PreparedStatement stmt = connect.prepareStatement(INSERT_REVIEWS);
		stmt.setInt(1, review.getReviewID());
		stmt.setInt(2, review.getUserID());
		stmt.setInt(3, review.getItemID());
		stmt.setString(4, review.getRating());
		stmt.setString(5, review.getDescription());
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyy");
		java.util.Date dt = sdf.parse(review.getDateOfReview());
		Date reviewDate = new Date(dt.getTime());
		stmt.setDate(6, reviewDate);
		stmt.execute();
    }
    
    public static User login(String email, String password) {
    	Connection connect = null;
    	Statement statement = null;
    	
    	try {
    		connect = DriverManager.getConnection(PROJDB_URL,USER,PASS);
    		
    		PreparedStatement stmt = connect.prepareStatement(LOGIN_QUERY);
    		stmt.setString(1, email);
    		stmt.setString(2, password);
    		
    		//Store the result from executing the LOGIN_QUERY
    		//Users Table Schema: {int userID, str fName, str lName, 
    		//						str email, str  gender, int age, bool root, str password}
    		ResultSet result = stmt.executeQuery();
    		if(result.next()) {
    			int userID = result.getInt(1);
    			String fName = result.getString(2);
    			String lName = result.getString(3);
    			String gender = result.getString(4);
    			int age = result.getInt(5);
    			boolean root = result.getBoolean(6);
    			
    			return new User(userID, fName, lName, email, gender, age, root, password);
    		}
    		
    	}
    	catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
         }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
         }finally{
            //finally block used to close resources
            try{
               if(statement!=null)
                  statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
               if(connect!=null)
                  connect.close();
            }catch(SQLException se){
               se.printStackTrace();
            }//end finally try
         }
    	return null;
    }
    
    public static User register(String fName, String lName, String email, String gender, int age, String password) {
    	Connection connect = null;
    	Statement statement = null;
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver"); 
    		connect = DriverManager
    				.getConnection(PROJDB_URL,USER,PASS);
    		
    		//Check if email passed in already exists in the DB
    		String query = "SELECT COUNT(email) FROM Users WHERE email=?";
    		PreparedStatement stmt = connect.prepareStatement(query);
    		stmt.setString(1, email);
    		ResultSet result = stmt.executeQuery();
    		int count = -1;
    		while(result.next()) {
    			count = result.getInt(1);
    		}
    		if(count > 0 || count == -1) {
    			//Cannot have duplicate emails
    			return null;
    		}
    		else {
    			result.close();
    			stmt.close();
    			
    			//Need to determine id for newUser
    			statement = connect.createStatement();
    			ResultSet result2 = statement.executeQuery("SELECT COUNT(userID) from Users");
    			int userID = -1;
    			while(result2.next()) {
    				userID = result2.getInt(1);
    			}
    			userID = userID + 1;
    			
    			//Insert User
    			User user = new User(userID, fName, lName, email, gender, age, false, password);
    			insertUser(connect, user);
    			return user;    			
    		}
    		
    		
    	}
    	catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
         }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
         }finally{
            //finally block used to close resources
            try{
               if(statement!=null)
                  statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
               if(connect!=null)
                  connect.close();
            }catch(SQLException se){
               se.printStackTrace();
            }//end finally try
         }
    	return null;
    }
    
    
    
    
    
    
}
