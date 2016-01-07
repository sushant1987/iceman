package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
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
import com.sap.hcp.successfactors.lms.extensionfw.pojo.ReportStruct;

@Service
public class ReportStructServiceImpl implements ReportStructService{

	private static final Logger logger = LoggerFactory.getLogger(ReportStructServiceImpl.class);

	private static final String XS_ReportStruct_TABLE = "ReportStructs";

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
	public List<ReportStruct> getReportStructData() {
		List<ReportStruct> allReportStructData = new ArrayList<ReportStruct>();
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed = oDataAccess.readFeed(XS_ReportStruct_TABLE, null, null, null);
			for (ODataEntry entry : feed.getEntries()) {

				ReportStruct reportStructData = oDataEntryToReportStructData(entry);
				allReportStructData.add(reportStructData);
			}
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}

		return allReportStructData;
	}

	private ReportStruct oDataEntryToReportStructData(ODataEntry entry) {
		ReportStruct reportStructData = new ReportStruct();
		Map<String, Object> entryDetails = entry.getProperties();
		reportStructData.setId((Long) entryDetails.get("Id"));
		reportStructData.setTagName((String) entryDetails.get("TagName"));
		reportStructData.setParentTagName((String) entryDetails.get("ParentTagName"));
		reportStructData.setReportType((String) entryDetails.get("ReportType"));

		return reportStructData;
	}
}
