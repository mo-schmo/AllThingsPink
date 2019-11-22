<%--

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%
String id = request.getParameter("PriceHighToLow");
String driverName = "com.mysql.jdbc.Driver";
String connectionUrl = "jdbc:mysql://localhost:3306/";
String dbName = "projectdb";
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

	<%
	
	public String getStuffs(HttpServletRequest request, HttpServletResponse response){
	String sqlStatement; 
    switch (id) {
    	case "PriceLowToHigh":
    		sqlStatement = "select * from item Order by price ASC";
    		break;
        case "PriceHighToLow ":
      		sqlStatement = "select * from item order by price DESC"; 
      	case "Category": 
      		sqlStatement = "select * from item Order by category ASC"; 
    		break;
        default:          	
        	sqlStatement =   "select * from item";       	
            break; 
    	}
    	return sqlStatement; 
	}
	
            %> 
--%>