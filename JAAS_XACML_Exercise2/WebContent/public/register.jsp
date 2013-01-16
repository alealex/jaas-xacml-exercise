<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDCS - Registration Page</title>
<SCRIPT TYPE="text/javascript">
function showMenu(mySel){
	var myVal;
	myVal = mySel.options[mySel.selectedIndex].value;
	if(myVal == "Professor"){
		document.getElementById('PROFESSOR_NUMBER').style.display='inline';
		document.getElementById('SUBJECT').style.display='inline';
		document.getElementById('STUDENT_NUMBER').style.display='none';
	}else if(myVal == "Student"){
		document.getElementById('PROFESSOR_NUMBER').style.display='none';
		document.getElementById('SUBJECT').style.display='none';
		document.getElementById('STUDENT_NUMBER').style.display='inline';
	}
}

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

  <p style="text-align: center;">Username:<input name="USERNAME"> <br></p>
  <p style="text-align: center;">Password:<input name="PASSWORD" type="password"><br></p>
  <p align="center">
	<select ONCHANGE="showMenu(this)" name="role">
		<option selected>Professor</option>
		<option>Student</option>
	</select>
  
  <p style="text-align: center;">Name:<input name="NAME"> <br></p>
  <p style="text-align: center;">Surname:<input name="SURNAME"> <br></p>
  <p style="text-align: center;">Date of birth:<input name="DATE_OF_BIRTH"> <br></p>
  <p style="text-align: center;">PROFESSOR ID:<input name="PROFESSOR_NUMBER"> <br></p>
  <p style="text-align: center;">STUDENT ID:<input name="STUDENT_NUMBER"> <br></p>
  <p style="text-align: center;">Subject<input name="SUBJECT"> <br></p>
  
  <div style="text-align: center;">
  <input name="reset" value="Reset" type="reset">&nbsp; 
  <input name="login" value="Submit"  type="submit">
  </div>
</form>
<br>
</body>
</html>