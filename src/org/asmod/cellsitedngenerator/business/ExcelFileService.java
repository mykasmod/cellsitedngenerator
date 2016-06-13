package org.asmod.cellsitedngenerator.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ExcelFileService {

    public List<String> getSiteIdList(String filePath);

    public HashMap<String, String> getBTSBCFNameBTSBCFDNMap(String filePath);

    public HashMap<String, String> getWBTSDNMap(String filePath);

    public HashMap<String, String> getLNCELDNMap(String filePath);

    public List<String> getMergedDNList(Map<String, String> twoGMap,
	    Map<String, String> threeGMap, Map<String, String> fourGMap,
	    List<String> siteIdList);

    public String getOutputFileName(String filePath);

    public String generateDNFile(String assignmentFilePath,
	    String marketSiteFilePath);
}
