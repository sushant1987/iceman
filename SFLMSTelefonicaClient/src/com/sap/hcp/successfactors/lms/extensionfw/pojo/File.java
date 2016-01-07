package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class File {

	private Long id;
	private String tenant;
	private String fileType;
	private Date createdDate;
	private int acceptanceStatus;
	private String createdBy;
	private Date startDateSelected;
	private Date endDateSelected;

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
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Date getCreatedDate() {
		if(createdDate!=null)
		return (Date)createdDate.clone();
	return null;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public int getAcceptanceStatus() {
		return acceptanceStatus;
	}
	public void setAcceptanceStatus(int acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getStartDateSelected() {
		if(startDateSelected!=null)
		return (Date)startDateSelected.clone();
	return null;
	}
	public void setStartDateSelected(Date startDateSelected) {
		this.startDateSelected = (Date)startDateSelected.clone();
	}
	public Date getEndDateSelected() {
		if(endDateSelected!=null)
		return (Date)endDateSelected.clone();
	return null;
	}
	public void setEndDateSelected(Date endDateSelected) {
		this.endDateSelected =(Date)endDateSelected.clone();
	}
}
