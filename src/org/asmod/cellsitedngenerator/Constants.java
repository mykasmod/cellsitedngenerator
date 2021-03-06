package org.asmod.cellsitedngenerator;

import org.apache.log4j.Logger;

public interface Constants {
    final static Logger logger = Logger.getLogger(Constants.class);

   /* 
    #NO NEED WERE USING CONFIG.PROPERTIES
    
    public static int SITE_ASSIGNMENT_SHEET_INDEX = 0;
    public static int SITE_ID_CELL_INDEX = 3;

    public static int TWO_G_SHEET_INDEX = 2;
    public static int THREE_G_SHEET_INDEX = 1;
    public static int FOUR_G_SHEET_INDEX = 0;

    public static int TWO_G_BCF_NAME_CELL_INDEX = 4;
    public static int TWO_G_BTS_DN_CELL_INDEX = 0;
    public static int TWO_G_BCF_DN_CELL_INDEX = 3;


    public static int THREE_G_WCELL_NAME_CELL_INDEX = 0;
    public static int THREE_G_DN_CELL_INDEX = 1;

    public static int FOUR_G_LNCELL_NAME_INDEX = 0;
    public static int FOUR_G_LNCEL_DN_INDEX = 2;

    public static int ASSIGNEE_CELL_INDEX = 5;*/
    
    public static String FILE_EXTENSION = ".xls";

    public static String MARKET = "market";
    public static String ASSIGNMENT = "assignment";

    public static String SAMPLE_FILEPATH_ASSIGNMENT = "C:\\Users\\myk\\Downloads\\attachments_2016_06_11\\SiteAssignmentExport (13).xls";
    public static String SAMPLE_FILEPATH_MARKET_SITE = "C:\\Users\\myk\\Downloads\\attachments_2016_06_11\\2G_3G_4G_MarketSiteInfo_06 10 16.xls";
    public static String SAMPLE_FILEPATH_ASSIGNMENT_OUTPUT = "C:\\Users\\myk\\Downloads\\attachments_2016_06_11\\2-Output.xls";

    public static String TWO_G_LOGMESSAGE = " DN Collected in 2G";
    public static String THREE_G_LOGMESSAGE = " DN Collected in 3G";
    public static String FOUR_G_LOGMESSAGE = " DN Collected in 4G";

    public static String FIRST_RUN = " at First Run";
    public static String SECOND_RUN = " at Second Run";
    public static String THIRD_RUN = " at Third Run";

    public static String INVALID_EXTENSION_MESSAGE = "Can't proceed with Invalid Extension. Only \".xls\" is allowed. Open Excel file and Save As... \"Excel 97-2003\"";
}
