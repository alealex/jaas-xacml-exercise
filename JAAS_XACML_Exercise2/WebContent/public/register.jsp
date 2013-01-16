<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDCS - Registration Page</title>

<script> 

	//Crea una nuova richiesta
	function newXMLHttpRequest() {
		var request = null;
		var browser = navigator.userAgent.toUpperCase();
		if(typeof(XMLHttpRequest) === "function" || typeof(XMLHttpRequest) === "object") {
			request = new XMLHttpRequest();
		} else if(window.ActiveXObject && browserUtente.indexOf("MSIE 4") < 0) {
			if(browser.indexOf("MSIE 5") < 0) {
				request = new ActiveXObject("Msxml2.XMLHTTP");
			} else {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return request;
	}
	
	// Callback: è stata editata la casella di testo
	function checkUsername() {
		var username = document.getElementById("USERNAME");
		if (username.value == "") {
			rimuoviUsername();
		} else {
			var req = newXMLHttpRequest();
			req.onreadystatechange = function() {
				if (req.readyState == 4) {
					if (req.status == 200) {
						gestisciRisposta(req.responseXML);
					} else if (req.status == 204){
						rimuoviUsername();
					}
				}
			};
			req.open("POST", "http://localhost:8080/JAAS_XACML_Exercise2/CheckUsernameServlet", true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			var params = "USERNAME=" + escape(username.value);
			req.send(params);
		}
	}
	
	// Gestisci la risposta dal server
	function gestisciRisposta(responseXML) {
		rimuoviUsername();
		if(responseXML.getElementsByTagName("username").length > 0) {
			var risposta = responseXML.getElementsByTagName("risposta")[0];
			if (risposta.childNodes.length > 0) {
				var check = risposta.childNodes[0].childNodes[0].nodeValue;
				document.getElementById('check_username').innerHTML= check;
				
				if(check == "false"){
					document.getElementById('check_username').innerHTML= "This is a valid username";
					document.getElementById('check_username').setAttribute("style", "color:green");
				}else{
					document.getElementById('check_username').innerHTML= "This username is already used";
					document.getElementById('check_username').setAttribute("style", "color:red");
				}
			}
		}
	}
	
	// Nessun suggerimento
	function rimuoviUsername() {
		var result= document.getElementById('check_username')
		var username = document.getElementById("USERNAME");
		while(username.hasChildNodes()) {
			username.removeChild(username.firstChild);
		}
		while(result.hasChildNodes()) {
			result.removeChild(result.firstChild);
		}
	}
	
	function innerSelection(mySel){
		
		if(mySel == "Professor"){
			var injectProfessorHTML = "<p style=\"text-align: left;\">Professor ID:<input name=\"PROFESSOR_NUMBER\"> <br></p>"+
			  "<p style=\"text-align: left;\">Subject: "+
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
	
	function clickReset(){
		document.getElementById('id_professor').innerHTML = "";
		document.getElementById('id_student').innerHTML = "";
		document.getElementById('check_username').innerHTML = "";
		document.getElementById('username_already_registered').innerHTML="";
	}
	
	function checkForm(){
		var check = document.getElementById('check_username').innerHTML;
		alert(check);
		
		if(check == "" ){
			document.getElementById('username_already_registered').innerHTML="Username not valid! please check ...";
			return false;
		} else if (check == "This username is already used"){
			document.getElementById('username_already_registered').innerHTML="Username not valid! please check ...";
			return false;
		} else if (check == "This is a valid username"){
			document.getElementById('username_already_registered').innerHTML="";
			return true;
		}
		
	}
	
</script>
</head>
<body>

<form style="height: 83px;" method="POST" onsubmit="return checkForm()" onreset="clickReset()" action="http://localhost:8080/JAAS_XACML_Exercise2/RegistrationServlet" name="registration">
  <div style="text-align: center;">
  </div>
<p style="text-align: center;"><strong><img style="width: 484px; height: 75px;" alt="TeamAndroid" 
	src="http://www.teamandroid.it/wp-content/themes/traction/images/logoTeamAndroid.png"><br>
</strong></p>
  <p style="text-align: center;"><strong>Please fill all these fields to register your account</strong><br>
  </p>

  <p style="text-align: left;">Username:<input id="USERNAME" name="USERNAME" type="text" onkeyup="checkUsername();"></p> 
  <div id="check_username"></div>
  <p style="text-align: left;">Password:<input name="PASSWORD" type="password"><br></p>
  <p align="left">Select a Role:
	<select onchange="innerSelection(this.value)" name="ROLE" >
		<option selected>Seleziona un ruolo...</option>
		<option>Professor</option>
		<option>Student</option>
	</select></p>
	
	<p style="text-align: left;">Name:<input name="NAME" > <br></p>
	<p style="text-align: left;">Surname:<input name="SURNAME"> <br></p>
  	<p style="text-align: left;">Date of birth:<input name="DATE_OF_BIRTH"> <br></p>
  
  <div id="id_professor"></div>
  <div id="id_student"></div>
  
  <div style="text-align: left;">
  <input name="reset" value="Reset" type="reset">&nbsp; 
  <input name="login" value="Submit"  type="submit">
  	<div id="username_already_registered"></div>
  </div>
</form>
<br>
</body>
</html>