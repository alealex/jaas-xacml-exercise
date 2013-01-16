package it.security.example.database;

import it.security.example.metadata.ProfessorMetaData;
import it.security.example.metadata.StudentMetaData;
import it.security.example.metadata.UserMetaData;
import it.security.example.model.Professor;
import it.security.example.model.Student;
import it.security.example.model.User;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
				InvalidKeySpecException{
		
		String QUERY_ADD_STUDENT = "INSERT INTO "+StudentMetaData.STUDENTS_TABLE+" "
			+ "VALUES (?,?,?,?,?)";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_ADD_STUDENT);
		      stmt.setString(1, cipherTextValue(username));
		      stmt.setString(2, cipherTextValue(name));
		      stmt.setString(3, cipherTextValue(surname));
		      stmt.setString(4, cipherTextValue(dateOfBirth));
		      stmt.setString(5, cipherTextValue(studentNumber));
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
				InvalidKeySpecException{
		
		String QUERY_ADD_PROFESSOR = "INSERT INTO "+ProfessorMetaData.PROFESSORS_TABLE+" "
				+ "VALUES (?,?,?,?,?,?)";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_ADD_PROFESSOR);
		      stmt.setString(1, cipherTextValue(username));
		      stmt.setString(2, cipherTextValue(name));
		      stmt.setString(3, cipherTextValue(surname));
		      stmt.setString(4, cipherTextValue(dateOfBirth));
		      stmt.setString(5, cipherTextValue(professorNumber));
		      stmt.setString(6, cipherTextValue(subject));
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
	
	public Boolean loginUser(
			String username,
			String password)
					throws SQLException,
						InvalidKeyException,
						NoSuchAlgorithmException,
						NoSuchPaddingException, 
						IllegalBlockSizeException, 
						BadPaddingException,
						InvalidKeySpecException{
		
		String QUERY_LOGIN_USER = "SELECT COUNT(*) AS RESULT1 FROM "+UserMetaData.USERS_TABLE+
				" WHERE "+UserMetaData.USERS_USERNAME+"=?"+
				" AND "+UserMetaData.USERS_PASSWORD+"=?";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_LOGIN_USER);
		      stmt.setString(1, cipherTextValue(username));
		      stmt.setString(2, cipherTextValue(password));
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
					InvalidKeySpecException{
		
		String QUERY_USER_ROLE = "SELECT DISTINCT"+UserMetaData.USERS_ROLE+
							" FROM "+UserMetaData.USERS_TABLE+
							" WHERE "+UserMetaData.USERS_USERNAME+"= ? ";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_USER_ROLE);
		      stmt.setString(1, cipherTextValue(utente.getUsername()));
		      ResultSet rs = stmt.executeQuery();
		      if (rs.next())
					return decipherTextValues(rs.getString(UserMetaData.USERS_ROLE));
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
				InvalidKeySpecException{
		
		String QUERY_STUDENT = null;
		Student student = new Student();

		QUERY_STUDENT = "SELECT DISTINCT *"+
			" FROM "+UserMetaData.USERS_TABLE+
			" WHERE "+UserMetaData.USERS_USERNAME+"='"+username+"'";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_STUDENT);
		      stmt.setString(1, cipherTextValue(username));
		      ResultSet rs = stmt.executeQuery();
		      if(rs.next()){
					student.set_id(Integer.valueOf(decipherTextValues(String.valueOf(rs.getInt(StudentMetaData.STUDENTS_ID)))));
					student.setUsername(decipherTextValues(rs.getString(StudentMetaData.STUDENTS_USERNAME)));
					student.setName(decipherTextValues(rs.getString(StudentMetaData.STUDENTS_NAME)));
					student.setSurname(decipherTextValues(rs.getString(StudentMetaData.STUDENTS_SURNAME)));
					student.setStudentNumber(decipherTextValues(rs.getString(StudentMetaData.STUDENTS_NUMBER)));
					student.setDateOfBirth(decipherTextValues(rs.getString(StudentMetaData.STUDENTS_DATE_OF_BIRTH)));			
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
					InvalidKeySpecException{
		
		String QUERY_PROFESSOR = null;
		Professor professor = new Professor();

		QUERY_PROFESSOR = "SELECT DISTINCT *"+
			" FROM "+ProfessorMetaData.PROFESSORS_TABLE+
			" WHERE "+ProfessorMetaData.PROFESSORS_USERNAME+"='"+username+"'";
		
		PreparedStatement stmt = null;
		   try {
		      stmt = getConnection().prepareStatement(QUERY_PROFESSOR);
		      stmt.setString(1, cipherTextValue(username));
		      ResultSet rs = stmt.executeQuery();
		      if(rs.next()){
		    	  professor.set_id(Integer.valueOf(decipherTextValues(String.valueOf(rs.getInt(ProfessorMetaData.PROFESSORS_ID)))));
		    	  professor.setUsername(decipherTextValues(rs.getString(ProfessorMetaData.PROFESSORS_USERNAME)));
		    	  professor.setName(decipherTextValues(rs.getString(ProfessorMetaData.PROFESSORS_NAME)));
		    	  professor.setSurname(decipherTextValues(rs.getString(ProfessorMetaData.PROFESSORS_SURNAME)));
		    	  professor.setProfessorNumber(decipherTextValues(rs.getString(ProfessorMetaData.PROFESSORS_NUMBER)));
		    	  professor.setDateOfBirth(decipherTextValues(rs.getString(ProfessorMetaData.PROFESSORS_DATE_OF_BIRTH)));			
		    	  professor.setSubject(decipherTextValues(rs.getString(ProfessorMetaData.PROFESSORS_SUBJECT)));			

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
	
	
	private String cipherTextValue(String valuesToBeCrypted) 
			throws NoSuchAlgorithmException, 
			NoSuchPaddingException, 
			InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException,
			InvalidKeySpecException{
		
		/* Derive the key, given password and salt. */
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(
				rb.getString("properties.encryptKey").toCharArray(),
				SecureRandom.getSeed(8), 
				65536, 
				256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

		/* Encrypt the message. */
		Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aes.init(Cipher.ENCRYPT_MODE,secret);
		byte[] ciphertext = aes.doFinal(valuesToBeCrypted.getBytes());
		return new String(ciphertext);
	}
	
	private String decipherTextValues(String valuesToBeDecrypted)
			throws NoSuchAlgorithmException, 
			NoSuchPaddingException, 
			InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException,
			InvalidKeySpecException{
		/* Derive the key, given password and salt. */
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(
				rb.getString("properties.encryptKey").toCharArray(),
				SecureRandom.getSeed(8), 
				65536, 
				256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		
		/* Decrypt the message. */
		Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aes.init(Cipher.DECRYPT_MODE, secret);
		return new String(aes.doFinal(valuesToBeDecrypted.getBytes()));	
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection){
		this.connection = connection;
	}
}
