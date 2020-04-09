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
	
	private ConfigurationReader() {
		
	}
	
	public static void loadConfiguration() {
		
		try {
			
//			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			inputStream = ConfigurationReader.class.getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null)
				properties.load(inputStream);
			
			else
				throw new FileNotFoundException(String.format("Property file '%s' not found in the classpath", propFileName));
			
			System.out.println("Loading properties: " + properties);
			
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
			
			outputStream = new FileOutputStream("src/main/resources/" + propFileName);
			properties.store(outputStream, null);
			
			System.out.println("Saving properties: " + properties);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
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
