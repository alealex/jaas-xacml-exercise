package it.security.example.model;

public class Student {
	private Integer _id;
	private String username;
	private String name;
	private String surname;
	private String dateOfBirth;
	private String studentNumber;
	
	public Student(){
		this._id=-1;
		this.username="";
		this.name="";
		this.surname="";
		this.dateOfBirth="";
		this.studentNumber="";
	}
	
	public Student(
			Integer _id,
			String username,
			String name,
			String surname,
			String dateOfBirth,
			String studentNumber){
		this._id=_id;
		this.username=username;
		this.name=name;
		this.surname=surname;
		this.dateOfBirth=dateOfBirth;
		this.studentNumber=studentNumber;
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

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
}
