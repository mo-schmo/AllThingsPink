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
<div class="card">
	<div class="card-header text-white" style="background:#ffcccc">Leave a Review</div>
	<div class="card-body">
		<form class="form-group" method="post" action="/DBProject/ReviewServlet.java"> <input type="hidden" name="itemID" value='<%= request.getParameter("itemID")%>'></input>
			<table>
				<tr>
					<td>
						Description:
					</td>
					<td>
						<input type="text" name="description"></input>
					</td>
				</tr>
				<tr>
					<td>
						Score:
					</td>
					<td>
						<select name="score">
							<option value="Excellent">Excellent</option>
							<option value="Good">Good</option>
							<option value="Fair">Fair</option>
							<option value="Poor">Poor</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<input class="w3-btn pink" type="submit" name="submit" value="Submit"></input>
					</td>
					<td>
						<p><a href="Index.jsp?<%HttpSession sess = request.getSession(false);%>">Cancel</a></p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<% if (request.getAttribute("Error")!=null) {
						 out.print(request.getAttribute("Error"));
						 }%>
					</td>
				</tr>	
			</table>
		</form>
	</div>
</div>
</body>
</html>