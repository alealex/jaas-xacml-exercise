<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type"><title>Login Page</title></head><body>
<form style="height: 83px;" method="POST" action="http://localhost:8080/JAAS_XACML_EXERCISE2/servlet" name="login">
  <div style="text-align: center;">
  </div>
<p style="text-align: center;"><strong><img style="width: 484px; height: 75px;" alt="TeamAndroid" 
	src="http://www.teamandroid.it/wp-content/themes/traction/images/logoTeamAndroid.png"><br>
</strong></p>
  <p style="text-align: center;"><strong>Please fill all these fields to login</strong><br>
  </p>

  <p style="text-align: center;"><small>username:</small><input name="username"> <br>
  </p>
  <p style="text-align: center;"><small>password:<input name="password" type="password"></small><br>
  </p>
  
  <div style="text-align: center;"><input name="Reset" value="Reset" type="reset">&nbsp; 
  <input value="Login" name="Login" type="submit">
  </div>
  
  <div style="text-align: center;"><input type="button" onclick="alert('Inserire nome pagina registrazione')" name="Sign In" value="Sign-In" ><br>

  </div>
</form>
<br>

</body></html>