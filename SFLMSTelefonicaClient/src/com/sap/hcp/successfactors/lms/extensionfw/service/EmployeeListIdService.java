package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.EmployeeListId;

public interface EmployeeListIdService {

	public List<EmployeeListId> getAll();
	
	public void save(List<EmployeeListId> list);

	void delete();
	
}
