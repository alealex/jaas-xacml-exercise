package it.security.example.database;

import it.security.example.metadata.ProfessorMetaData;
import it.security.example.metadata.StudentMetaData;
import it.security.example.metadata.UserMetaData;
import it.security.example.model.Professor;
import it.security.example.model.Student;
import it.security.example.model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MyDatabase {
	
	private Connection connection;
	private ResourceBundle rb; 
	
	public MyDatabase () throws SQLException, ClassNotFoundException{
	
		rb = ResourceBundle.getBundle("db");
		Class.forName(rb.getString("properties.driver"));		
	}
	
	public Connection createDatabaseConnection() throws SQLException{
		
		System.out.println("BUNDLE - Driver:"+rb.getString("properties.driver"));
		System.out.println("BUNDLE - Username:" +rb.getString("properties.username"));
		System.out.println("BUNDLE - Password:" +rb.getString("properties.password"));
		
		setConnection(DriverManager.getConnection(
				rb.getString("properties.db.url"),
				rb.getString("properties.username"),
				rb.getString("properties.password")
				));
		return getConnection();
	}
	
	public boolean closeDatabaseConnection() throws SQLException{
		getConnection().close();
		if(getConnection().isClosed())
			return true;
		else return false;
	}
	
	public int addUser(
			String username,
			String password,
			String role)throws SQLException,
					InvalidKeyException,
					NoSuchAlgorithmException,
					NoSuchPaddingException, 
					IllegalBlockSizeException, 
					BadPaddingException,
					InvalidKeySpecException,
					UnsupportedEncodingException{
		
		String QUERY_ADD_USER = "INSERT INTO "+UserMetaData.USERS_TABLE+" "
				+"("+UserMetaData.USERS_USERNAME+","
				+UserMetaData.USERS_PASSWORD+","
				+UserMetaData.USERS_ROLE+")"
				+ "VALUES (?,?,?)";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_ADD_USER);
		      stmt.setString(1, Base64.encodeBytes(cipherTextValue(username.getBytes("UTF-8"))));
		      stmt.setString(2, Base64.encodeBytes(cipherTextValue(password.getBytes("UTF-8"))));
		      stmt.setString(3, Base64.encodeBytes(cipherTextValue(role.getBytes("UTF-8"))));
		      return stmt.executeUpdate();
		   }catch (SQLException e) {
			e.printStackTrace();
			return -1;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
	}

	public int addStudent(
			String username,
			String name,
			String surname,
			String dateOfBirth,
			String studentNumber
			) throws SQLException,
				InvalidKeyException,
				NoSuchAlgorithmException,
				NoSuchPaddingException, 
				IllegalBlockSizeException, 
				BadPaddingException,
				InvalidKeySpecException,
				UnsupportedEncodingException{
		
		String QUERY_ADD_STUDENT = "INSERT INTO "+StudentMetaData.STUDENTS_TABLE+" "
			+"("+StudentMetaData.STUDENTS_USERNAME+","
				+StudentMetaData.STUDENTS_NAME+","
				+StudentMetaData.STUDENTS_SURNAME+","
				+StudentMetaData.STUDENTS_DATE_OF_BIRTH+","
				+StudentMetaData.STUDENTS_NUMBER+")"
				+ "VALUES (?,?,?,?,?)";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_ADD_STUDENT);
		      stmt.setString(1, Base64.encodeBytes(cipherTextValue(username.getBytes("UTF-8"))));
		      stmt.setString(2, Base64.encodeBytes(cipherTextValue(name.getBytes("UTF-8"))));
		      stmt.setString(3, Base64.encodeBytes(cipherTextValue(surname.getBytes("UTF-8"))));
		      stmt.setString(4, Base64.encodeBytes(cipherTextValue(dateOfBirth.getBytes("UTF-8"))));
		      stmt.setString(5, Base64.encodeBytes(cipherTextValue(studentNumber.getBytes("UTF-8"))));
		      return stmt.executeUpdate();
		   }catch (SQLException e) {
			e.printStackTrace();
			return -1;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
	}
	
	public int addProfessor(
			String username,
			String name,
			String surname,
			String dateOfBirth,
			String professorNumber,
			String subject) throws SQLException,
				InvalidKeyException,
				NoSuchAlgorithmException,
				NoSuchPaddingException, 
				IllegalBlockSizeException, 
				BadPaddingException,
				InvalidKeySpecException,
				UnsupportedEncodingException{
		
		String QUERY_ADD_PROFESSOR = "INSERT INTO "+ProfessorMetaData.PROFESSORS_TABLE+" "
				+"("+ProfessorMetaData.PROFESSORS_USERNAME+","
				+ProfessorMetaData.PROFESSORS_NAME+","
				+ProfessorMetaData.PROFESSORS_SURNAME+","
				+ProfessorMetaData.PROFESSORS_DATE_OF_BIRTH+","
				+ProfessorMetaData.PROFESSORS_NUMBER+","
				+ProfessorMetaData.PROFESSORS_SUBJECT+")"
				+ "VALUES (?,?,?,?,?,?)";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_ADD_PROFESSOR);
		      stmt.setString(1,  Base64.encodeBytes(cipherTextValue(username.getBytes("UTF-8"))));
		      stmt.setString(2,  Base64.encodeBytes(cipherTextValue(name.getBytes("UTF-8"))));
		      stmt.setString(3,  Base64.encodeBytes(cipherTextValue(surname.getBytes("UTF-8"))));
		      stmt.setString(4,  Base64.encodeBytes(cipherTextValue(dateOfBirth.getBytes("UTF-8"))));
		      stmt.setString(5,  Base64.encodeBytes(cipherTextValue(professorNumber.getBytes("UTF-8"))));
		      stmt.setString(6,  Base64.encodeBytes(cipherTextValue(subject.getBytes("UTF-8"))));
		      return stmt.executeUpdate();
		   }catch (SQLException e) {
			e.printStackTrace();
			return -1;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
	}
	
	public Boolean checkUsername(String username)
			throws SQLException,
			InvalidKeyException,
			NoSuchAlgorithmException,
			NoSuchPaddingException, 
			IllegalBlockSizeException, 
			BadPaddingException,
			InvalidKeySpecException,
			UnsupportedEncodingException{
		
		String QUERY_CHECK_USERNAME = "SELECT COUNT(*) AS RESULT1 FROM "+UserMetaData.USERS_TABLE+
				" WHERE "+UserMetaData.USERS_USERNAME+"=?";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_CHECK_USERNAME);
		      stmt.setString(1,  Base64.encodeBytes(cipherTextValue(username.getBytes("UTF-8"))));
		      ResultSet rs = stmt.executeQuery();
		      
		      Integer userIsMatched= null;
		      if(rs.next()){
		    	  userIsMatched = rs.getInt("RESULT1");
		    	  System.out.println("Ho trovato: "+ userIsMatched + " utenti gia' registrati con questa useranme");
		      		if(userIsMatched > 0) return true;
		      		else return false;
		      }else return false;
		      
		      
		   }catch (SQLException e) {
			e.printStackTrace();
			return false;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
		
	}
	
	public Boolean loginUser(
			String username,
			String password)
					throws SQLException,
						InvalidKeyException,
						NoSuchAlgorithmException,
						NoSuchPaddingException, 
						IllegalBlockSizeException, 
						BadPaddingException,
						InvalidKeySpecException,
						UnsupportedEncodingException{
		
		String QUERY_LOGIN_USER = "SELECT COUNT(*) AS RESULT1 FROM "+UserMetaData.USERS_TABLE+
				" WHERE "+UserMetaData.USERS_USERNAME+"=?"+
				" AND "+UserMetaData.USERS_PASSWORD+"=?";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_LOGIN_USER);
		      stmt.setString(1,  Base64.encodeBytes(cipherTextValue(username.getBytes("UTF-8"))));
		      stmt.setString(2,  Base64.encodeBytes(cipherTextValue(password.getBytes("UTF-8"))));
		      ResultSet rs = stmt.executeQuery();
		      
		      Integer userIsMatched = rs.getInt("RESULT1");
		      if(userIsMatched == 1) return true;
		      else return false;
		      
		   }catch (SQLException e) {
			e.printStackTrace();
			return false;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
	}
		
	public String getUserRole(User utente) 
			throws SQLException,
					InvalidKeyException,
					NoSuchAlgorithmException,
					NoSuchPaddingException, 
					IllegalBlockSizeException, 
					BadPaddingException,
					InvalidKeySpecException,
					IOException{
		
		String QUERY_USER_ROLE = "SELECT DISTINCT"+UserMetaData.USERS_ROLE+
							" FROM "+UserMetaData.USERS_TABLE+
							" WHERE "+UserMetaData.USERS_USERNAME+"= ? ";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_USER_ROLE);
		      stmt.setString(1,  Base64.encodeBytes(cipherTextValue(utente.getUsername().getBytes("UTF-8"))));
		      ResultSet rs = stmt.executeQuery();
		      if (rs.next())
					return new String(decipherTextValues(Base64.decode(rs.getString(UserMetaData.USERS_ROLE))));
				else return null;
		   }catch (SQLException e) {
			e.printStackTrace();
			return null;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
	}
	
	public Student getStudent(String username) 
			throws SQLException,
				InvalidKeyException,
				NoSuchAlgorithmException,
				NoSuchPaddingException, 
				IllegalBlockSizeException, 
				BadPaddingException,
				InvalidKeySpecException,
				IOException{
		
		String QUERY_STUDENT = null;
		Student student = new Student();

		QUERY_STUDENT = "SELECT DISTINCT *"+
			" FROM "+StudentMetaData.STUDENTS_TABLE+
			" WHERE "+StudentMetaData.STUDENTS_USERNAME+"=?";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_STUDENT);
		      stmt.setString(1, Base64.encodeBytes(cipherTextValue(username.getBytes("UTF-8"))));
		      ResultSet rs = stmt.executeQuery();
		      if(rs.next()){
					student.set_id(Integer.valueOf(new String(rs.getBytes(StudentMetaData.STUDENTS_ID))));
					student.setUsername(new String(decipherTextValues(Base64.decode(rs.getString(StudentMetaData.STUDENTS_USERNAME)))));
					student.setName(new String(decipherTextValues(Base64.decode(rs.getString(StudentMetaData.STUDENTS_NAME)))));
					student.setSurname(new String(decipherTextValues(Base64.decode(rs.getString(StudentMetaData.STUDENTS_SURNAME)))));
					student.setStudent_Number(new String(decipherTextValues(Base64.decode(rs.getString(StudentMetaData.STUDENTS_NUMBER)))));
					student.setDate_Of_Birth(new String(decipherTextValues(Base64.decode(rs.getString(StudentMetaData.STUDENTS_DATE_OF_BIRTH)))));			
				}
				return student;
				
		   }catch (SQLException e) {
			e.printStackTrace();
			return null;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
	}
		
	public Professor getProfessor(String username)
			throws SQLException,
					InvalidKeyException,
					NoSuchAlgorithmException,
					NoSuchPaddingException, 
					IllegalBlockSizeException, 
					BadPaddingException,
					InvalidKeySpecException,
					IOException{
		
		String QUERY_PROFESSOR = null;
		Professor professor = new Professor();

		QUERY_PROFESSOR = "SELECT DISTINCT *"+
			" FROM "+ProfessorMetaData.PROFESSORS_TABLE+
			" WHERE "+ProfessorMetaData.PROFESSORS_USERNAME+"=?";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_PROFESSOR);
		      stmt.setString(1, Base64.encodeBytes(cipherTextValue(username.getBytes("UTF-8"))));
		      ResultSet rs = stmt.executeQuery();
		      if(rs.next()){
		    	  professor.set_id(Integer.valueOf(new String(rs.getBytes(ProfessorMetaData.PROFESSORS_ID))));
		    	  professor.setUsername(new String(decipherTextValues(Base64.decode(rs.getString(ProfessorMetaData.PROFESSORS_USERNAME)))));
		    	  professor.setName(new String(decipherTextValues(Base64.decode(rs.getString(ProfessorMetaData.PROFESSORS_NAME)))));
		    	  professor.setSurname(new String(decipherTextValues(Base64.decode(rs.getString(ProfessorMetaData.PROFESSORS_SURNAME)))));
		    	  professor.setProfessor_Number(new String(decipherTextValues(Base64.decode(rs.getString(ProfessorMetaData.PROFESSORS_NUMBER)))));
		    	  professor.setDate_Of_Birth(new String(decipherTextValues(Base64.decode(rs.getString(ProfessorMetaData.PROFESSORS_DATE_OF_BIRTH)))));			
		    	  professor.setSubject(new String(decipherTextValues(Base64.decode(rs.getString(ProfessorMetaData.PROFESSORS_SUBJECT)))));			
		      }
			return professor;
				
		   }catch (SQLException e) {
			e.printStackTrace();
			return null;
		   }
		   finally {
		      try {
		         if (stmt != null) { stmt.close(); }
		      }
		      catch (Exception e){e.printStackTrace();}
		   }
	}
	
	
	private byte[] cipherTextValue(byte[] valuesToBeCrypted) 
			throws NoSuchAlgorithmException, 
			NoSuchPaddingException, 
			InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException,
			InvalidKeySpecException,
			UnsupportedEncodingException{
		
		byte[] key = (rb.getString("properties.encryptKey")).getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16); // use only first 128 bit

		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		
		Cipher cipher =  Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		return cipher.doFinal(valuesToBeCrypted);	
	}
	
	private byte[] decipherTextValues(byte[] valuesToBeDecrypted)
			throws NoSuchAlgorithmException, 
			NoSuchPaddingException, 
			InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException,
			InvalidKeySpecException,
			UnsupportedEncodingException{
		byte[] key = (rb.getString("properties.encryptKey")).getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16); // use only first 128 bit

		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher =  Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		return cipher.doFinal(valuesToBeDecrypted);
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection){
		this.connection = connection;
	}
}
