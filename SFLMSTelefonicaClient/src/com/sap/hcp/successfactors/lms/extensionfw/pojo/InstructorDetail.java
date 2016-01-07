/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class InstructorDetail {

	private long id;
	private String tenant;
	private Instructor instructor;
	private Offering offering;
	private long teachingDuration;
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

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Offering getOffering() {
		return offering;
	}

	public void setOffering(Offering offering) {
		this.offering = offering;
	}

	public long getTeachingDuration() {
		return teachingDuration;
	}

	public void setTeachingDuration(long teachingDuration) {
		this.teachingDuration = teachingDuration;
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
		this.modifiedOn =(Date)modifiedOn.clone();
	}
}
