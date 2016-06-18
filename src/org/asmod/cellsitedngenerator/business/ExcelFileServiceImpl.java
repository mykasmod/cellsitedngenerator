package org.asmod.cellsitedngenerator.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.asmod.cellsitedngenerator.Constants;
import org.asmod.cellsitedngenerator.MainWindow;
import org.asmod.cellsitedngenerator.business.util.WorkbookFileUtil;
import org.asmod.cellsitedngenerator.business.util.WorksheetUtil;

public class ExcelFileServiceImpl implements ExcelFileService {
    final static Logger logger = Logger.getLogger(ExcelFileServiceImpl.class);
    private String assigneeName = null;

    public List<String> getSiteIdList(String filePath) {
	Workbook workBook = null;
	workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	List<String> siteIdList = WorksheetUtil.readWorksheetList(workBook, Constants.SITE_ID_CELL_INDEX,
		Constants.SITE_ASSIGNMENT_SHEET_INDEX);

	return siteIdList;

    }

    /*
     * Get 2G Combined BTS BCF Name and DN Map
     */
    public HashMap<String, String> get2GMap(String filePath, int dnCellIndex) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);
	HashMap<String, String> btsNameBTSDNMap = WorksheetUtil.readWorksheetMap(workBook,
		Constants.TWO_G_BCF_NAME_CELL_INDEX, dnCellIndex, Constants.TWO_G_SHEET_INDEX);
	return btsNameBTSDNMap;
    }

    /*
     * Get 3G WBTS DN Map
     */
    public HashMap<String, String> get3GMap(String filePath, boolean isCleanKey, boolean isCleanValue) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	HashMap<String, String> dnMap = WorksheetUtil.readWorksheetMap(workBook, Constants.THREE_G_WBTS_NAME_CELL_INDEX,
		Constants.THREE_G_DN_CELL_INDEX, Constants.THREE_G_SHEET_INDEX);

	HashMap<String, String> cleanDNMap = new HashMap<String, String>();
	if (isCleanKey) {
	    cleanDNMap = clean3GKey(dnMap);
	} else {
	    cleanDNMap = dnMap;
	}

	if (isCleanValue) {
	    return clean3GValue(cleanDNMap);
	} else {
	    return cleanDNMap;
	}

    }

    private HashMap<String, String> clean3GKey(HashMap<String, String> map) {

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

    private HashMap<String, String> clean3GValue(HashMap<String, String> map) {

	HashMap<String, String> cleanMap = new HashMap<String, String>();

	String strippedValue = null;
	int lastIndexOfSlash = 0;
	String value = null;
	for (Entry<String, String> entry : map.entrySet()) {
	    value = entry.getValue();
	    // key = entry.getKey();
	    if (value.length() > 0) { // stripped all lastIndexOfSlash
		lastIndexOfSlash = value.lastIndexOf('/');
		if (lastIndexOfSlash > 8) {
		    strippedValue = value.substring(0, lastIndexOfSlash);
		} else {
		    strippedValue = value;

		    /*MainWindow.setTextAreaLoggerText(
		        "Can't clean 3G value did not met [lastIndexOfSlash > 20], lastIndexOfSLash is "
		    	    + lastIndexOfSlash);
		    MainWindow.setTextAreaLoggerText("Can't clean 3G, value is " + value + " key is " + entry.getKey());*/
		}
	    } else {
		strippedValue = value;
		/*MainWindow.setTextAreaLoggerText(
			"Can't clean 3G value did not met [value.length() > 0], value length " + value.length());
		MainWindow.setTextAreaLoggerText("Can't clean 3G, value is " + value + " key is " + entry.getKey());*/
	    }
	    cleanMap.put(entry.getKey(), strippedValue);
	}
	return cleanMap;
    }

    /*
     * Get 4G Map
     * 
     */
    public HashMap<String, String> get4GMap(String filePath, boolean isCleanKey) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	HashMap<String, String> lncelDNMap = WorksheetUtil.readWorksheetMap(workBook,
		Constants.FOUR_G_LNCELL_NAME_INDEX, Constants.FOUR_G_LNCEL_DN_INDEX, Constants.FOUR_G_SHEET_INDEX);
	if (isCleanKey) {
	    return clean4GKey(lncelDNMap);
	} else {
	    return lncelDNMap;
	}

    }

    private HashMap<String, String> clean4GKey(HashMap<String, String> map) {

	HashMap<String, String> cleanMap = new HashMap<String, String>();
	String cleanKey = null;

	for (Entry<String, String> entry : map.entrySet()) {
	    String key = entry.getKey();
	    if (!key.equals(null) && key.length() > 8) {
		cleanKey = key.substring(1, 9);
		cleanMap.put(cleanKey, entry.getValue());
	    }
	}

	return cleanMap;
    }

    private HashMap<String, String> clean4GValue(HashMap<String, String> map) {

	HashMap<String, String> cleanMap = new HashMap<String, String>();

	String strippedValue = null;
	int lastIndexOfSlash = 0;
	String value = null;
	for (Entry<String, String> entry : map.entrySet()) {
	    value = entry.getValue();
	    if (value.length() > 0) { // stripped all lastIndexOfSlash
		lastIndexOfSlash = value.lastIndexOf('/');
		if (lastIndexOfSlash > 8) {
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
     * Get Merged DN List of all network Map
     */
    public List<String> getMergedDNList(Map<String, String> twoGMap, Map<String, String> twoGMap2,
	    Map<String, String> threeGMap, Map<String, String> threeGMap2, Map<String, String> threeGMap3,
	    Map<String, String> fourGMap, Map<String, String> fourGMap2, Map<String, String> fourGMap3,
	    List<String> siteIdList) {
	List<String> mergedDNList = new ArrayList<String>();
	mergedDNList.addAll(getDNListMatchedSiteID(twoGMap, siteIdList));
	MainWindow.setTextAreaLoggerText(twoGMap.size() + Constants.TWO_G_LOGMESSAGE + Constants.FIRST_RUN);
	mergedDNList.addAll(getDNListMatchedSiteID(twoGMap2, siteIdList));
	MainWindow.setTextAreaLoggerText(twoGMap2.size() + Constants.TWO_G_LOGMESSAGE + Constants.SECOND_RUN);
	mergedDNList.addAll(getDNListMatchedSiteID(threeGMap, siteIdList));
	MainWindow.setTextAreaLoggerText(threeGMap.size() + Constants.THREE_G_LOGMESSAGE + Constants.FIRST_RUN);
	mergedDNList.addAll(getDNListMatchedSiteID(threeGMap2, siteIdList));
	MainWindow.setTextAreaLoggerText(threeGMap2.size() + Constants.THREE_G_LOGMESSAGE + Constants.SECOND_RUN);
	mergedDNList.addAll(getDNListMatchedSiteID(threeGMap3, siteIdList));
	MainWindow.setTextAreaLoggerText(threeGMap3.size() + Constants.THREE_G_LOGMESSAGE + Constants.THIRD_RUN);
	mergedDNList.addAll(getDNListMatchedSiteID(fourGMap, siteIdList));
	MainWindow.setTextAreaLoggerText(fourGMap.size() + Constants.FOUR_G_LOGMESSAGE + Constants.FIRST_RUN);
	mergedDNList.addAll(getDNListMatchedSiteID(fourGMap2, siteIdList));
	MainWindow.setTextAreaLoggerText(fourGMap2.size() + Constants.FOUR_G_LOGMESSAGE + Constants.SECOND_RUN);
	mergedDNList.addAll(getDNListMatchedSiteID(fourGMap3, siteIdList));
	MainWindow.setTextAreaLoggerText(fourGMap3.size() + Constants.FOUR_G_LOGMESSAGE + Constants.THIRD_RUN);
	MainWindow.setTextAreaLoggerText(mergedDNList.size() + " Total Matched SiteId for " + assigneeName);
	return mergedDNList;
    }

    private List<String> getDNListMatchedSiteID(Map<String, String> gMap, List<String> siteIdList) {
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
	String outputFullFileName = filePath.substring(0, lastIndexOfSlash) + '\\' + getAssigneeName(filePath) + "."
		+ Constants.FILE_EXTENSION;
	return outputFullFileName;
    }

    private String getAssigneeName(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	String assigneeName = WorksheetUtil.getAssigneeName(workBook, Constants.ASSIGNEE_CELL_INDEX,
		Constants.SITE_ASSIGNMENT_SHEET_INDEX);

	return assigneeName;
    }

    public String generateDNFile(String assignmentFilePath, String marketSiteFilePath) {

	// Get output file name
	String outputFileName = null;

	// List
	List<String> siteIdList = getSiteIdList(assignmentFilePath);

	// Set member var assignee name
	assigneeName = getAssigneeName(assignmentFilePath);

	int increment = 1;

	// Map 2G
	// KEEP ALL
	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	Map<String, String> twoGMap = get2GMap(marketSiteFilePath, Constants.TWO_G_BTS_DN_CELL_INDEX);

	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	Map<String, String> twoGMap2 = get2GMap(marketSiteFilePath, Constants.TWO_G_BCF_DN_CELL_INDEX);

	// Map 3G
	// PLMN-PLMN/RNC-615/WBTS-13311/WCEL-1331112
	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	Map<String, String> threeGMap = get3GMap(marketSiteFilePath, true, false);

	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	HashMap<String, String> threeGMap2 = get3GMap(marketSiteFilePath, false, true);

	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	HashMap<String, String> threeGMap3 = clean3GValue(threeGMap2);

	// Map 4G
	// PLMN-PLMN/MRBTS-74341/LNBTS-74341/LNCEL-11
	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	HashMap<String, String> fourGMap = get4GMap(marketSiteFilePath, true);

	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	HashMap<String, String> fourGMap2 = clean4GValue(fourGMap);

	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	HashMap<String, String> fourGMap3 = clean4GValue(fourGMap2);

	// Merged DN List
	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	List<String> mergedDNList = getMergedDNList(twoGMap, twoGMap2, threeGMap, threeGMap2, threeGMap3, fourGMap,
		fourGMap2, fourGMap3, siteIdList);

	outputFileName = getOutputFileName(assignmentFilePath);

	// Save output file
	MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
	WorkbookFileUtil.createWorkbookToFilePath(mergedDNList, outputFileName);

	return outputFileName;
    }

}
