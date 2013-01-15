package it.security.xacml.example.database;

public class Utente {

	private String username;
	private String password;
	private String nome;
	private String ruolo;
	
	public Utente(){
		this.username="";
		this.password="";
		this.nome="";
		this.ruolo="";
	}
	
	public Utente(
			String username,
			String password,
			String nome,
			String ruolo){
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.ruolo = ruolo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
}
