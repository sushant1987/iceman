package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
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
import com.sap.hcp.successfactors.lms.extensionfw.pojo.TagMapping;

@Service
public class TagMappingServiceImpl implements TagMappingService{
	
	private static final Logger logger = LoggerFactory.getLogger(TagMappingServiceImpl.class);

	private static final String XS_TagMapping_TABLE = "TagMappings";

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
	public List<TagMapping> getTagMappingData() {
		List<TagMapping> allTagMappingData = new ArrayList<TagMapping>();
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed = oDataAccess.readFeed(XS_TagMapping_TABLE, null, null, null);
			for (ODataEntry entry : feed.getEntries()) {

				TagMapping TagMappingData = oDataEntryToTagMappingData(entry);
				allTagMappingData.add(TagMappingData);
			}
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}

		return allTagMappingData;
	}
	private TagMapping oDataEntryToTagMappingData(ODataEntry entry) {
		TagMapping TagMappingData = new TagMapping();
		Map<String, Object> entryDetails = entry.getProperties();
		TagMappingData.setId((Long) entryDetails.get("Id"));
        //TagMappingData.setTenant((String) entryDetails.get("TENANT_ID"));
        TagMappingData.setCreatedDate((Date) entryDetails.get("CreatedDate"));
        TagMappingData.setColumnName((String) entryDetails.get("ColumnName"));
        TagMappingData.setTagName((String) entryDetails.get("TagName"));
     
        return TagMappingData;
	}
	
}
