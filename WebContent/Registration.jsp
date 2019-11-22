<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="DBProject.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body background="pink.jpeg">
    <div class="loginbox" style="height: 650px">
    <img src="panda.png" class="avatar">
        <h1>Login Here</h1>
        <form name="registrationForm" method="post" action="/DBProject/RegistrationServlet" onSubmit="return validateForm()">
            <p>First Name</p>
            <input type="text" name="firstName" placeholder="Enter first name">
            <p>Last Name</p>
            <input type="text" name="lastName" placeholder="Enter last name">
            <p>Age</p>
            <input type="number" name="age" min="18" max="150" placeholder="Enter age">
            <p>Email</p>
            <input type="text" name="email" placeholder="Enter email">
            <p>Password</p>
            <input type="password" name="password" id="userPassword" placeholder="Enter Password">
            <input type="password" name="confirmPassword" id="userConfirmPassword" placeholder="Confirm Password">
            <div class="checkboxes">
            	<label><input type="checkbox" onclick="showFunction()"/>Show Password</label>
            </div>
            <input type="submit" name="submit" value="Submit">
            <a href="Login.jsp">Already have an account?</a>
        </form>
    </div>

   <!--FUNCTIONS--> 
    <script>
    function showFunction() {
          var x = document.getElementById("userPassword");
          var y = document.getElementById("userConfirmPassword");
          if (x.type === "password") {
            x.type = "text";
            y.type = "text";
          } else {
            x.type = "password";
            y.type = "password";
          }
        }
    
    function validateForm(){
        
        var email = document.registrationForm.email;
        var fName = document.registrationForm.firstName;
        var lName = document.registrationForm.lastName;
        var age = document.registrationForm.age;
        var pass = document.registrationForm.password;
        var confirmPass = document.registrationForm.confirmPassword;
        var bool = true;
        
        if(fName.value == "" || lName.value == "" || pass.value == "" || age.value == "" ){
            alert("Please complete all fields");
            return false;
        }

        //Resource for regEX: https://html5-tutorial.net/form-validation/validating-email
        var emailFormat =/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if(!email.value.match(emailFormat)){
            alert("Please enter a valid email!");
            return false;
        }
        
        if(pass.value != confirmPass.value){
        	alert("Passwords do not match!");
        	return false;
        }
    }
    
    </script>
</body>
</html>