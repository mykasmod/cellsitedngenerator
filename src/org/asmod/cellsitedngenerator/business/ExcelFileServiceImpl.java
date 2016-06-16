package org.asmod.cellsitedngenerator.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Workbook;
import org.asmod.cellsitedngenerator.Constants;
import org.asmod.cellsitedngenerator.MainWindow;
import org.asmod.cellsitedngenerator.business.util.WorkbookFileUtil;
import org.asmod.cellsitedngenerator.business.util.WorksheetUtil;

public class ExcelFileServiceImpl implements ExcelFileService {

    public List<String> getSiteIdList(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	List<String> siteIdList = WorksheetUtil.readWorksheetList(workBook,
		Constants.SITE_ID_CELL_INDEX,
		Constants.SITE_ASSIGNMENT_SHEET_INDEX);

	return siteIdList;

    }

    /*
     * Get 2G Combined BTS BCF Name and DN Map
     */
    public HashMap<String, String> getBTSBCFNameBTSBCFDNMap(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	Integer size = 0;
	String logMessage = null;

	HashMap<String, String> btsNameBTSDNMap = WorksheetUtil
		.readWorksheetMap(workBook, Constants.TWO_G_BCF_NAME_CELL_INDEX,
			Constants.TWO_G_BTS_DN_CELL_INDEX,
			Constants.TWO_G_SHEET_INDEX);
	// logging
	size = btsNameBTSDNMap.size();
	logMessage = size + Constants.TWO_G_LOGMESSAGE + " at 1st Run";

	MainWindow.setTextAreaLoggerText(logMessage);

	HashMap<String, String> btsNameBTSDNMap2 = WorksheetUtil
		.readWorksheetMap(workBook, Constants.TWO_G_BCF_NAME_CELL_INDEX,
			Constants.TWO_G_BCF_DN_CELL_INDEX,
			Constants.TWO_G_SHEET_INDEX);
	// logging
	size = btsNameBTSDNMap2.size();
	logMessage = size + Constants.TWO_G_LOGMESSAGE + " at 2nd Run";
	MainWindow.setTextAreaLoggerText(logMessage);

	btsNameBTSDNMap.putAll(btsNameBTSDNMap2);

	return btsNameBTSDNMap;
    }

    /*
     * Get 3G WBTS DN Map
     */
    public HashMap<String, String> getWBTSDNMap(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	HashMap<String, String> wBTSDNMap = WorksheetUtil.readWorksheetMap(
		workBook, Constants.THREE_G_WBTS_NAME_CELL_INDEX,
		Constants.THREE_G_DN_CELL_INDEX, Constants.THREE_G_SHEET_INDEX);

	// logging
	Integer size = 0;
	size = wBTSDNMap.size();
	String logMessage = size + Constants.THREE_G_LOGMESSAGE;
	MainWindow.setTextAreaLoggerText(logMessage);

	HashMap<String, String> cleanwBTSDNMap = new HashMap<String, String>();
	cleanwBTSDNMap = cleanWBTSDNKey(wBTSDNMap);
	return cleanWBTSDNValue(cleanwBTSDNMap);
    }

    private HashMap<String, String> cleanWBTSDNKey(
	    HashMap<String, String> map) {

	HashMap<String, String> cleanMap = new HashMap<String, String>();
	String cleanKey = null;

	for (Entry<String, String> entry : map.entrySet()) {
	    String key = entry.getKey();
	    if (!key.equals(null) && key.length() > 8) {
		cleanKey = key.substring(1);
	    } else {
		cleanKey = key;
	    }
	    cleanMap.put(cleanKey, entry.getValue());
	}
	return cleanMap;
    }

    private HashMap<String, String> cleanWBTSDNValue(
	    HashMap<String, String> map) {
	HashMap<String, String> cleanMap = new HashMap<String, String>();

	String strippedValue = null;
	int lastIndexOfSlash = 0;
	String value = null;
	for (Entry<String, String> entry : map.entrySet()) {
	    value = entry.getValue();
	    if (value.length() > 0) { // stripped all lastIndexOfSlash
		lastIndexOfSlash = value.lastIndexOf('/');
		if (lastIndexOfSlash > 20) {
		    strippedValue = value.substring(0, lastIndexOfSlash);
		} else {
		    strippedValue = value;
		}

	    } else {
		strippedValue = value;
	    }
	    cleanMap.put(entry.getKey(), strippedValue);
	}
	return cleanMap;
    }

