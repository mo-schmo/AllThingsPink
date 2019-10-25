<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="ISO-8859-1">
        <title>Register</title>
    </head>

    <body>
        <h1>User Registration</h1>
        <form name="registrationForm" action="/DBProject/RegistrationServlet" method="post" onSubmit="return validateForm()">
            <table style="width: 25%">
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstName" placeholder="Enter first name" /></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lastName" placeholder="Enter last name" /></td>
                </tr>
                <tr>
                	<td>Age</td>
                	<td><input type="number" name="age" min="18" placeholder="Enter age"></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" placeholder="Enter email" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" id="userPassword" placeholder="Enter password" /><br>
                    <input type="checkbox" onclick="showFunction()"/>Show Password</td>        
                </tr>
                <tr>
                    <td>Gender</td>
                    <td><input type="radio" name="gender" value="Male" checked/>M
                    	<input type="radio" name="gender" value="Female"/>F</td>
                </tr>
            </table>
            <input type="submit" value="Submit" /></form>
            
            <div class="container signin">
				<p>
					Already have an account? <a href="Login.jsp">Sign in</a>.
				</p>
			</div>
			<br>
			<div>
			<%
			if (request.getAttribute("errorMessage")!=null) {
				 out.print(request.getAttribute("errorMessage"));
				 }
			%>
			</div>
    </body>
    
    
   <!--FUNCTIONS--> 
	<script>
	function showFunction() {
		  var x = document.getElementById("userPassword");
		  if (x.type === "password") {
		    x.type = "text";
		  } else {
		    x.type = "password";
		  }
		}
	
	function validateForm(){
		
		var email = document.registrationForm.email;
		var fName = document.registrationForm.firstName;
		var lName = document.registrationForm.lastName;
		var pass = document.registrationForm.password;
		var bool = true;
		
		if(fName.value == "" || lName.value == "" || pass.value == "" ){
			alert("Please complete all fields")
			return false;
		}

		//Resource for regEX: https://html5-tutorial.net/form-validation/validating-email
		var emailFormat =/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if(!email.value.match(emailFormat)){
			alert("Please enter a valid email!")
			return false;
		}
	}
	
	</script>
    
    

    </html>