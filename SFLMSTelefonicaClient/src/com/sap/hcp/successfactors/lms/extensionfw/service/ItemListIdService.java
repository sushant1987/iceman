package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.ItemListId;

public interface ItemListIdService {
	
	public List<ItemListId> getAll();
	
	public void save(List<ItemListId> list);

	void delete();

	List<ItemListId> getByReportId(Long runId);

}
