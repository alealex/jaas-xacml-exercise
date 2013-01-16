package it.security.example.servlet;

import it.security.example.database.MyDatabase;

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

/**
 * Servlet implementation class CheckUsernameServlet
 */
@WebServlet("/CheckUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUsernameServlet() {
        super();
        System.out.println("CheckUsernameServlet");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Niente GET, solo POST...");
		response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// USERNAME e' il parametro passato dal client nella richiesta Ajax 
		String targetUsername = request.getParameter("USERNAME");
		if(targetUsername == null || targetUsername.equals("")) {
			System.out.println("Manca il parametro USERNAME...");
			response.sendError(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		System.out.println("Il client ha mandato: " + targetUsername);
		
		try {
			MyDatabase db = new MyDatabase();
			db.createDatabaseConnection();
		
			StringBuffer xmlReply = new StringBuffer();
			xmlReply.append("<risposta>");
			xmlReply.append("<username>" + db.checkUsername(targetUsername) + "</username>");
			xmlReply.append("</risposta>");
			
			System.out.println(xmlReply.toString());
			response.setContentType("text/xml"); 
			response.setHeader("Cache-Control", "no-cache"); 
			response.getWriter().write(xmlReply.toString()); 	
			
		} catch (SQLException e) {
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}

}
