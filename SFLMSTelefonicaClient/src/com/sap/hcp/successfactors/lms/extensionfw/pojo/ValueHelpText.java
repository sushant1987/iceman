package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class ValueHelpText {

	private long id;
	private TenantId tenant;
	private ValueHelp valueHelp;
	private long sequenceNumber;
	private String languageKey;
	private String text;
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

	public TenantId getTenant() {
		return tenant;
	}

	public void setTenant(TenantId tenant) {
		this.tenant = tenant;
	}
	
	public ValueHelp getValueHelp() {
		return valueHelp;
	}

	public void setValueHelp(ValueHelp valueHelp) {
		this.valueHelp = valueHelp;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLanguageKey() {
		return languageKey;
	}

	public void setLanguageKey(String languageKey) {
		this.languageKey = languageKey;
	}
}
