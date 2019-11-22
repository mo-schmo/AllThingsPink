<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="DBProject.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body background="pink.jpeg">
    <div class="loginbox">
    <img src="panda.png" class="avatar">
        <h1>Login Here</h1>
        <form method="post" action="/DBProject/LoginServlet" onSubmit="validateLogin()">
            <p>Username</p>
            <input type="text" name="username" placeholder="Enter Username">
            <p>Password</p>
            <input type="password" name="password" placeholder="Enter Password">
            <input type="submit" name="submit" value="Login">
            <a href="#" data-toggle="popover" data-trigger="hover" title="Forgot Password?" data-content="That's tough...">Lost your password?</a><br>
            <a href="Registration.jsp">Don't have an account?</a>
        </form>
    </div>
    <script>
    $(document).ready(function(){
    	  $('[data-toggle="popover"]').popover();   
    });
    </script>
<%--     <% if (request.getAttribute("LoginFailed")!=null) {
                 out.print(request.getAttribute("LoginFailed"));
                 }
                 
       if (request.getAttribute("successMessage")!=null) {
                 out.print(request.getAttribute("successMessage"));
                 }
     %> --%>
</body>
</html>