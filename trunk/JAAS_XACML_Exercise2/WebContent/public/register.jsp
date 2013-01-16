<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDCS - Registration Page</title>
<SCRIPT TYPE="text/javascript">
<!--
function dropdown(mySel){
	var myWin, myVal;
	myVal = mySel.options[mySel.selectedIndex].value;
	if(myVal){
		if(mySel.form.target)myWin = parent[mySel.form.target];
		else myWin = window;
		if (! myWin) return true;
		myWin.location = myVal;
	}
	return false;
}
//-->
</SCRIPT>

</head>
<body>

<form style="height: 83px;" method="POST" action="http://localhost:8080/JAAS_XACML_EXERCISE2/servlet" name="login">
  <div style="text-align: center;">
  </div>
<p style="text-align: center;"><strong><img style="width: 484px; height: 75px;" alt="TeamAndroid" 
	src="http://www.teamandroid.it/wp-content/themes/traction/images/logoTeamAndroid.png"><br>
</strong></p>
  <p style="text-align: center;"><strong>Please fill all these fields to login</strong><br>
  </p>

  <p style="text-align: center;">Username:<input name="username"> <br></p>
  <p style="text-align: center;">Password:<input name="password" type="password"><br></p>
  <p align="center">
	<select ONCHANGE="showMenu(this.options[this.selectedIndex].value)" name="role">
		<option selected>Select a Role</option>
		<option>Professor</option>
		<option>Student</option>
	</select>
  
  <p style="text-align: center;">Nome:<input name="name"> <br></p>
  <p style="text-align: center;">Cognome:<input name="surname"> <br></p>
  <p style="text-align: center;">Data di nascita:<input name="date_of_birth"> <br></p>
  <p style="text-align: center;">Matricola:<input name="username"> <br></p>
  
  <div style="text-align: center;">
  <input name="Reset" value="Reset" type="reset">&nbsp; 
  <input value="Login" name="Login" type="submit">
  </div>
  
  <div style="text-align: center;"><input type="button" onclick="alert('Inserire nome pagina registrazione')" name="sign-up" value="Sign up" ><br>

  </div>
</form>
<br>
</body>
</html>