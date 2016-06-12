package org.asmod.cellsitedngenerator.business;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public HashMap<String, String> getBTSNameBTSDNMap(String filePath) {
	// HSSFWorkbook workBook =
	// WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

	HashMap<String, String> btsNameBTSDNMap = WorksheetUtil
		.readWorksheetMap(workBook, Constants.TWO_G_BTS_NAME_CELL_INDEX,
			Constants.TWO_G_BTS_DN_CELL_INDEX,
			Constants.TWO_G_SHEET_INDEX);

	return btsNameBTSDNMap;
    }

}
