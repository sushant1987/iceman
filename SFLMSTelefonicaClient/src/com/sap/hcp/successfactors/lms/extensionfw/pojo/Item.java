/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class Item {

	private Long id;
	
	private Date modifiedOn;
	
	private Date itemCreatedOn;
	
	private String modifiedByUser;
	
	private String itemCode;
	
	private String itemCode1;
	
	private String itemTitle;
	
	private Double itemLength;
	
	private String delMethod;
	
	private String legalEntity;
	
	private Double creditHoursScheduled;
	
	private Double creditHoursOnline;
	
	private String observations;
	
	private String objectives;
	
	private String conIndex;
	
	private String customData;
	
	private Date startDate;
	
	private String difficultyLevel;
	
	public String getItemCode1() {
		return itemCode1;
	}
	public void setItemCode1(String itemCode1) {
		this.itemCode1 = itemCode1;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Date getItemCreatedOn() {
		return itemCreatedOn;
	}
	public void setItemCreatedOn(Date itemCreatedOn) {
		this.itemCreatedOn = itemCreatedOn;
	}
	public Double getItemLength() {
		return itemLength;
	}
	public void setItemLength(Double itemLength) {
		this.itemLength = itemLength;
	}
	public String getDelMethod() {
		return delMethod;
	}
	public void setDelMethod(String delMethod) {
		this.delMethod = delMethod;
	}
	public Double getCreditHoursScheduled() {
		return creditHoursScheduled;
	}
	public void setCreditHoursScheduled(Double creditHoursScheduled) {
		this.creditHoursScheduled = creditHoursScheduled;
	}
	public Double getCreditHoursOnline() {
		return creditHoursOnline;
	}
	public void setCreditHoursOnline(Double creditHoursOnline) {
		this.creditHoursOnline = creditHoursOnline;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public String getObjectives() {
		return objectives;
	}
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}
	public String getConIndex() {
		return conIndex;
	}
	public void setConIndex(String conIndex) {
		this.conIndex = conIndex;
	}
	public Date getModifiedOn() {
		if(modifiedOn!=null)
		return (Date)modifiedOn.clone();
	return null;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = (Date)modifiedOn.clone();
	}
	public String getModifiedByUser() {
		return modifiedByUser;
	}
	public void setModifiedByUser(String modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}
	public String getCustomData() {
		return customData;
	}
	public void setCustomData(String customData) {
		this.customData = customData;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public Date getStartDate() {
		if(startDate!=null)
		return (Date)startDate.clone();
	return null;
	}
	public void setStartDate(Date startDate) {
		this.startDate = (Date)startDate.clone();
	}
	public String getDifficultyLevel() {
		return difficultyLevel;
	}
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
}
