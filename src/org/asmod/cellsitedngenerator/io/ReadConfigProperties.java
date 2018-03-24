package org.asmod.cellsitedngenerator.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Properties;

public class ReadConfigProperties {
    final static Logger logger = Logger.getLogger(ReadConfigProperties.class);
    
    public static void main(String args[]) {
	readFile();
    }
     
     @SuppressWarnings("unchecked")
    public static void readFile() {
	 Properties prop = new Properties();
	 InputStream input = null;
	    
	try {
	    input = new FileInputStream("config.properties.txt");
	    prop.load(input);
	    
	    HashMap<String, Integer> configPropertiesMap = new HashMap<String, Integer>();
	    // get the property value and print it out
	    @SuppressWarnings("rawtypes")
	    Map<String, String> propMap = (Map) prop;
	    for (Entry<String, String> entry : propMap.entrySet()) {
		System.out.println(entry.getKey() + "=" + entry.getValue());
		configPropertiesMap.put(entry.getKey(), Integer.parseInt(entry.getValue()));
	    }
	    ConfigPropertiesService.setConfigPropertiesMap(configPropertiesMap);    
	} catch (IOException ex) {
	    logger.info(ex.getMessage());
	    CreateConfigProperties.createFile();	    
	} finally {
	    if (input != null) {
		try {
        		input.close();
        	    } catch (IOException e) {
        		logger.error(e.getMessage());        		
        	    }
	    }
	}

    }
}
