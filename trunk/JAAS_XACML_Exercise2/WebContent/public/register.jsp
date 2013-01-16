<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDCS - Registration Page</title>

<script> 
	
	function innerSelection(mySel){
		
		if(mySel == "Professor"){
			var injectProfessorHTML = "<p style=\"text-align: left;\">Professor ID:<input name=\"PROFESSOR_NUMBER\"> <br></p>"+
			  "<p style=\"text-align: left;\">Subject"+
			 	"<select name=\"SUBJECT\">"+
				"<option selected>SDCS</option>"+
				"<option>SE</option>"+
				"<option>CN2</option>"+
				"<option>CE2</option>"+
				"<option>IM</option>"+
				"</select>"+
			"</p>";
				document.getElementById('id_professor').innerHTML = injectProfessorHTML;
			
		}else if(mySel == "Student"){
			var injectStudentHTML ="<p style=\"text-align: left; \">Student ID:<input name=\"STUDENT_NUMBER\"> <br></p>";
			document.getElementById('id_student').innerHTML = injectStudentHTML;
		}
	}
	}
</script>
</head>
<body>

<form style="height: 83px;" method="POST" action="http://localhost:8080/JAAS_XACML_EXERCISE2/RegistrationServlet" name="registration">
  <div style="text-align: center;">
  </div>
<p style="text-align: center;"><strong><img style="width: 484px; height: 75px;" alt="TeamAndroid" 
	src="http://www.teamandroid.it/wp-content/themes/traction/images/logoTeamAndroid.png"><br>
</strong></p>
  <p style="text-align: center;"><strong>Please fill all these fields to login</strong><br>
  </p>

  <p style="text-align: left;">Username:<input name="USERNAME"> <br></p>
  <p style="text-align: left;">Password:<input name="PASSWORD" type="password"><br></p>
  <p align="left">Select a Role:
	<select onchange="innerSelection(this.value)" name="role" >
		<option selected>Professor</option>
		<option>Student</option>
	</select></p>
	
	<p style="text-align: left;">Name:<input name="NAME"> <br></p>
	<p style="text-align: left;">Surname:<input name="SURNAME"> <br></p>
  	<p style="text-align: left;">Date of birth:<input name="DATE_OF_BIRTH"> <br></p>
  
  <div id="id_professor"></div>
  <div id="id_student"></div>
  
  <div style="text-align: left;">
  <input name="reset" value="Reset" type="reset">&nbsp; 
  <input name="login" value="Submit"  type="submit">
  </div>
</form>
<br>
</body>
</html>