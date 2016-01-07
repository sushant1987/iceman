/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class Facility {

	private long id;
	private String tenant;
	private String lmsFacilityId;
	private String centerName;
	private String facilityName;
	private String address;
	private String zipCode;
	private String city;
	private String country;
	private String createdBy;
	private Date createdOn;
	private String modifiedBy;
	private Date modifiedOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getLmsFacilityId() {
		return lmsFacilityId;
	}

	public void setLmsFacilityId(String lmsFacilityId) {
		this.lmsFacilityId = lmsFacilityId;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		if(createdOn!=null)
		return (Date)createdOn.clone();
	return null;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = (Date)createdOn.clone();
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		if(modifiedOn!=null)
		return (Date)modifiedOn.clone();
	return null;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = (Date)modifiedOn.clone();
	}
}
