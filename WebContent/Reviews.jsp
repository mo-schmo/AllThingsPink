<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Reviews</title>
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
<div class="wrappter">
	<!-- Begin Side-Bar -->
	<nav class="navbar navbar-light navbar-static bg-faded" role="navigation">
    	<button class="navbar-toggler" id="navbarSideButton" type="button">
      		&#9776;
    	</button>
		<ul class="navbar-side" id="navbarSide">
 			<li class="navbar-side-item">
 				<i style="margin-left:5px; color:#f9d2f0;" class="fa fa-home fa-2x"></i>
    			<a href="Index.jsp?<%HttpSession sess = request.getSession(false);%>">Home</a>
  			</li>
  			 <li class="navbar-side-item">
 				<i style="margin-left:5px; color:#f9d2f0" class="fa fa-users fa-2x"></i>
    			<a href="Controller?action=users" class="">View All Users</a>
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
	 
	 
	 
	<div class="card">
		<div class="card-header text-white" style="background:#ffcccc">Leave a Review</div>
		<div class="card-body">
			<form method="post" action="/DBProject/Controller?action=leaveReview"> <input type="hidden" name="itemID" value='<%= request.getParameter("itemID")%>'></input>
				<div class="form-group">
					<label class="col-md-2" for="comment">Comment:</label>
					<textarea class="form control" rows="" id="comment" name="description"></textarea>
				</div>
				<div class="form-group">
					<label class="col-md-2" for="rating">Rating:</label>
					<select class="form" id="rating" name="rating">
						<option value="Excellent">Excellent</option>
						<option value="Good">Good</option>
						<option value="Fair">Fair</option>
						<option value="Poor">Poor</option>
					</select>
				</div>
				<div class="form-group">
					<label class="col-md-2" for="submit"></label>
					<input class="w3-btn pink" type="submit" name="submit" value="Submit"></input>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>