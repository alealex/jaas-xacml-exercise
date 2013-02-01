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

		JaasAuthentication jaasAuthentication= new JaasAuthentication(request.getParameter(UserMetaData.USERS_USERNAME), 
				request.getParameter(UserMetaData.USERS_PASSWORD));
		
		Boolean esitoLogin = jaasAuthentication.tryLogin();
		if(esitoLogin){
			User loggedUser=new User();
			loggedUser.setUsername(request.getParameter(UserMetaData.USERS_USERNAME));
			loggedUser.setPassword(request.getParameter(UserMetaData.USERS_PASSWORD));
			try {
				MyDatabase database=new MyDatabase();
				database.createDatabaseConnection();
				System.out.println("Il ruolo dell'utente e': "+database.getUserRole(loggedUser));
				
				if(database.getUserRole(loggedUser).toLowerCase().equals(RoleMetaData.ISPROFESSOR)){
					System.out.println("L'utente e' un professore");
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
					response.sendRedirect(response.encodeRedirectUrl("/JAAS_XACML_Exercise2/professors/professors.jsp"));
				}
				else if(database.getUserRole(loggedUser).toLowerCase().equals(RoleMetaData.ISSTUDENT)){
					System.out.println("L'utente e' uno studente");
					Student student=new Student();
					student=database.getStudent(loggedUser.getUsername());
					HttpSession session=request.getSession();
					session.setAttribute(StudentMetaData.STUDENTS_USERNAME, student.getUsername());
					session.setAttribute(StudentMetaData.STUDENTS_NAME, student.getName());
					session.setAttribute(StudentMetaData.STUDENTS_SURNAME, student.getSurname());
					session.setAttribute(StudentMetaData.STUDENTS_NUMBER, student.getStudent_Number());
					session.setAttribute(StudentMetaData.STUDENTS_DATE_OF_BIRTH, student.getDate_Of_Birth());			
					session.setAttribute(UserMetaData.USERS_ROLE, RoleMetaData.ISSTUDENT);					
					response.sendRedirect(response.encodeRedirectUrl("/JAAS_XACML_Exercise2/students/students.jsp"));
				}
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("L'utente nunn e nisciun auhauhau");
			response.sendRedirect(response.encodeRedirectUrl("/JAAS_XACML_Exercise2/public/login.jsp"));
		}
	}

}
