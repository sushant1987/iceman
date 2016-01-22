package com.sap.hcp.successfactors.lms.extensionfw.service;


import java.util.List;

import org.apache.olingo.odata2.api.ep.feed.ODataFeed;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Offering;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;

public interface NewOfferingService {

	void addCustomFieldValue(Offering offeringCustomvalue);
	List<Offering> getOfferingData(String id, String legalEntity, String date, String days, boolean checkForInstructor);
	ODataFeed getOfferingData();
	
	List<Offering> getInvalidOfferingData(String code, List <Parametrised> param);
	List<Offering> getOfferingByOfferingIds(List<Long> offeringIds);
}
