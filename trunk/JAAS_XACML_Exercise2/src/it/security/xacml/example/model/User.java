package it.security.xacml.example.model;

public class User {

	private Integer _id;
	private String username;
	private String password;
	private String role;
	
	public User(){
		this._id =0;
		this.username="";
		this.password="";
		this.role="";
	}
	
	public User(
			Integer _id,
			String username,
			String password,
			String role){
		this._id=_id;
		this.username = username;
		this.password = password;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}
}
