package it.security.example.jaas;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class JaasAuthentication {
	
	private String username;
	private String password;
	
	public JaasAuthentication(String username, String password){
		this.username=username;
		this.password=password;
	}
	
	public void tryLogin ( ) {
		System.setProperty("java.security.auth.login.config", "jaas.config");			

		try {
			//Instanzio il loginContext ed il callback handler
			LoginContext lc = new LoginContext("ConfigFile", new JaasCallBackHandler(username, password));
			//Chiamata per effettuare le operazioni di login
			lc.login();
			System.out.println("Il subject e': "+lc.getSubject().toString());
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
}
