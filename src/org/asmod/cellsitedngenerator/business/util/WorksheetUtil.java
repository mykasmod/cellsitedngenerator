package org.asmod.cellsitedngenerator.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorksheetUtil {
    private WorksheetUtil() {
    }

    /*
     * Save a cell value of specific cell index to a List. \n We're expecting
     * only one sheet in an excel file
     * 
     * @param workbook
     * 
     * @param cellIndexToSave
     */
    public static List<String> readWorksheetList(Workbook workBook,
	    int cellIndexToSave, int sheetIndex) {

	Sheet sheet = workBook.getSheetAt(sheetIndex);
	String stringCellValue = null;
	List<String> siteIdList = new ArrayList<String>();

	for (Row row : sheet) {
	    int cellIndexCount = 0;
	    for (Cell cell : row) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
		    stringCellValue = cell.getRichStringCellValue().getString();
		    break;
		case Cell.CELL_TYPE_NUMERIC:
		    if (DateUtil.isCellDateFormatted(cell)) {
			stringCellValue = cell.getDateCellValue().toString();

		    } else {
			stringCellValue = Double
				.toString(cell.getNumericCellValue());
		    }
		    break;
		case Cell.CELL_TYPE_BOOLEAN:
		    stringCellValue = Boolean
			    .toString(cell.getBooleanCellValue());

		    break;
		case Cell.CELL_TYPE_FORMULA:
		    stringCellValue = cell.getCellFormula().toString();

		    break;
		default:
		}

		if (cellIndexCount == cellIndexToSave) {
		    siteIdList.add(stringCellValue);
		}
		cellIndexCount++;

	    }
	}

	return cleanSiteIdList(siteIdList);
    }

    private static List<String> cleanSiteIdList(List<String> siteidList) {
	siteidList.remove(siteidList.indexOf("SiteID"));
	return siteidList;
    }

    /*
     * Reads a String, DNModel Map
     * 
     * @param btsNameIndex
     * 
     * @param btsDNIndex
     * 
     * @param sheetIndex
     */
    public static HashMap<String, String> readWorksheetMap(Workbook workBook,
	    int btsNameIndex, int btsDNIndex, int sheetIndex) {

	Sheet sheet = workBook.getSheetAt(sheetIndex);
	String stringCellValue = null;
	HashMap<String, String> btsNameBTSDNMap = new HashMap<String, String>();
	for (Row row : sheet) {
	    int cellIndexCount = 0;
	    String btsName = "";
	    String btsDN = "";
	    for (Cell cell : row) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
		    stringCellValue = cell.getRichStringCellValue().getString();
		    break;
		case Cell.CELL_TYPE_NUMERIC:
		    if (DateUtil.isCellDateFormatted(cell)) {
			stringCellValue = cell.getDateCellValue().toString();

		    } else {
			stringCellValue = Double
				.toString(cell.getNumericCellValue());
		    }
		    break;
		case Cell.CELL_TYPE_BOOLEAN:
		    stringCellValue = Boolean
			    .toString(cell.getBooleanCellValue());

		    break;
		case Cell.CELL_TYPE_FORMULA:
		    stringCellValue = cell.getCellFormula().toString();

		    break;
		default:
		}

		if (cellIndexCount == btsNameIndex) {
		    btsName = stringCellValue;
		}

		if (cellIndexCount == btsDNIndex) {
		    btsDN = stringCellValue;
		}

		cellIndexCount++;
	    }
	    btsNameBTSDNMap.put(btsName, btsDN);
	}

	return btsNameBTSDNMap;
    }
}