<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Users Page</title>
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
  	
<style>
		.fa {
  			font-size: 20px;
  			cursor: pointer;
  			user-select: none;
		}

		.fa:hover {
 			color: #F999D7;
		}
  	</style>

</head>
<body style="padding-bottom:40px;">
	 <%
	 	HttpSession sess = request.getSession(false);
/* 	 	if(sess.getAttribute("userID") == null){
			response.sendRedirect("Login.jsp");
		}  */
	 %>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%
String driverName = "com.mysql.jdbc.Driver";
String connectionUrl = "jdbc:mysql://localhost:3306/";
String dbName = "projectDB";
String userId = "john";
String password = "pass1234";

try {
Class.forName(driverName);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}

Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
%>
	<div class="wrapper">
			<!-- Begin Side-Bar -->
		<nav class="navbar navbar-light navbar-static bg-faded" role="navigation">
	    	<button class="navbar-toggler" id="navbarSideButton" type="button">
	      		&#9776;
	    	</button>
			<ul class="navbar-side" id="navbarSide">
	 			<li class="navbar-side-item">
	 				<i style="margin-left:5px; color:#f9d2f0;" class="fa fa-home fa-2x"></i>
	    			<a href="Index.jsp">Home</a>
	  			</li>
	  			 <li class="navbar-side-item">
	 				<i style="margin-left:5px; color:#f9d2f0" class="fa fa-users fa-2x"></i>
	    			<a href="Controller?action=users">View All Users</a>
	  			</li>
	  			<li class="navbar-side-item">
	    			<a href="#" class="side-link">Part 3.3</a>
	  			</li>
	  			<li class="navbar-side-item">
	    			<a href="#" class="side-link">Part 3.4</a>
	  			</li>
	  			<li class="navbar-side-item">
	    			<a href="#" class="side-link">Part 3.4</a>
	  			</li>
			</ul>
			<div class="overlay"></div>
		</nav>
		
		<script>
	
		$( document ).ready(function() {
	
		  // Open navbarSide when button is clicked
		  $('#navbarSideButton').on('click', function() {
		    $('#navbarSide').addClass('reveal');
		    $('.overlay').show();
		  });
	
		  // Close navbarSide when the outside of menu is clicked
		  $('.overlay').on('click', function(){
		    $('#navbarSide').removeClass('reveal');
		    $('.overlay').hide();
		  });
	
		});
		 </script>
		 <!-- End side-bar -->
		<!-- Working with navbar https://stackoverflow.com/questions/19733447/bootstrap-navbar-with-left-center-or-right-aligned-items -->
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-bottom">
		  <!-- Brand/logo -->
		  <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collaspe2">
			  <a class="navbar-brand" href="#">
			  	<% 
			  	if(sess.getAttribute("root") != null){ %>
			  			<img src="root.png" alt="logo" style="width:40px;">
			  		<%}
			  	%>
			  </a>
			  <ul class="navbar-nav">
			  	<li class="nav-item">
			  		<p class="navbar-brand">${userName}</p>	
			  	</li>
			  </ul>
		  </div>
		  <div class="mx-auto order-0">
		        <a class="navbar-brand mx-auto" style="color:pink" href="#">ALL THINGS PINK</a>
		        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
		            <span class="navbar-toggler-icon"></span>
		        </button>
		    </div>
		    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
		        <ul class="navbar-nav ml-auto">
		            <li class="nav-item">
		                <a class="nav-link" href="Controller?action=logout">Logout</a>
		            </li>
		        </ul>
		    </div>
		</nav> 
		<!-- End of Navigation Codes -->
		
		<div class="card">
			<div class="card-header text-white" style="background: #ffcccc">Registered Users</div>
		<div class="card-body">
			<!-- Search Bar and Category Button -->
			<div class="row">
				<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#sortByTwoPosted">Task 3.2</button>&nbsp
				<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#findFavoritedUsers">Task 3.5</button>
			</div><br>
			<div id="sortByTwoPosted" class="collapse">
   				<form method='post' action='Controller?action=sortByTwoPosted'>
    				<div class='form-group'><input class='form-control' type='text' name='category1' placeholder='Enter Category'/></div>
        			<div class='form-group'><input class='form-control' type='text' name='category2' placeholder='Enter Category'/></div>
        			<input class='btn btn-light' type='submit'/>
    			</form>
  			</div>
  			<div id="findFavoritedUsers" class="collapse">
   				<form method='post' action='Controller?action=findFavoritedUsers'>
    				<div class='input-group form-group'>
    					<div class='input-group-prepend'>
    						<span class="input-group-text">@</span>
    					</div>
    					<input class='form-control' type='text' name='userX' placeholder='Username'/>
    				</div>
    				<div class='input-group form-group'>
    					<div class='input-group-prepend'>
    						<span class="input-group-text">@</span>
    					</div>
    					<input class='form-control' type='text' name='userY' placeholder='Username'/>
    				</div>
    				<input class='btn btn-light' type='submit'/>
    			</form>
  			</div>
			<!--End Search and Category-->
			<div class="card-body">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<td></td>
							<td><b>User ID</b></td>
							<td><b>First Name</b></td>
							<td><b>Last Name</b></td>
						</tr>
					</thead>
					<tbody class="panel">
						<c:forEach items="${userList}" var="user">
							<tr>
								<td width=95>
									<i onClick="window.location.href='Controller?action=addFaveUser&tempID=${user.userID}'" class="fa fa-thumbs-up"></i>
									<i style="margin-left:10px;" onClick="window.location.href='Controller?action=removeFaveUser&tempID=${user.userID}'" class="fa fa-thumbs-down"></i>
								</td>
								<td><a href="userData.jsp?tempID=${user.userID }&firstName=${user.firstName}&lastName=${user.lastName}">${user.userID }</a></td>
								<td>${user.firstName }</td>
								<td>${user.lastName }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</div>
</body>
</html>