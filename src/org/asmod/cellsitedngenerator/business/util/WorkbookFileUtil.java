package org.asmod.cellsitedngenerator.business.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WorkbookFileUtil {

    private WorkbookFileUtil() {
    }

    public static HSSFWorkbook getWorkBookFromFilePath(String filePath) {
	FileInputStream file;
	HSSFWorkbook workBook = null;
	try {
	    file = new FileInputStream(filePath);
	    workBook = new HSSFWorkbook(file);
	} catch (FileNotFoundException e) {

	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return workBook;
    }

}
