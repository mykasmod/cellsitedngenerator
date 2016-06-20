package org.asmod.cellsitedngenerator.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.asmod.cellsitedngenerator.Constants;
import org.asmod.cellsitedngenerator.MainWindow;
import org.asmod.cellsitedngenerator.business.util.WorkbookFileUtil;
import org.asmod.cellsitedngenerator.business.util.WorksheetUtil;

public class ExcelFileServiceImpl implements ExcelFileService {
    final static Logger logger = Logger.getLogger(ExcelFileServiceImpl.class);

    private String assigneeName = null;

    @Override
    public List<String> getSiteIdList(String filePath) {
        Workbook workBook = null;
        workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

        List<String> siteIdList = WorksheetUtil.readWorksheetList(workBook, Constants.SITE_ID_CELL_INDEX, Constants.SITE_ASSIGNMENT_SHEET_INDEX);

        return siteIdList;

    }

    /*
     * Get 2G KeyValue Map . The 1st run example passed params are:
     * bcf_name_cell_key, bcf_dn_cell_value. First Run Use BCF_NAME as key in
     * [key,value] Use whole column BCF_NAME as key to get BCF_DN
     */
    @Override
    public HashMap<String, String> get2GMap(String filePath, int uniqueKeyCellIndex, int dnCellIndex) {
        Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);
        HashMap<String, String> twoGKeyValueMap = WorksheetUtil.readWorksheetMap(workBook, uniqueKeyCellIndex, dnCellIndex, Constants.TWO_G_SHEET_INDEX);
        return twoGKeyValueMap;
    }

    /*
     * Get 3G DN Map Complete set first, then remove wcel, then wbts, then rnc
     * @params filePath
     * @params isCleanKey
     * @params isCleanValue
     */
    @Override
    public HashMap<String, String> get3GMap(String filePath) {
        Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

        HashMap<String, String> dnMap = WorksheetUtil.readWorksheetMap(workBook, Constants.THREE_G_WCELL_NAME_CELL_INDEX, Constants.THREE_G_DN_CELL_INDEX, Constants.THREE_G_SHEET_INDEX);
        return dnMap;
    }

    private HashMap<String, String> clean3GValue(HashMap<String, String> map) {

        HashMap<String, String> cleaned3GValueMap = new HashMap<String, String>();

        String strippedValue = null;
        int lastIndexOfSlash = 0;
        String value = null;
        for (Entry<String, String> entry : map.entrySet()) {
            value = entry.getValue();
            if (value.length() > 0) { // stripped all lastIndexOfSlash
                lastIndexOfSlash = value.lastIndexOf('/');
                if (lastIndexOfSlash > 8) {
                    strippedValue = value.substring(0, lastIndexOfSlash);
                } else {
                    strippedValue = value;
                }
            } else {
                strippedValue = value;
            }
            cleaned3GValueMap.put(entry.getKey(), strippedValue);
        }
        return cleaned3GValueMap;
    }

    /*
     * Get 4G Map
     */
    @Override
    public HashMap<String, String> get4GMap(String filePath) {
        Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);
        HashMap<String, String> lncelDNMap = WorksheetUtil.readWorksheetMap(workBook, Constants.FOUR_G_LNCELL_NAME_INDEX, Constants.FOUR_G_LNCEL_DN_INDEX, Constants.FOUR_G_SHEET_INDEX);
        return lncelDNMap;
    }

    private HashMap<String, String> clean4GValue(HashMap<String, String> map) {

        HashMap<String, String> cleanMap = new HashMap<String, String>();

        String strippedValue = null;
        int lastIndexOfSlash = 0;
        String value = null;
        for (Entry<String, String> entry : map.entrySet()) {
            value = entry.getValue();
            if (value.length() > 0) { // stripped all lastIndexOfSlash
                lastIndexOfSlash = value.lastIndexOf('/');
                if (lastIndexOfSlash > 8) {
                    strippedValue = value.substring(0, lastIndexOfSlash);
                } else {
                    strippedValue = value;
                }
            } else {
                strippedValue = value;
            }
            cleanMap.put(entry.getKey(), strippedValue);
        }
        return cleanMap;
    }

    /*
     * Get Merged DN List of all network Map
     */
    @Override
    public List<String> getMergedDNList(Map<String, String> twoGMap, Map<String, String> twoGMap2, Map<String, String> threeGMap, Map<String, String> threeGMap2, Map<String, String> threeGMap3, Map<String, String> fourGMap,
            Map<String, String> fourGMap2, Map<String, String> fourGMap3, List<String> siteIdList) {
        List<String> mergedDNList = new ArrayList<String>();
        mergedDNList.addAll(getDNListMatchedSiteID(twoGMap, siteIdList));
        MainWindow.setTextAreaLoggerText(twoGMap.size() + Constants.TWO_G_LOGMESSAGE + Constants.FIRST_RUN);
        mergedDNList.addAll(getDNListMatchedSiteID(twoGMap2, siteIdList));
        MainWindow.setTextAreaLoggerText(twoGMap2.size() + Constants.TWO_G_LOGMESSAGE + Constants.SECOND_RUN);
        mergedDNList.addAll(getDNListMatchedSiteID(threeGMap, siteIdList));
        MainWindow.setTextAreaLoggerText(threeGMap.size() + Constants.THREE_G_LOGMESSAGE + Constants.FIRST_RUN);
        mergedDNList.addAll(getDNListMatchedSiteID(threeGMap2, siteIdList));
        MainWindow.setTextAreaLoggerText(threeGMap2.size() + Constants.THREE_G_LOGMESSAGE + Constants.SECOND_RUN);
        mergedDNList.addAll(getDNListMatchedSiteID(threeGMap3, siteIdList));
        MainWindow.setTextAreaLoggerText(threeGMap3.size() + Constants.THREE_G_LOGMESSAGE + Constants.THIRD_RUN);
        mergedDNList.addAll(getDNListMatchedSiteID(fourGMap, siteIdList));
        MainWindow.setTextAreaLoggerText(fourGMap.size() + Constants.FOUR_G_LOGMESSAGE + Constants.FIRST_RUN);
        mergedDNList.addAll(getDNListMatchedSiteID(fourGMap2, siteIdList));
        MainWindow.setTextAreaLoggerText(fourGMap2.size() + Constants.FOUR_G_LOGMESSAGE + Constants.SECOND_RUN);
        mergedDNList.addAll(getDNListMatchedSiteID(fourGMap3, siteIdList));
        MainWindow.setTextAreaLoggerText(fourGMap3.size() + Constants.FOUR_G_LOGMESSAGE + Constants.THIRD_RUN);
        MainWindow.setTextAreaLoggerText(mergedDNList.size() + " Total Matched SiteId for " + assigneeName);
        return mergedDNList;
    }

    private List<String> getDNListMatchedSiteID(Map<String, String> gMap, List<String> siteIdList) {
        List<String> dnList = new ArrayList<String>();
        for (String siteId : siteIdList) {
            // It's agreed that key in the entry always has the format
            // "8digit_siteid/dnvalue"
            for (Entry<String, String> entry : gMap.entrySet()) {
                if (entry.getKey().contains(siteId)) {
                    dnList.add(entry.getValue());
                }
            }
        }
        return dnList;
    }

    /*
     * Get OutputFileName
     */
    @Override
    public String getOutputFileName(String filePath) {
        int lastIndexOfSlash = filePath.lastIndexOf('\\');
        String outputFullFileName = filePath.substring(0, lastIndexOfSlash) + '\\' + getAssigneeName(filePath) + Constants.FILE_EXTENSION;
        return outputFullFileName;
    }

    private String getAssigneeName(String filePath) {
        Workbook workBook = WorkbookFileUtil.getWorkBookFromFilePath(filePath);

        String assigneeName = WorksheetUtil.getAssigneeName(workBook, Constants.ASSIGNEE_CELL_INDEX, Constants.SITE_ASSIGNMENT_SHEET_INDEX);

        return assigneeName;
    }

    private String getExtension(String filepath) {
        return filepath.substring(filepath.lastIndexOf('.'), filepath.length());
    }

    @Override
    public String generateDNFile(String assignmentFilePath, String marketSiteFilePath) {

        // Get output file name
        String outputFileName = null;

        if (!Constants.FILE_EXTENSION.equalsIgnoreCase(getExtension(marketSiteFilePath)) || !Constants.FILE_EXTENSION.equalsIgnoreCase(getExtension(assignmentFilePath))) {
            MainWindow.setTextAreaLoggerText(Constants.INVALID_EXTENSION_MESSAGE);
            logger.info(Constants.INVALID_EXTENSION_MESSAGE);
            // TODO: Evaluate further if needed to interrupt here
            if (Thread.currentThread().isAlive()) {
                Thread.currentThread().getThreadGroup().interrupt();
            }
            return null;
        }
        // List
        List<String> siteIdList = getSiteIdList(assignmentFilePath);

        // Set member var assignee name
        assigneeName = getAssigneeName(assignmentFilePath);

        int increment = 1;

        // Map 2G
        // KEEP ALL

        // #2G First Run
        // Use BCF_NAME+BCF_DN as key in [key,value]
        // Use whole column BCF_NAME+whole BCF_DN as key to get BCF_DN value
        //
        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        Map<String, String> twoGMap = get2GMap(marketSiteFilePath, Constants.TWO_G_BCF_NAME_CELL_INDEX, Constants.TWO_G_BCF_DN_CELL_INDEX);

        // #2G Second Run
        // -1 Concatenate BCF_NAME + BTS_DN, use as KEY in [key,value]
        // -1 Save BTS_DN as value
        // -2 Match KEY that contains siteid save BTS_DN

        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        Map<String, String> twoGMap2 = get2GMap(marketSiteFilePath, Constants.TWO_G_BCF_NAME_CELL_INDEX, Constants.TWO_G_BTS_DN_CELL_INDEX);

        // Map 3G
        // PLMN-PLMN/RNC-615/WBTS-13311/WCEL-1331112
        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        HashMap<String, String> threeGMap = get3GMap(marketSiteFilePath);

        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        HashMap<String, String> threeGMap2 = clean3GValue(threeGMap);

        HashMap<String, String> threeGMap3 = new HashMap<String, String>();
        if (MainWindow.getChkbox3GRNCIsSelected()) {
            MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
            threeGMap3 = clean3GValue(threeGMap2);
        } else {
            threeGMap3.clear();
        }

        // Map 4G
        // PLMN-PLMN/MRBTS-74341/LNBTS-74341/LNCEL-11
        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        HashMap<String, String> fourGMap = get4GMap(marketSiteFilePath);

        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        HashMap<String, String> fourGMap2 = clean4GValue(fourGMap);

        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        HashMap<String, String> fourGMap3 = clean4GValue(fourGMap2);

        // Merged DN List
        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        List<String> mergedDNList = getMergedDNList(twoGMap, twoGMap2, threeGMap, threeGMap2, threeGMap3, fourGMap, fourGMap2, fourGMap3, siteIdList);

        outputFileName = getOutputFileName(assignmentFilePath);

        // Save output file
        MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + increment);
        WorkbookFileUtil.createWorkbookToFilePath(mergedDNList, outputFileName);

        return outputFileName;
    }

}
