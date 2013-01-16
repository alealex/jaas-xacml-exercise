package it.security.example.model;

public class Student {
	private Integer _id;
	private String username;
	private String name;
	private String surname;
	private String date_of_birth;
	private String student_number;
	
	public Student(){
		this._id=-1;
		this.username="";
		this.name="";
		this.surname="";
		this.date_of_birth="";
		this.student_number="";
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
		this.date_of_birth=dateOfBirth;
		this.student_number=studentNumber;
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

	public void setDate_Of_Birth(String dateOfBirth) {
		this.date_of_birth = dateOfBirth;
	}

	public String getStudent_Number() {
		return student_number;
	}

	public void setStudent_Number(String studentNumber) {
		this.student_number = studentNumber;
	}
}
