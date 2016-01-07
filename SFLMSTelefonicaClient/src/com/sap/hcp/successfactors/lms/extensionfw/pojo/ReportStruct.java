package com.sap.hcp.successfactors.lms.extensionfw.pojo;

public class ReportStruct {

	private Long id;
	private String tenant;
	private String tagName;
	private String parentTagname;
	private String reportType;
	private String showflag;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getParentTagName() {
		return parentTagname;
	}
	public void setParentTagName(String parentTagname) {
		this.parentTagname = parentTagname;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getShowflag() {
		return showflag;
	}
	public void setShowflag(String showflag) {
		this.showflag = showflag;
	}
	
}
