package org.asmod.cellsitedngenerator.business;

import java.util.HashMap;
import java.util.List;

public interface ExcelFileService {

    public List<String> getSiteIdList(String filePath);

    public HashMap<String, String> getBTSBCFNameBTSBCFDNMap(String filePath);

    public HashMap<String, String> getWBTSDNMap(String filePath);
}
