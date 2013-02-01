package it.security.example.servlet;

import it.security.example.database.MyDatabase;
import it.security.example.jaas.JaasAuthentication;
import it.security.example.metadata.ProfessorMetaData;
import it.security.example.metadata.RoleMetaData;
import it.security.example.metadata.StudentMetaData;
import it.security.example.metadata.UserMetaData;
import it.security.example.model.Professor;
import it.security.example.model.Student;
import it.security.example.model.User;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JaasAuthentication jaasAuthentication= new JaasAuthentication(
				request.getParameter(UserMetaData.USERS_USERNAME), 
				request.getParameter(UserMetaData.USERS_PASSWORD));
		
		 Subject subject = jaasAuthentication.tryLogin();
		
		 String[] hashMapKeys = new String[]{
				 "dbUrl",
				 "dbUserName",
				 "dbPassword",
				 "dbDriver",
				 "encryptKey"
		 };
		 HashMap<String,String> dbOptions = new HashMap<String,String>();
		 if(subject!=null){
			
			Iterator<Principal> iterator=subject.getPrincipals().iterator();
			System.out.println("Ho trovato: " + subject.getPrincipals().size());
			int i=0;
			/*
			 * CREO L'HASHMAP DA PASSARE AL DB:
			 */
			User loggedUser=new User();
			while(iterator.hasNext()){
				String value = iterator.next().getName();
				System.out.println("Posizione ->"+i+"" +value);	
				
				if(i==0) loggedUser.setRole(value);
				if(i==6) loggedUser.setUsername(value);
				else if(i>0 && i<6) {dbOptions.put(hashMapKeys[i-1], value);
					System.out.println("HASH_MAP VALUE ->"+hashMapKeys[i-1]+"<-:"
								+dbOptions.get(hashMapKeys[i-1]));
				}
				i++;
			}

			MyDatabase database;
			try {
				database = new MyDatabase (dbOptions);
				database.createDatabaseConnection();
				if(loggedUser.getRole().toLowerCase().equals(RoleMetaData.ISPROFESSOR)){
					System.out.println("L'utente e' un professore con USERNAME: " + loggedUser.getUsername());
					Professor professor=new Professor();
					professor=database.getProfessor(loggedUser.getUsername());
					HttpSession session=request.getSession();
					session.setAttribute(ProfessorMetaData.PROFESSORS_USERNAME, professor.getUsername());
					session.setAttribute(ProfessorMetaData.PROFESSORS_NAME, professor.getName());
					session.setAttribute(ProfessorMetaData.PROFESSORS_SURNAME, professor.getSurname());
					session.setAttribute(ProfessorMetaData.PROFESSORS_DATE_OF_BIRTH, professor.getDate_Of_Birth());
					session.setAttribute(ProfessorMetaData.PROFESSORS_NUMBER, professor.getProfessor_Number());
					session.setAttribute(ProfessorMetaData.PROFESSORS_SUBJECT, professor.getSubject());
					session.setAttribute(UserMetaData.USERS_ROLE, RoleMetaData.ISPROFESSOR);					
					session.setAttribute("LoginContext", JaasAuthentication.getLCinstance());
					response.sendRedirect(response.encodeRedirectURL("/JAAS_XACML_Exercise2/professors/professors.jsp"));
				}else if(loggedUser.getRole().toLowerCase().equals(RoleMetaData.ISSTUDENT)){
					System.out.println("L'utente e' uno studente con USERNAME: " + loggedUser.getUsername());
					Student student=new Student();
					student=database.getStudent(loggedUser.getUsername());
					HttpSession session=request.getSession();
					session.setAttribute(StudentMetaData.STUDENTS_USERNAME, student.getUsername());
					session.setAttribute(StudentMetaData.STUDENTS_NAME, student.getName());
					session.setAttribute(StudentMetaData.STUDENTS_SURNAME, student.getSurname());
					session.setAttribute(StudentMetaData.STUDENTS_NUMBER, student.getStudent_Number());
					session.setAttribute(StudentMetaData.STUDENTS_DATE_OF_BIRTH, student.getDate_Of_Birth());			
					session.setAttribute(UserMetaData.USERS_ROLE, RoleMetaData.ISSTUDENT);					
					session.setAttribute("LoginContext", JaasAuthentication.getLCinstance());
					response.sendRedirect(response.encodeRedirectURL("/JAAS_XACML_Exercise2/students/students.jsp"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
		
		}
		else{
			System.out.println("The username or the password are not correct. Please check your data and try again the login !!!");
			response.sendRedirect(response.encodeRedirectURL("/JAAS_XACML_Exercise2/public/login.jsp"));
		}
	}

}
