package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.OfferingListId;

public interface OfferingListIdService { 

	public List<OfferingListId> getAll();
	
	public void save(List<OfferingListId> list);

	void delete();

	List<OfferingListId> getByReportId(Long runId);
	
}
