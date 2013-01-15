package it.security.xacml.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


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
	
	public String getUserRole(Utente utente) 
			throws SQLException{
		
		String QUERY_USER = "SELECT "+UtenteMetaData.UTENTI_RUOLO+
							" FROM "+UtenteMetaData.UTENTI_TABLE+
							" WHERE "+UtenteMetaData.UTENTI_USERNAME+"='"+utente.getUsername()+
							"' AND "+UtenteMetaData.UTENTI_PASSWORD+"='"+utente.getPassword()+"'";
		
		Statement stm = getConnection().createStatement();
		ResultSet rs = stm.executeQuery(QUERY_USER);
		if (rs.next())
			return rs.getString(UtenteMetaData.UTENTI_RUOLO);
		else return null;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection){
		this.connection = connection;
	}
}
