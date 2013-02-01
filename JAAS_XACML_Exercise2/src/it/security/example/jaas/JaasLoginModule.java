package it.security.example.jaas;

import it.security.example.database.MyDatabase;
import it.security.example.model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class JaasLoginModule implements LoginModule {

	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String,?> sharedState;
	private Map<String,?> options;
	private User loggedUser;

	private boolean succeeded = false;

	public JaasLoginModule() {
		System.out.println("Login Module - constructor called");
	}

	public boolean abort() throws LoginException {
		System.out.println("Login Module - abort called");
		return false;
	}

	

	public void initialize(Subject subject,
			CallbackHandler callbackHandler,
			Map<String, ?> sharedState,
			Map<String, ?> options) {
		System.out.println("Login Module - " +
				"initialize called");
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
		
		System.out.println("SharedState: "+ this.sharedState.toString());
		System.out.println("Options: "+ options.toString());
		succeeded = false;
	}

	public boolean login() throws LoginException {
		System.out.println("Login Module - login called");
		if (callbackHandler == null) {
			throw new LoginException("Oops, callbackHandler is null");
		}

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("name:");
		callbacks[1] = new PasswordCallback("password:", false);

		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("Oops, IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("Oops, UnsupportedCallbackException calling handle on callbackHandler");
		}

		NameCallback nameCallback = (NameCallback) callbacks[0];
		PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
		loggedUser=new User();
		loggedUser.setUsername(nameCallback.getName());
		loggedUser.setPassword(String.valueOf(passwordCallback.getPassword()));

		MyDatabase database=null;
		try {
			database = new MyDatabase(options);
			database.createDatabaseConnection();
			if(database.loginUser(loggedUser.getUsername(), loggedUser.getPassword())){
				
				loggedUser.setRole(database.getUserRole(loggedUser));
				System.out.println("Il database ha tornato il valore");
				succeeded=true;
				}
			else{
				System.out.println("Il database non ha tornato il valore");

				succeeded=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return succeeded;
		
	}
	
	public boolean commit() throws LoginException {
		System.out.println("Login Module - commit called");		
		/*
		 * PRINT FOR DEBUG
		 */
		System.out.println("COMMIT ROLE: "+ loggedUser.getRole());
		System.out.println("COMMIT DB_URL: "+(String) options.get("dbUrl"));
		System.out.println("COMMIT DB_USERNAME: "+(String) options.get("dbUserName"));
		System.out.println("COMMIT DB_PASSWORD: "+(String) options.get("dbPassword"));
		System.out.println("COMMIT DB_ENCRYPT_KEY: "+(String) options.get("encryptKey"));
		System.out.println("COMMIT DB_DRIVER: "+(String) options.get("dbDriver"));
		/* ********************************* */
		
		subject.getPrincipals().add(new JaasPrincipal(
				loggedUser.getRole()));
		subject.getPrincipals().add(new JaasPrincipal(
				(String) options.get("dbUrl")));
		subject.getPrincipals().add(new JaasPrincipal(
				(String) options.get("dbUserName")));
		subject.getPrincipals().add(new JaasPrincipal(
				(String) options.get("dbPassword")));
		subject.getPrincipals().add(new JaasPrincipal(
				(String) options.get("dbDriver")));
		subject.getPrincipals().add(new JaasPrincipal(
				(String) options.get("encryptKey")));
		subject.getPrincipals().add(new JaasPrincipal(
				loggedUser.getUsername()));
		return succeeded;
	}

	public boolean logout() throws LoginException {
		System.out.println("Login Module - logout called");
		
		/*
		 * Rimuovo tutti i Principals relativi all'utente:
		 */
		try{
			subject.getPrincipals().remove(new JaasPrincipal(
					loggedUser.getRole()));
			subject.getPrincipals().remove(new JaasPrincipal(
					(String) options.get("dbUrl")));
			subject.getPrincipals().remove(new JaasPrincipal(
					(String) options.get("dbUserName")));
			subject.getPrincipals().remove(new JaasPrincipal(
					(String) options.get("dbPassword")));
			subject.getPrincipals().remove(new JaasPrincipal(
					(String) options.get("dbDriver")));
			subject.getPrincipals().remove(new JaasPrincipal(
					(String) options.get("encryptKey")));
			subject.getPrincipals().remove(new JaasPrincipal(
					loggedUser.getUsername()));		
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}

