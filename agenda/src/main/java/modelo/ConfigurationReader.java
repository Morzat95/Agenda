package modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
	
	private static InputStream inputStream;
//	private static FileInputStream inputStream;
	private static FileOutputStream outputStream;
	private static Properties properties = new Properties();
	
	private static String propFileName = "config.properties";
	public static ConfigurationReader instance;
	
	private ConfigurationReader() {
		
	}
	
	public static void loadConfiguration() {
		
		try {
			
//			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//			inputStream = ConfigurationReader.class.getClassLoader().getResourceAsStream(propFileName);
			inputStream = new FileInputStream("src/main/resources/" + propFileName);
			
			if (inputStream != null)
				properties.load(inputStream);
			
			else
				throw new FileNotFoundException(String.format("Property file '%s' not found in the classpath", propFileName));
			
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
	
	public static void saveData(String dbName, String timeZone, String usr, String pass) {
		
		try {
			
			properties.setProperty("database", dbName);
			properties.setProperty("timezone", timeZone);
			properties.setProperty("user", usr);
			properties.setProperty("password", pass);
			
//			databaseName = dbName;
//			serverTimezone = timeZone;
//			user = usr;
//			password = pass;
			
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
	
	public static String getDatabaseName() {
		return properties.getProperty("database");
	}
	
	public static String getServerTimezone() {
		return properties.getProperty("timezone");
	}
	
	public static String getUser() {
		return properties.getProperty("user");
	}
	
	public static String getPassword() {
		return properties.getProperty("password");
	}

}
