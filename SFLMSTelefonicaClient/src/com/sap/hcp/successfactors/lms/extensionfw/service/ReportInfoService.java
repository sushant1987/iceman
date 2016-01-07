/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.ReportInfo;

/**
 * @author I319792
 *
 */
public interface ReportInfoService {

	List<ReportInfo> getAll();

	ReportInfo save(ReportInfo reportInfo);

	ReportInfo getById(Long id);
	
	List<ReportInfo> getByReportType(String reportType);

	List<ReportInfo> getByLegalEntity(String legalEntity);
	
	public List<ReportInfo> getReportByParam(String id, String legalEntity, String date);

	void delete();

}
