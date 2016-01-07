package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.util.Date;
import java.util.UUID;

public class Participant {
	private String participantId;
	private String name;
	private String firstSurname;
	private String secondSurname;
	private String sex;
	private Date dateOfBirth;
	private String studylevel;
	private Date createdDate;
	private String email;
	private String phoneNumber;
	private String cg;
	private UUID customDataId;
	
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
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = (Date)dateOfBirth.clone();
	}
	public String getStudylevel() {
		return studylevel;
	}
	public void setStudylevel(String studylevel) {
		this.studylevel = studylevel;
	}
	public Date getCreatedDate() {
		if(createdDate!=null)
		return (Date)createdDate.clone();
	return null;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate =(Date)createdDate.clone();
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
	public String getCg() {
		return cg;
	}
	public void setCg(String cg) {
		this.cg = cg;
	}
	public UUID getCustomDataId() {
		return customDataId;
	}
	public void setCustomDataId(UUID customDataId) {
		this.customDataId = customDataId;
	}
}
