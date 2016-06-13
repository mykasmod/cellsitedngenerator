package org.asmod.cellsitedngenerator.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WorkbookFileUtil {

    private WorkbookFileUtil() {
    }

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

    public static void createWorkbookToFilePath(List<String> mergedDNList,
	    String filePath) {
	Workbook workbook = createWorkbook(mergedDNList);

	FileOutputStream fileOutputStream;
	try {
	    fileOutputStream = new FileOutputStream(new File(filePath));

	    workbook.write(fileOutputStream);
	    fileOutputStream.close();
	    // TODO: CLEAN OR Progress bar; System.out.println(filePath + "
	    // written successfully");
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    private static Workbook createWorkbook(List<String> mergedDNList) {
	Workbook workbook = new HSSFWorkbook();

	Sheet sheet = workbook.createSheet("DN List");

	/*
	 * Row rowhead = sheet.createRow((short) 0);
	 * rowhead.createCell(0).setCellValue("DN List");
	 */
	int rowCount = 0;
	Row row = null;
	for (String dn : mergedDNList) {
	    row = sheet.createRow((short) rowCount);
	    row.createCell(0).setCellValue(dn);
	    rowCount++;
	}
	return workbook;
    }

}
