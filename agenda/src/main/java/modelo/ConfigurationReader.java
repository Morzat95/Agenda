package modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
	
	private static InputStream inputStream;
	private static FileOutputStream outputStream;
	private static Properties properties = new Properties();
	
	private static String propFileName = "config.properties";
	
	private String databaseName;
	private String serverTimezone;
	private String user;
	private String password;
	
	public static ConfigurationReader instance;
	
	private ConfigurationReader() {
		
	}
	
	public static ConfigurationReader getInstance() {
		if (instance == null)
			instance = new ConfigurationReader();
		return instance;
	}
	
	public void loadConfiguration() {
		
		try {
			
//			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			inputStream = ConfigurationReader.class.getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null)
				properties.load(inputStream);
			
			else
				throw new FileNotFoundException(String.format("Property file '%s' not found in the classpath", propFileName));
			
			System.out.println("Loading properties: " + properties);
			
			databaseName = properties.getProperty("database");
			serverTimezone = properties.getProperty("timezone");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			
		} catch (Exception e) {
			
			System.out.println("Exception: " + e);
			
		} finally {
			
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void saveData(String dbName, String timeZone, String usr, String pass) {
		
		try {
			
			properties.setProperty("database", dbName);
			properties.setProperty("timezone", timeZone);
			properties.setProperty("user", usr);
			properties.setProperty("password", pass);
			
			databaseName = dbName;
			serverTimezone = timeZone;
			user = usr;
			password = pass;
			
			outputStream = new FileOutputStream("src/main/resources/" + propFileName);
			properties.store(outputStream, null);
			
			outputStream.close();
			
			System.out.println("Saving properties: " + properties);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			if (outputStream != null)
				
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			outputStream = null;
			
		}
		
	}
	
	public String getDatabaseName() {
//		return properties.getProperty("database");
		return databaseName;
	}
	
	public String getServerTimezone() {
//		return properties.getProperty("timezone");
		return serverTimezone;
	}
	
	public String getUser() {
//		return properties.getProperty("user");
		return user;
	}
	
	public String getPassword() {
//		return properties.getProperty("password");
		return password;
	}

}