    /*
     * Get 4G Map
     * 
     */
    public HashMap<String, String> getLNCELDNMap(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	HashMap<String, String> lncelDNMap = WorksheetUtil.readWorksheetMap(
		workBook, Constants.FOUR_G_LNCELL_NAME_INDEX,
		Constants.FOUR_G_LNCEL_DN_INDEX, Constants.FOUR_G_SHEET_INDEX);

	return cleanLNCELDNKey(lncelDNMap);
    }

    private HashMap<String, String> cleanLNCELDNKey(
	    HashMap<String, String> map) {

	HashMap<String, String> cleanMap = new HashMap<String, String>();
	String cleanKey = null;

	for (Entry<String, String> entry : map.entrySet()) {
	    String key = entry.getKey();
	    if (!key.equals(null) && key.length() > 8) {
		cleanKey = key.substring(1, 9);
		cleanMap.put(cleanKey, entry.getValue());
	    }
	}

	// logging
	List<String> files = new ArrayList<String>();
	Integer size = 0;
	files.clear();
	size = cleanMap.size();
	String logMessage = size + Constants.FOUR_G_LOGMESSAGE;

	MainWindow.setTextAreaLoggerText(logMessage);

	return cleanMap;
    }

    /*
     * Get Merged DN List of all network Map
     */
    public List<String> getMergedDNList(Map<String, String> twoGMap,
	    Map<String, String> threeGMap, Map<String, String> fourGMap,
	    List<String> siteIdList) {
	List<String> mergedDNList = new ArrayList<String>();
	mergedDNList.addAll(getDNListMatchedSiteID(twoGMap, siteIdList));
	mergedDNList.addAll(getDNListMatchedSiteID(threeGMap, siteIdList));
	mergedDNList.addAll(getDNListMatchedSiteID(fourGMap, siteIdList));

	return mergedDNList;
    }

    private List<String> getDNListMatchedSiteID(Map<String, String> gMap,
	    List<String> siteIdList) {
	List<String> dnList = new ArrayList<String>();
	for (String siteId : siteIdList) {
	    if (gMap.containsKey(siteId)) {
		dnList.add(gMap.get(siteId));
	    }
	}

	return dnList;
    }

    /*
     * Get OutputFileName
     */
    public String getOutputFileName(String filePath) {
	int lastIndexOfSlash = filePath.lastIndexOf('\\');
	String outputFullFileName = filePath.substring(0, lastIndexOfSlash)
		+ '\\' + getAssigneeName(filePath) + "."
		+ Constants.FILE_EXTENSION;
	return outputFullFileName;
    }

    private String getAssigneeName(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	String assigneeName = WorksheetUtil.getAssigneeName(workBook,
		Constants.ASSIGNEE_CELL_INDEX,
		Constants.SITE_ASSIGNMENT_SHEET_INDEX);

	return assigneeName;
    }

    public String generateDNFile(String assignmentFilePath,
	    String marketSiteFilePath) {

	// List
	List<String> siteIdList = getSiteIdList(assignmentFilePath);

	// Map 2G
	Map<String, String> twoGMap = getBTSBCFNameBTSBCFDNMap(
		marketSiteFilePath);
	// Map 3G
	Map<String, String> threeGMap = getWBTSDNMap(marketSiteFilePath);

	// Map 4G
	Map<String, String> fourGMap = getLNCELDNMap(marketSiteFilePath);

	// Merged DN List
	List<String> mergedDNList = getMergedDNList(twoGMap, threeGMap,
		fourGMap, siteIdList);

	// Get output file name
	String outputFileName = getOutputFileName(assignmentFilePath);

	// Save output file
	WorkbookFileUtil.createWorkbookToFilePath(mergedDNList, outputFileName);

	return outputFileName;
    }

}
