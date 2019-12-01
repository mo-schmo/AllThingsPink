<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>User Information</title>
	
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" type="text/css" href="style.css">
	
	<!-- Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  	
  	<!-- Like Button -->
  	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<div class="wrapper">
		<%@page import="java.sql.DriverManager"%>
		<%@page import="java.sql.ResultSet"%>
		<%@page import="java.sql.Statement"%>
		<%@page import="java.sql.Connection"%>
		<%@page import="DBProject.Controller" %>
		
		<%
		String  userID = request.getParameter("tempID");
		String driverName = "com.mysql.jdbc.Driver";
		try {
		Class.forName(driverName);
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		%>
		<div class="card">
			<div class="card-header text-white" style="background: #ffcccc; font-size: 24px">User Info: <%=request.getParameter("firstName") +" "+ request.getParameter("lastName")%></div>
		</div>
		<%
		try{ 
		connection = Controller.getConnection();
		statement=connection.createStatement();
		String sql ="SELECT itemID,name,price,description FROM item WHERE seller="+userID;
		
		resultSet = statement.executeQuery(sql);
		while(resultSet.next()){
		%>
			<div class="card row">
    			<div class="card-body">
      				<h4 class="card-title"><%=resultSet.getString("name") %></h4>
      					<p class="card-text">Price: $<%=resultSet.getDouble("price")%></p>
      					<p class="card-text">Description: <%=resultSet.getString("description") %></p>
      					<button class="btn-primary" onclick = "window.location.href= 'Reviews.jsp?itemID=<%=resultSet.getInt("itemID")%>'">Add a Review!</button>
      					<button class="btn-primary" onclick = "">Add to Favorite</button>
    			</div>
  			</div>
  		<%
			}

		} 
		catch (Exception e) {
				e.printStackTrace();
			}
		%>
	</div>
</body>
</html>