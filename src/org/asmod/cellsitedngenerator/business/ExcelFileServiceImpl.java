package org.asmod.cellsitedngenerator.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Workbook;
import org.asmod.cellsitedngenerator.Constants;
import org.asmod.cellsitedngenerator.business.util.WorkbookFileUtil;
import org.asmod.cellsitedngenerator.business.util.WorksheetUtil;

public class ExcelFileServiceImpl implements ExcelFileService {

    @Override
    public List<String> getSiteIdList(String filePath) {
	// HSSFWorkbook workBook =
	// WorkbookFileUtil.getWorkBookFromFilePath(filePath);
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
	// HSSFWorkbook workBook =
	// WorkbookFileUtil.getWorkBookFromFilePath(filePath);

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

	return cleanWBTSKey(wBTSDNMap);
    }

    private HashMap<String, String> cleanWBTSKey(HashMap<String, String> map) {

	HashMap<String, String> cleanMap = new HashMap<String, String>();
	String cleanKey = null;

	for (Entry<String, String> entry : map.entrySet()) {
	    // .substring(1);
	    String key = entry.getKey();
	    if (!key.equals(null) && key.length() > 8) {
		cleanKey = key.substring(1);
		cleanMap.put(cleanKey, entry.getValue());
	    }
	}
	return cleanMap;
    }

}
