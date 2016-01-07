package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;

public class OfferingParticipant {

	private Long id;
	private String tenant;
	private Offering offering;
	private Item item;
	private String participantId;
	private int typeDocument;
	private String name;
	private String firstSurname;
	private String secondSurname;
	private String sex;
	private Date dateOfBirth;
	private Date createdDate;
	private String email;
	private String phoneNumber;
	private String customData;
	
	public Offering getOffering() {
		return offering;
	}
	public void setOffering(Offering offering) {
		this.offering = offering;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public long getId() {
		if(id!=null)
		return id;
	return 0l;
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
	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstSurname() {
		return firstSurname;
	}
	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}
	public String getSecondSurname() {
		return secondSurname;
	}
	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getDateOfBirth() {
		if(dateOfBirth!=null)
		return (Date)dateOfBirth.clone();
	return null;
	}
	public void setDateOfBirth(Date date) {
		this.dateOfBirth = (Date)date.clone();
	}
	public Date getCreatedDate() {
		if(createdDate!=null)
		return (Date)createdDate.clone();
	return null;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCustomData() {
		return customData;
	}
	public void setCustomData(String customData) {
		this.customData = customData;
	}
	public int getTypeDocument() {
		return typeDocument;
	}
	public void setTypeDocument(int typeDocument) {
		this.typeDocument = typeDocument;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
