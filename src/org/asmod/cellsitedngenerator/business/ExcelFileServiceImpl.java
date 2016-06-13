package org.asmod.cellsitedngenerator.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Workbook;
import org.asmod.cellsitedngenerator.Constants;
import org.asmod.cellsitedngenerator.business.util.WorkbookFileUtil;
import org.asmod.cellsitedngenerator.business.util.WorksheetUtil;

public class ExcelFileServiceImpl implements ExcelFileService {

    @Override
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
    @Override
    public HashMap<String, String> getBTSBCFNameBTSBCFDNMap(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	HashMap<String, String> btsNameBTSDNMap = WorksheetUtil
		.readWorksheetMap(workBook, Constants.TWO_G_BTS_NAME_CELL_INDEX,
			Constants.TWO_G_BTS_DN_CELL_INDEX,
			Constants.TWO_G_SHEET_INDEX);

	HashMap<String, String> btsNameBTSDNMap2 = WorksheetUtil
		.readWorksheetMap(workBook, Constants.TWO_G_BCF_NAME_CELL_INDEX,
			Constants.TWO_G_BCF_DN_CELL_INDEX,
			Constants.TWO_G_SHEET_INDEX);

	btsNameBTSDNMap.putAll(btsNameBTSDNMap2);

	return btsNameBTSDNMap;
    }

    /*
     * Get 3G WBTS DN Map
     */
    @Override
    public HashMap<String, String> getWBTSDNMap(String filePath) {
	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	HashMap<String, String> wBTSDNMap = WorksheetUtil.readWorksheetMap(
		workBook, Constants.THREE_G_WBTS_NAME_CELL_INDEX,
		Constants.THREE_G_DN_CELL_INDEX, Constants.THREE_G_SHEET_INDEX);

	return cleanWBTSDNKey(wBTSDNMap);
    }

    private HashMap<String, String> cleanWBTSDNKey(
	    HashMap<String, String> map) {

	HashMap<String, String> cleanMap = new HashMap<String, String>();
	String cleanKey = null;

	for (Entry<String, String> entry : map.entrySet()) {
	    String key = entry.getKey();
	    if (!key.equals(null) && key.length() > 8) {
		cleanKey = key.substring(1);
		cleanMap.put(cleanKey, entry.getValue());
	    }
	}
	return cleanMap;
    }

    /*
     * Get 4G Map
     * 
     */
    @Override
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
	return cleanMap;
    }

    /*
     * Get Merged DN List of all network Map
     */
    @Override
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

}
