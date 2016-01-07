package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hcp.successfactors.lms.extensionfw.multitenancy.CurrentTenantResolver;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientService;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientServiceImpl;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.File;

@Service
public class FileServiceImpl implements FileService{

	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	private static final String XS_FILE_TABLE = "File";

	ODataClientService oDataService;
	
    @Autowired
	CurrentTenantResolver currentTenantResolver;

	private synchronized ODataClientService getODataService() throws EntityProviderException, IOException, NamingException {

		if (oDataService == null ) {
			oDataService = new ODataClientServiceImpl(currentTenantResolver);
			oDataService.getEdm();
		}

		return oDataService;

	}

	@Override
	public List<File> getFileData() {
		List<File> allFileData = new ArrayList<File>();
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed = oDataAccess.readFeed(XS_FILE_TABLE, null, null, null);
			for (ODataEntry entry : feed.getEntries()) {

				File FileData = oDataEntryToFileData(entry);
				allFileData.add(FileData);
			}
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}

		return allFileData;
	}
	
	private File oDataEntryToFileData(ODataEntry entry) {
		File fileData = new File();
		Map<String, Object> entryDetails = entry.getProperties();
        fileData.setId((Long) entryDetails.get("ID"));
        fileData.setTenant((String) entryDetails.get("TENANT_ID"));
        fileData.setFileType((String) entryDetails.get("FILE_TYPE"));
        fileData.setCreatedDate((Date) entryDetails.get("CREATED_DATE"));
        fileData.setAcceptanceStatus((int) entryDetails.get("ACCEPTANCE_STATUS"));
        fileData.setCreatedBy((String) entryDetails.get("CREATED_BY"));
        fileData.setStartDateSelected((Date) entryDetails.get("START_DATE_SELECTED"));
        fileData.setEndDateSelected((Date) entryDetails.get("END_DATE_SELECTED"));
 
        return fileData;
	}

	@Override
	public boolean insertFileData(File file) {
	//	ODataEntry odataentry = null;
//		try {
//			ODataClientService oDataAccess = getODataService();
//			Map<String , Object> map = new HashMap<String, Object>();
//			map.put("TENANT_ID", (Object) file.getTenant());
//			map.put("FILE_TYPE", (Object) file.getFileType());
//			map.put("CREATED_DATE", (Object) file.getCreatedDate());
//			map.put("CREATED_BY", (Object) file.getCreatedBy());
//			map.put("ACCEPTANCE_STATUS", (Object) file.getAcceptanceStatus());
//			map.put("START_DATE_SELECTED", (Object) file.getStartDateSelected());
//			map.put("END_DATE_SELECTED", (Object) file.getEndDateSelected());
//			if(odataentry != null)
//				return true;
//		} catch (EntityProviderException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		} catch (NamingException e) {
//			
//			e.printStackTrace();
//		}
//		catch (Exception e) {
//			
//			e.printStackTrace();
//		}
///		return false;
		
		
		return true;
	}
}
