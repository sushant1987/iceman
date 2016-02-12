package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.text.ParseException;
import java.util.List;

import org.apache.olingo.odata2.api.ep.feed.ODataFeed;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Employee;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Item;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Offering;

public interface ItemService {
	
	ODataFeed getItemData();

	void addCustomFieldValue(Item itemCustomvalue);

	List<Item> getItemData(String id, String legalEntity, String date,
			String runId);

	List<Item> getItemByItemIds(List<Long> itemIds);
}
