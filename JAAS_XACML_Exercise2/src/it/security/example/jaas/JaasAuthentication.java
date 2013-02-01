package it.security.example.jaas;


import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class JaasAuthentication {
	
	private String username;
	private String password;
	private static LoginContext lc;

	// MARIO
	private String CONFIG_FILE_PATH = "/Users/mario_fio/Desktop/authenticationjaas.config";
	

	// PAKO
//	private String CONFIG_FILE_PATH = "/Users/pasqualederosa/Desktop/authenticationjaas.config";

	// GIANCARLO
//	private String CONFIG_FILE_PATH = "/home/giancarlo/Scrivania/authenticationjaas.config";


	public JaasAuthentication(String username, String password){
		this.username=username;
		this.password=password;
	}
	
	public Subject tryLogin ( ) {
		
		Boolean isLogged = false;
		Subject subject = null;
		
		
//		System.setProperty("java.security.auth.login.config", CONFIG_PATH
//				+"JAAS_XACML_Exercise2/WebContent/jaasconfiguration/authenticationjaas.config");	
		
		
		System.setProperty("java.security.auth.login.config", CONFIG_FILE_PATH);
		try {
			//Instanzio il loginContext ed il callback handler
			lc = new LoginContext("JaasConfigureFile",
					new JaasCallBackHandler(username, password));
			//Chiamata per effettuare le operazioni di login
			lc.login();
			
			subject = (Subject) lc.getSubject();
			System.out.println("Il subject Principals size e': "+lc.getSubject().getPrincipals().size());
			isLogged = true;
		} catch (LoginException e) {
			System.out.println("Login exception");			
			e.printStackTrace();
			isLogged = false;
		}
		
		if (isLogged) return subject;
		else return null;
	}
	
	public static LoginContext getLCinstance(){
		return lc;
	}
}
