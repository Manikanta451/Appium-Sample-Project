package com.sp.utilities;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 
 * This Class is used to get the instance of properties from
 * application.properties file
 * 
 * 
 */

public class PropertiesFileReader {
	
	private static PropertiesFileReader INSTANCE = null;
	static Properties properties = new Properties();
	private static final String PROP_FILE= "application.properties";
	
	
	static InputStream in,input = null;
	static{
	
		try {
		    in = PropertiesFileReader.class.getResourceAsStream("/resources/"+PROP_FILE);
			properties.load(in);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private PropertiesFileReader(){

	}
	
	public static PropertiesFileReader getInstance() {
        if (INSTANCE==null) {
        	INSTANCE = new PropertiesFileReader();
        }
        return INSTANCE;
    }
	
	public static Properties readProperty(String name){
		
		try {
			if(null != name){
			in = properties.getClass().getResourceAsStream("/resources/"+name);
			
			properties.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}


	
	public static String getproperty(String key){
		String value = null;
		// True If Key Is Not Null And Not Empty
		if(null != key && !"".equalsIgnoreCase(key)){
			value = (String)properties.get(key);
		}
		return value;
	}

}