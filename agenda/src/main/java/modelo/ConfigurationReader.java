package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
	
	private static String databaseName;
	private static String serverTimeZone;
	private static String user;
	private static String password;
	
	private static InputStream inputStream;
	
	private ConfigurationReader() {
		
	}
	
	public static void loadConfiguration() {
		
		try {
			
			Properties properties = new Properties();
			String propFileName = "config.properties";
			
//			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			inputStream = ConfigurationReader.class.getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null)
				properties.load(inputStream);
			
			else
				throw new FileNotFoundException(String.format("Property file '%s' not found in the classpath", propFileName));
			
			databaseName = properties.getProperty("database");
			serverTimeZone = properties.getProperty("timezone");
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
	
	public static String getDatabaseName() {
		return databaseName;
	}
	
	public static String getServerTimezone() {
		return serverTimeZone;
	}
	
	public static String getUser() {
		return user;
	}
	
	public static String getPassword() {
		return password;
	}

}
