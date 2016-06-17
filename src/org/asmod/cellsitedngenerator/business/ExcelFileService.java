package org.asmod.cellsitedngenerator.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ExcelFileService {

    public List<String> getSiteIdList(String filePath);

    public HashMap<String, String> get2GMap(String filePath, int dnCellIndex);

    public HashMap<String, String> get3GMap(String filePath, boolean isCleanKey, boolean isCleanValue);

    public HashMap<String, String> get4GMap(String filePath, boolean isCleanKey);

    public List<String> getMergedDNList(Map<String, String> twoGMap, Map<String, String> twoGMap2,
            Map<String, String> threeGMap, Map<String, String> threeGMap2, Map<String, String> fourGMap, Map<String, String> fourGMap2, Map<String, String> fourGMap3,
            List<String> siteIdList);

    public String getOutputFileName(String filePath);

    public String generateDNFile(String assignmentFilePath,
	    String marketSiteFilePath);
}
