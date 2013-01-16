package it.security.example.model;

public class Professor {

	private Integer _id;
	private String username;
	private String name;
	private String surname;
	private String date_of_birth;
	private String professor_number;
	private String subject;
	
	public Professor(){
		this._id=0;
		this.username="";
		this.name="";
		this.surname="";
		this.date_of_birth="";
		this.professor_number="";
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
		this.date_of_birth=dateOfBirth;
		this.professor_number=professorNumber;
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

	public String getDate_Of_Birth() {
		return date_of_birth;
	}

	public void setDate_Of_Birth(String date_Of_Birth) {
		this.date_of_birth = date_Of_Birth;
	}

	public String getProfessor_Number() {
		return professor_number;
	}

	public void setProfessor_Number(String professor_number) {
		this.professor_number = professor_number;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
