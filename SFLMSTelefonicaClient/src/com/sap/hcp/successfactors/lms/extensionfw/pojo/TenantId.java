package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class TenantId {

	private String tenant;
	private String name;
	private String modifiedBy;
	private Date modifiedOn;
	private String modifiedByUser;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
}
