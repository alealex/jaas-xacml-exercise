<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>
  
  <meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
  <title>Student Page</title>
  
</head><body>
<br>

<% HttpSession sessione = request.getSession(); String nome = (String) sessione.getAttribute("NAME")
	+" "+(String)sessione.getAttribute("SURNAME");
String student_number = (String) sessione.getAttribute("STUDENT_NUMBER");
String username = (String) sessione.getAttribute("USERNAME");
String date = (String) sessione.getAttribute("DATE_OF_BIRTH");
%>
<h2><i>Welcome <span style="color: black;"><%=nome %></span></i></h2>

<br>
<p> Your UserName is: <span style="color: black;"><%=username %></span> </p>
<p> Your Student Number is: <span style="color: black;"><%=student_number %></span> </p>
<p> Your date of birth is: <span style="color: black;"><%=date%></span> </p>
<br>
<h3><a href="<%=request.getContextPath()%>/public/logout.jsp">Logout</a></h3><br>

</body></html>