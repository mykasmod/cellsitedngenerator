package org.asmod.cellsitedngenerator.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public interface ExcelFileService {
    final static Logger logger = Logger.getLogger(ExcelFileService.class);

    public List<String> getSiteIdList(String filePath);

    public HashMap<String, String> get2GMap(String filePath, int uniqueKeyCellIndex, int dnCellIndex);

    public HashMap<String, String> get3GMap(String filePath);

    public HashMap<String, String> get4GMap(String filePath);

    public List<String> getMergedDNList(Map<String, String> twoGMap, Map<String, String> twoGMap2, Map<String, String> threeGMap, Map<String, String> threeGMap2, Map<String, String> threeGMap3, Map<String, String> fourGMap,
            Map<String, String> fourGMap2, Map<String, String> fourGMap3, List<String> siteIdList);

    public String getOutputFileName(String filePath);

    public String generateDNFile(String assignmentFilePath, String marketSiteFilePath);
}
