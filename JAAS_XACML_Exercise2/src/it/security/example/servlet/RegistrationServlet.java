package it.security.example.servlet;

import it.security.example.database.MyDatabase;
import it.security.example.metadata.RoleMetaData;
import it.security.example.model.Professor;
import it.security.example.model.Student;
import it.security.example.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Parametri di registrazione inviati: ");
		System.out.println("Memorizzo dati in db... ");
		
		try {
			saveUserIntoDB(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(response.encodeRedirectURL("/JAAS_XACML_Exercise2/public/login.jsp"));
	}
	
	public boolean saveUserIntoDB(HttpServletRequest request) throws SQLException, ClassNotFoundException{
		
		MyDatabase database = new MyDatabase();
		
		try {
			database.createDatabaseConnection();
			
			/*
			 * La username inserita non e' presente in DB 
			 * procedo con la registrazione:
			 * Procedo creando comunque l'oggetto USER:
			 */
			
			/* Setto l'oggetto User */
			User user = new User();
			Method[] userMethods = User.class.getMethods() ;
			for(Method metodo : userMethods) {			
				if(metodo.getName().startsWith("set"))
					try {

						System.out.println(metodo.getName().substring(3, 4).toLowerCase()+
								metodo.getName().substring(4)+": "+request.getParameter(			
										metodo.getName().substring(3).toUpperCase())
								);
						
						metodo.invoke(user, new Object[] { request.getParameter(			
								metodo.getName().substring(3).toUpperCase())
								});
					} catch(Exception e) { 
						e.printStackTrace(); 
					}
			}
			
			if (request.getParameter("ROLE").equalsIgnoreCase(RoleMetaData.ISSTUDENT)){
				
				/* Setto l'oggetto studente */
				Student student = new Student();
				Method[] studentMethods = Student.class.getMethods() ;
				for(Method metodo : studentMethods) {			
					if(metodo.getName().startsWith("set"))
						try {

							System.out.println(metodo.getName().substring(3, 4).toLowerCase()+
									metodo.getName().substring(4)+": "+request.getParameter(			
											metodo.getName().substring(3).toUpperCase()));
							
							metodo.invoke(student, new Object[] { request.getParameter(			
									metodo.getName().substring(3).toUpperCase())});
						} catch(Exception e) { 
							e.printStackTrace(); 
						}
				
				}	
					
					database.addUser(
						user.getUsername(),
						user.getPassword(), 
						user.getRole());
				
					database.addStudent(
							student.getUsername(),
							student.getName(), 
							student.getSurname(),
							student.getDate_Of_Birth(), 
							student.getStudent_Number());
					
					database.closeDatabaseConnection();
					return true;
					
			}else if(request.getParameter("ROLE").equalsIgnoreCase(RoleMetaData.ISPROFESSOR)){
				
				/* Setto l'oggetto professore */
				Professor professor = new Professor();
				Method[] professorMethods = Professor.class.getMethods() ;
				for(Method metodo : professorMethods) {			
					if(metodo.getName().startsWith("set"))
						try {

							System.out.println(metodo.getName().substring(3, 4).toLowerCase()+
									metodo.getName().substring(4)+": "+request.getParameter(
											metodo.getName().substring(3).toUpperCase()));
							
							metodo.invoke(professor, new Object[] { request.getParameter(			
									metodo.getName().substring(3).toUpperCase())});
						} catch(Exception e) { 
							e.printStackTrace(); 
						}
					}
				
					database.addUser(
						user.getUsername(),
						user.getPassword(), 
						user.getRole());
			
				database.addProfessor(
						professor.getUsername(),
						professor.getName(),
						professor.getSurname(),
						professor.getDate_Of_Birth(),
						professor.getProfessor_Number(), 
						professor.getSubject());
				database.closeDatabaseConnection();
				return true;
			}else{
				database.closeDatabaseConnection();
				return false;
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			return false;
		} catch (BadPaddingException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		}finally{
			database.closeDatabaseConnection();
		}
	}
}
