package org.asmod.cellsitedngenerator.business;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.asmod.cellsitedngenerator.Constants;
import org.asmod.cellsitedngenerator.business.util.WorkbookFileUtil;
import org.asmod.cellsitedngenerator.business.util.WorksheetUtil;

public class ExcelFileServiceImpl implements ExcelFileService {

    @Override
    public List<String> getSiteIdList(String filePath) {
	HSSFWorkbook workBook = WorkbookFileUtil
		.getWorkBookFromFilePath(filePath);
	List<String> siteIdList = WorksheetUtil.readWorksheet(workBook,
		Constants.CELL_INDEX_TWO);

	return siteIdList;

    }

}
