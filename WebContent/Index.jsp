<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.io.IOException" %>
<%@page import="javax.servlet.RequestDispatcher" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Home Page</title>
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
<body style="padding-bottom: 40px;">
	 <%
	 	HttpSession sess = request.getSession(false);
/* 	 	if(sess.getAttribute("userID") == null){
			response.sendRedirect("Login.jsp");
		}  */
	 %>
<div class="wrapper">
	<!-- Begin Side-Bar -->
	<nav class="navbar navbar-light navbar-static bg-faded" role="navigation">
    	<button class="navbar-toggler" id="navbarSideButton" type="button">
      		&#9776;
    	</button>
		<ul class="navbar-side" id="navbarSide">
 			<li class="navbar-side-item">
 				<i style="margin-left:5px;" class="fa fa-users"></i>
    			<a onClick="" class="side-link">View All Users</a>
  			</li>
  			 <li class="navbar-side-item">
    			<a href="#" class="side-link">Part 3.2</a>
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
	
	<!-- Card Insert Item -->
	<div class="card">
		<div class="card-header text-white" style="background:#ffcccc">Insert Item</div>
		<div class="card-body">
		<!-- Uploading Files: https://www.guru99.com/jsp-file-upload-download.html -->
		<!-- https://stackoverflow.com/questions/14723812/how-do-i-call-a-specific-java-method-on-a-click-submit-event-of-a-specific-butto -->
		<form name="addItemForm" class="form-controls" method="post" action="Controller?action=addItem&userID=${userID}&userName=${userName}&root=${root}">
			<label>Title</label>
			<input class="w3-input w3-border" type="text" name="title"/>
			
			<label>Description</label>
			<input class="w3-input w3-border" type="text" name="description"/>
			
			<label>Category</label>
			<input class="w3-input w3-border" type="text" name="category">
			
			<label>Price</label>
			<input class="w3-input w3-border" type="text" name="price"/>
			
	<!-- 		<label>Upload Image</label><br>
			<input class="form-control-file" type="file" name="image"/><br><br> -->
			<br><input type="submit" class="w3-btn pink" value="Add"/>
		</form>
		</div>
	</div>
	<!-- End Card View -->
	
	<!-- Card Search Item -->
	<div class="card">
		<div class="card-header text-white" style="background: #ffcccc">Search Item</div>
		<div class="card-body">
			<!-- Search Bar and Category Button -->
			<div class="row">
				<div class="dropdown col-sm-2">
					<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Category</button>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="Controller?action=sortByPrice&sortBy=price">Price</a>
						<a class="dropdown-item" href="#">Other</a>
					</div>
				</div>
				<div class="col-sm-10">
					<form class="">
						<input id="searchText" class="form-control" type="text" placeholder="Search.." style="/* width: 85% */">
					<!-- 	<button class="btn btn-success" type="submit">Search</button> -->
					</form>
				</div>
			</div><br>
			<!--End Search and Category-->
			<!-- Item Table -->
			<table class="table table-bordered table-hover" id="itemTable">
				<thead>
					<tr>
						<!-- <td width="60" height="60"></td> --> <!-- For image category -->
						<td></td>
						<td>Category</td>
						<td>Name</td>
						<td>Description</td>
						<td>Price</td>
						<td>Add Review</td>
					</tr>
				</thead>
				<tbody id="itemData" class="panel">
					<c:forEach items="${itemList}" var="item">
						<tr <%-- data-toggle="collapse" data-parent="#itemTable" href="# ${item.itemID }" --%>>
							<%-- <td><img class="zoom" src="${item.imageURL }" width="60" height="60"></td> --%>
							<td style='white-space: nowrap; display: inline-block;'>
								<button onclick="window.location.href= 'Controller?action=addFave&itemID=${item.itemID}&itemName=${item.name}'" class="btn fa fa-thumbs-up"></button>
								<button onclick="window.location.href= 'Controller?action=removeFave&itemID=${item.itemID}&itemName=${item.name}'" class="btn fa fa-thumbs-down"></button>
							</td>
							<td>${item.category }</td>
							<td>${item.name }</td>
							<td>${item.description }</td>
							<td>$ ${item.price }</td>
							<td>
								<button class="dropdown-item" onclick = "window.location.href= 'Reviews.jsp?itemID=${item.itemID}'">Add a Review!</button>
							</td>
						</tr>
	<%-- 					<tr id="${item.itemID }" class="collapse in"> <!-- this doesn't seem to work, look at it later -->
							<td colspan="4" class="hiddenRow">Leave a review</td>
						</tr> --%>
					</c:forEach>
	
				</tbody>
			</table>
			
			<!-- BEGIN JAVASCRIPT -->
			<script>
				/* Search Functionality */
				$(document).ready(function(){
					$("#searchText").on("keyup", function() {
	  					var value = $(this).val().toLowerCase();
	  					$("#itemData tr").filter(function() {
	    				$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	  					});
					});
				});
				/* End Search Functionality */
				
				/* Highlight Selected Item */
				function changeLike(x) {
  					x.classList.toggle("fa-thumbs-down");
				}
				/* END Heart Functionality */
				
			</script>
			<!-- END OF JAVASCRIPT -->
			
		</div>
	</div>
	<!-- End Card View -->
</div>
</body>
</html>