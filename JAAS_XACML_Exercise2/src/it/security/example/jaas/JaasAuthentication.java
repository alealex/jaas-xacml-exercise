package it.security.example.jaas;


import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class JaasAuthentication {
	
	private String username;
	private String password;

	// PAKO
//	private String CONFIG_PATH="/Users/pasqualederosa/Documents/workspaceSDCS/";
//  private String CONFIG_FILE_PATH = "/home/giancarlo/Scrivania/authenticationjaas.config";

	
	// MARIO
	private String CONFIG_PATH="/Users/mario_fio/Documents/workspace-security/";
	private String CONFIG_FILE_PATH = "/Users/mario_fio/Desktop/authenticationjaas.config";
	
	// GIANCARLO
//	private String CONFIG_PATH="/home/giancarlo/Scrivania/Cioppy/Università/Magistrale/Applicazioni Telematiche/workspace2/";
//	private String CONFIG_FILE_PATH = "/home/giancarlo/Scrivania/authenticationjaas.config";

	public JaasAuthentication(String username, String password){
		this.username=username;
		this.password=password;
	}
	
	public boolean tryLogin ( ) {
//		System.setProperty("java.security.auth.login.config", CONFIG_PATH
//				+"JAAS_XACML_Exercise2/WebContent/jaasconfiguration/authenticationjaas.config");	
		System.setProperty("java.security.auth.login.config", CONFIG_FILE_PATH);
		
		try {
			//Instanzio il loginContext ed il callback handler
			LoginContext lc = new LoginContext("JaasConfigureFile", new JaasCallBackHandler(username, password));
			//Chiamata per effettuare le operazioni di login
			lc.login();
			System.out.println("Il subject e': "+lc.getSubject().toString());
			return true;
		} catch (LoginException e) {
			System.out.println("Login exception");			
			e.printStackTrace();
			return false;
		}
	}
}
