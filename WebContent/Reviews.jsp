<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reviews</title>
</head>
<body>
<form method="post" action="/DBProject/ReviewServlet.java"> <input type="hidden" name="itemID" value='<%= request.getParameter("itemID")%>'></input>
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
				<input type="submit" name="submit" value="Submit"></input>
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
</body>
</html>