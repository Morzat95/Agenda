package persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import modelo.ConfigurationReader;

public class Conexion 
{
	public static Conexion instancia;
	private Connection connection;
	private Logger log = Logger.getLogger(Conexion.class);
	
	private String databaseName = "";
	private String serverTimezone = "";
	private String user = "";
	private String password = "";
	
	private Conexion()
	{
		try
		{
//			Class.forName("com.mysql.jdbc.Driver"); // quitar si no es necesario
			Class.forName("com.mysql.cj.jdbc.Driver"); // quitar si no es necesario
//			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grupo_G14?serverTimezone=UTC","root","root");
			
			loadConnectionData();
			System.out.println(String.format("Trying to connect to '%s' with user '%s' and password '%s'.", databaseName, user, password));
			this.connection = DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=%s", databaseName, serverTimezone), user, password);
			
			this.connection.setAutoCommit(false);
			log.info("Conexión exitosa");
		}
		catch(Exception e)
		{
			log.error("Conexión fallida", e);
		}
	}
	
	public static Conexion getConexion()   
	{								
		if(instancia == null)
		{
			instancia = new Conexion();
		}
		return instancia;
	}

	public Connection getSQLConexion() 
	{
		return this.connection;
	}
	
	public void cerrarConexion()
	{
		try 
		{
			this.connection.close();
			log.info("Conexion cerrada");
		}
		catch (SQLException e) 
		{
			log.error("Error al cerrar la conexión!", e);
		}
		instancia = null;
	}
	
	private void loadConnectionData() {
		ConfigurationReader.loadConfiguration();
		
		this.databaseName = ConfigurationReader.getDatabaseName();
		this.serverTimezone = ConfigurationReader.getServerTimezone();
		this.user = ConfigurationReader.getUser();
		this.password = ConfigurationReader.getPassword();
	}
}
