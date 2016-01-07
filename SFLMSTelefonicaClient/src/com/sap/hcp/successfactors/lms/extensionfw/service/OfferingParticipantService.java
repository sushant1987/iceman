package com.sap.hcp.successfactors.lms.extensionfw.service;

import org.apache.olingo.odata2.api.ep.feed.ODataFeed;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Item;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.OfferingParticipant;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Offering;

public interface OfferingParticipantService {

	ODataFeed getOfferingParticipantData();
	
	void addCustomFieldValue(OfferingParticipant offeringparticipantCustomvalue);
	
	ODataFeed getOfferingParticipantData(String id, String legalEntity, String date);
}
