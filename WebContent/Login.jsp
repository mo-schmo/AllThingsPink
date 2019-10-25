<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="DBProject.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" type="text/css" href="style.css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<!-- 	<img src="Capture.PNG" 
	style = "width:50%; height:50%;" class = "center" >  -->
<body bgcolor = #ffc1c>
<div class = "center"> 
<form method="post" class="login-form" action="/DBProject/LoginServlet" style = "width:800px; height:800px;">
	<table background = "background.jpeg" style = "width:50%; height:50%; background-repeat: no-repeat" >
		<tr>
			<td>
				Username:
			</td>
			<td>
				<input type="text" name="username"></input>
			</td>
		</tr>
				<tr>
			<td>
				Password:
			</td>
			<td>
			 
              
				<input type="password" name="password"></input>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" name="submit" value="Submit"></input>
			</td>
			<td>
				<p>You don't have an account? <a href="Registration.jsp">Sign up</a>.</p>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<% if (request.getAttribute("LoginFailed")!=null) {
				 out.print(request.getAttribute("LoginFailed"));
				 }
				 
				if (request.getAttribute("successMessage")!=null) {
					 out.print(request.getAttribute("successMessage"));
					 }
				 %>
				 
				</td>
		</tr>
			
	</table>
</form>
</div>

</body>

</html>