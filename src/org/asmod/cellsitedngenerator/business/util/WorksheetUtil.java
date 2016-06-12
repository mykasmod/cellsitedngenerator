package org.asmod.cellsitedngenerator.business.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

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
    public static List<String> readWorksheet(HSSFWorkbook workBook,
	    int cellIndexToSave) {

	HSSFSheet sheet = workBook.getSheetAt(0);
	String stringCellValue = null;
	List<String> siteIdList = new ArrayList<String>();

	for (Row row : sheet) {
	    int cellIndex = 0;
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

		if (cellIndex == cellIndexToSave) {
		    siteIdList.add(stringCellValue);
		}
		cellIndex++;

	    }
	}

	return cleanSiteIdList(siteIdList);
    }

    private static List<String> cleanSiteIdList(List<String> siteidList) {
	siteidList.remove(siteidList.indexOf("SiteID"));
	return siteidList;
    }

}