package org.asmod.cellsitedngenerator.business.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WorkbookFileUtil {

    private WorkbookFileUtil() {
    }

    // public static HSSFWorkbook getWorkBookFromFilePath(String filePath) {
    public static Workbook getWorkBookFromFilePath(String filePath) {
	FileInputStream file;
	// HSSFWorkbook workBook = null;
	Workbook workBook = null;

	try {
	    file = new FileInputStream(filePath);
	    // workBook = new XSSFWorkbook(file); // new HSSFWorkbook(file);
	    workBook = WorkbookFactory.create(file);
	} catch (FileNotFoundException e) {

	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return workBook;
    }

}
