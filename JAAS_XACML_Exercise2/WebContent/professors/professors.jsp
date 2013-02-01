<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>
  
  <meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
  <title>Professor Page</title>
  
</head><body>
<br>

<% HttpSession sessione = request.getSession(); String nome = (String) sessione.getAttribute("NAME")
	+" "+(String)sessione.getAttribute("SURNAME");
String username = (String) sessione.getAttribute("USERNAME");
String date = (String) sessione.getAttribute("DATE_OF_BIRTH");
String professorNumber = (String) sessione.getAttribute("PROFESSOR_NUMBER");
String subject = (String) sessione.getAttribute("SUBJECT");
Object LoginContext = (Object) sessione.getAttribute("LoginContext");

%>
<h2><i>Welcome <span style="color: black;"><%=nome %></span></i></h2>

<br>
<p> Your username is: <span style="color: black;"><%=username %></span> </p>
<p> You were born on : <span style="color: black;"><%=date %></span> </p>
<p> Your professorNumber is: <span style="color: black;"><%=professorNumber %></span> </p>
<p> You teach the following subject: <span style="color: black;"><%=subject %></span> </p>
<br>

<form name="logout" method="POST" action="http://localhost:8080/JAAS_XACML_Exercise2/LogoutServlet" >
  <input type="submit" value="Logout">
  <input type="hidden" name="LoginContext" value="<%=LoginContext%>"> <br>
</form>
</body></html>