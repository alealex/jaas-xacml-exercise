package it.security.xacml.example.model;

public class Professor {

	private Integer _id;
	private String username;
	private String name;
	private String surname;
	private String dateOfBirth;
	private String professorNumber;
	private String subject;
	
	public Professor(){
		this._id=0;
		this.username="";
		this.name="";
		this.surname="";
		this.dateOfBirth="";
		this.professorNumber="";
		this.subject="";
	}
	
	public Professor(
			Integer _id,
			String username,
			String name,
			String surname,
			String dateOfBirth,
			String professorNumber,
			String subject){
		this._id=_id;
		this.username=username;
		this.name=name;
		this.surname=surname;
		this.dateOfBirth=dateOfBirth;
		this.professorNumber=professorNumber;
		this.subject=subject;
	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProfessorNumber() {
		return professorNumber;
	}

	public void setProfessorNumber(String professorNumber) {
		this.professorNumber = professorNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
