package it.security.example.jaas;

import java.security.Principal;

public class JaasPrincipal implements Principal {

    private String username;

    
    public JaasPrincipal(String username) {
	if (username == null)
	    throw new NullPointerException("illegal null input");
		this.username = username;
    }

    @Override
    public String getName() {
	return username;
    }
 
    public int hashCode() {
	return username.hashCode();
    }



}
