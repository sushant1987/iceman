package com.sap.hcp.successfactors.lms.extensionfw.service;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.OfferingParticipant;
import com.sap.hcp.successfactors.lms.extensionfw.multitenancy.CurrentTenantResolver;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientService;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientServiceImpl;

@Service
public class OfferingParticipantServiceImpl implements OfferingParticipantService{

	private static final Logger logger = LoggerFactory.getLogger(OfferingParticipantServiceImpl.class);

	private static final String XS_OFFERINGPARTICIPANT_TABLE = "OfferingParticipants";

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
	public ODataFeed getOfferingParticipantData() {
		ODataFeed feed = null;
		try {
			ODataClientService oDataAccess = getODataService();
			feed = oDataAccess.readFeed(XS_OFFERINGPARTICIPANT_TABLE, null, null, null);
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}
		return feed;
	}
	
	@Override
	public ODataFeed getOfferingParticipantData(String id, String legalEntity, String date) {
		ODataFeed feed = null;
		try {
			ODataClientService oDataAccess = getODataService();
			feed = oDataAccess.readFeed(XS_OFFERINGPARTICIPANT_TABLE, null, null, null);
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}
		return feed;
	}
		
		
		@Override
		public void addCustomFieldValue(OfferingParticipant offeringParticipantCustomValue) {
			try {
				ODataClientService oDataAccess = getODataService();

				Map<String, Object> data = extendedDataToOData(offeringParticipantCustomValue);

				oDataAccess.patchEntry(offeringParticipantCustomValue.getId(),
						XS_OFFERINGPARTICIPANT_TABLE, data);

			} catch (Exception e) {
				logger.error("Something wrong getting OData ref", e);
			}

		}

		private Map<String, Object> extendedDataToOData(OfferingParticipant offeringParticipantCustomvalue) {
			Map<String, Object> odata = new HashMap<String, Object>();

			odata.put("CustomData", offeringParticipantCustomvalue.getCustomData());
			odata.put("Id",offeringParticipantCustomvalue.getId());
			return odata;
		}
	
}
