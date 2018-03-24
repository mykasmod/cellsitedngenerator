package org.asmod.cellsitedngenerator.io;

import java.util.HashMap;

public class ConfigPropertiesService {
    //stores the config properties map during read file
    private static HashMap<String, Integer> configPropertiesMap = new HashMap<String, Integer>();
    
  
    /**
     * @return the configPropertiesMap
     */
    public static HashMap<String, Integer> getConfigPropertiesMap() {
        return configPropertiesMap;
    }

    /**
     * @param configPropertiesMap the configPropertiesMap to set
     */
    public static void setConfigPropertiesMap(HashMap<String, Integer> configProperties) {
        configPropertiesMap = configProperties;
    }
    
  
    public static void initConfigProperties () {
	ReadConfigProperties.readFile(); //sets configPropertiesMap
	setConfigPropertiesFields();
	//Use in ExcelFileService

    }
    
    //Sets the Object property value; so that property easily fetched from object
    private static void setConfigPropertiesFields() {
	ConfigProperties configProperties = new ConfigProperties(); //int use in
	
	configProperties.setAssignee_Cell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.ASSIGNEE_CELL_INDEX));
	configProperties.setSiteIdCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.ASSIGNEE_CELL_INDEX));
	
	configProperties.setTwoGSheet(configPropertiesMap.get(ConstantsConfigPropertiesKeys.TWO_G_SHEET_INDEX));
	configProperties.setThreeGSheet(configPropertiesMap.get(ConstantsConfigPropertiesKeys.THREE_G_SHEET_INDEX));
	configProperties.setFourGSheet(configPropertiesMap.get(ConstantsConfigPropertiesKeys.FOUR_G_SHEET_INDEX));
	
	configProperties.setTwoGBCFNameCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.TWO_G_BCF_NAME_CELL_INDEX));
	configProperties.setTwoGBTSDNCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.TWO_G_BTS_DN_CELL_INDEX));
	configProperties.setTwoGBCFDNCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.TWO_G_BCF_DN_CELL_INDEX));
	
	configProperties.setThreeGWCellNameCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.THREE_G_WCELL_NAME_CELL_INDEX));
	configProperties.setThreeGDNCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.THREE_G_DN_CELL_INDEX));
	
	configProperties.setFourGLNCellNameCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.FOUR_G_LNCELL_NAME_INDEX));
	configProperties.setFourGLNCelDNCell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.FOUR_G_LNCEL_DN_INDEX));
	
	configProperties.setAssignee_Cell(configPropertiesMap.get(ConstantsConfigPropertiesKeys.ASSIGNEE_CELL_INDEX));
	
    }
}


