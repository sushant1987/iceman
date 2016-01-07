package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class TagMapping {

	private Long id;
	private String tenant;
	private Date createdDate;
	private String columnName;
	private String tagName;

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
	public Date getCreatedDate() {
		if(createdDate!=null)
		return (Date)createdDate.clone();
	return null;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate =(Date)createdDate.clone();
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
