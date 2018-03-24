package org.asmod.cellsitedngenerator.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.asmod.cellsitedngenerator.MainWindow;

public class CreateConfigProperties implements org.asmod.cellsitedngenerator.io.ConstantsConfigPropertiesKeys {
    final static Logger logger = Logger.getLogger(CreateConfigProperties.class);

    public static void createFile() {
	Properties prop = new Properties();
	OutputStream output = null;

	try {

	    output = new FileOutputStream("config.properties.txt");

	    // set the properties value

	    prop.setProperty(SITE_ASSIGNMENT_SHEET_INDEX, ZERO);
	    prop.setProperty(SITE_ID_CELL_INDEX, THREE);

	    prop.setProperty(TWO_G_SHEET_INDEX, TWO);
	    prop.setProperty(THREE_G_SHEET_INDEX, ONE);
	    prop.setProperty(FOUR_G_SHEET_INDEX, ZERO);

	    prop.setProperty(TWO_G_BCF_NAME_CELL_INDEX, FOUR);
	    prop.setProperty(TWO_G_BTS_DN_CELL_INDEX, ZERO);
	    prop.setProperty(TWO_G_BCF_DN_CELL_INDEX, THREE);

	    prop.setProperty(THREE_G_WCELL_NAME_CELL_INDEX, ZERO);
	    prop.setProperty(THREE_G_DN_CELL_INDEX, ONE);

	    prop.setProperty(FOUR_G_LNCELL_NAME_INDEX, ZERO);
	    prop.setProperty(FOUR_G_LNCEL_DN_INDEX, TWO);

	    prop.setProperty(ASSIGNEE_CELL_INDEX, FIVE);

	    // save properties to project root folder
	    prop.store(output, null);
	    logger.info("config.properties created!");

	} catch (IOException io) {
	    logger.error(io.getMessage());
	} finally {
	    if (output != null) {
		try {
		    output.close();
		} catch (IOException e) {
		    logger.error(e.getMessage());
		}
	    }

	}
    }

}
