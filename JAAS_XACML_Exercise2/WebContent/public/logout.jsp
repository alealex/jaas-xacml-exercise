<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%  HttpSession sessione = request.getSession();
	sessione.removeAttribute("utente");
	sessione.removeAttribute("ruolo");
	sessione.removeAttribute("error");
	sessione.invalidate();
   %>
<jsp:forward page="/index.jsp"></jsp:forward>

</body>
</html>