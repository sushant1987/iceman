package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;
import java.util.List;

public class ValueHelp {

	private long id;
	private String tenant;
	private List<ValueHelpText> valueHelpTexts;
	private long sequenceNumber;
	private String value;
	private String createdBy;
	private Date createdOn;
	private String createdByUser;
	private String modifiedBy;
	private Date modifiedOn;
	private String modifiedByUser;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		if(createdOn!=null)
		return (Date) createdOn.clone();
	return null;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = (Date) createdOn.clone();
	}

	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		if(modifiedOn!=null)
		return (Date) modifiedOn.clone();
		
	return null;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = (Date) modifiedOn.clone();
	}

	public String getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(String modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<ValueHelpText> getValueHelpTexts() {
		return valueHelpTexts;
	}

	public void setValueHelpTexts(List<ValueHelpText> valueHelpTexts) {
		this.valueHelpTexts = valueHelpTexts;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
}
