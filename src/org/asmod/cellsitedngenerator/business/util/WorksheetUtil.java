package org.asmod.cellsitedngenerator.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorksheetUtil {
    final static Logger logger = Logger.getLogger(WorksheetUtil.class);

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
    public static List<String> readWorksheetList(Workbook workBook, int cellIndexToSave, int sheetIndex) {

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
			stringCellValue = Double.toString(cell.getNumericCellValue());
		    }
		    break;
		case Cell.CELL_TYPE_BOOLEAN:
		    stringCellValue = Boolean.toString(cell.getBooleanCellValue());

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
     * Used by 2G, 3G, 4g getMap to get the Key Value Map from specific Worksheet.
     * e.g. entry key = (siteid/dn), value =(dn)
     * @param workbook
     * @param btsNameIndex
     * @param btsDNIndex
     * @param sheetIndex
     * @returns keyValue, ValueMap 
     */
    public static HashMap<String, String> readWorksheetMap(Workbook workBook, int uniqueKeyCellIndex, int dnValueIndex,
	    int sheetIndex) {

	Sheet sheet = workBook.getSheetAt(sheetIndex);
	String stringCellValue = null;
	HashMap<String, String> keyValueValueMap = new HashMap<String, String>();
	for (Row row : sheet) {
	    int cellIndexCount = 0;
	    String key = "";
	    String value = "";
	    for (Cell cell : row) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
		    stringCellValue = cell.getRichStringCellValue().getString();
		    break;
		case Cell.CELL_TYPE_NUMERIC:
		    if (DateUtil.isCellDateFormatted(cell)) {
			stringCellValue = cell.getDateCellValue().toString();

		    } else {
			stringCellValue = Double.toString(cell.getNumericCellValue());
		    }
		    break;
		case Cell.CELL_TYPE_BOOLEAN:
		    stringCellValue = Boolean.toString(cell.getBooleanCellValue());

		    break;
		case Cell.CELL_TYPE_FORMULA:
		    stringCellValue = cell.getCellFormula().toString();

		    break;
		default:
		}

		if (cellIndexCount == uniqueKeyCellIndex) {
		    key = stringCellValue;
		}

		if (cellIndexCount == dnValueIndex) {
		    value = stringCellValue;
		}

		cellIndexCount++;
	    }
	    keyValueValueMap.put(key+"/"+value, value);
	}

	return keyValueValueMap;
    }

    /*
     * Get the Assignee Name Cell Value List. We're Interested in 2nd Item Only
     */

    public static String getAssigneeName(Workbook workBook, int cellIndexToSave, int sheetIndex) {
	Sheet sheet = workBook.getSheetAt(sheetIndex);
	String stringCellValue = null;
	List<String> assigneeList = new ArrayList<String>();

	int rowCount = 0;
	labelRow: for (Row row : sheet) {
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
			stringCellValue = Double.toString(cell.getNumericCellValue());
		    }
		    break;
		case Cell.CELL_TYPE_BOOLEAN:
		    stringCellValue = Boolean.toString(cell.getBooleanCellValue());

		    break;
		case Cell.CELL_TYPE_FORMULA:
		    stringCellValue = cell.getCellFormula().toString();

		    break;
		default:
		}

		if (cellIndexCount == cellIndexToSave) {
		    assigneeList.add(stringCellValue);
		    if (rowCount == 1) {
			break labelRow; // we've got the name from 2nd row, exit
					// iteration
		    }
		}
		cellIndexCount++;

	    }

	    rowCount++;
	}

	return getAssigneeName(assigneeList);
    }

    private static String getAssigneeName(List<String> assigneeList) {
	String assigneeName = null;
	if (assigneeList.size() > 0) {
	    assigneeName = assigneeList.get(1);
	}

	return assigneeName;
    }
}