/**
 * 
 */
package com.amzedia.xstore.model;

import com.amzedia.xstore.model.BasicInfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Sushant
 * 
 */
//@XmlRootElement(name = "client")
public class Client {

	private int id;
	private BasicInfo basicInfo;
	private String userName;
	private String password;
	private boolean status;
	private String planType;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *                the id to set
	 */
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the basicInfo
	 */
	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	/**
	 * @param basicInfo
	 *                the basicInfo to set
	 */
	@XmlElement
	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *                the userName to set
	 */
	@XmlElement
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *                the password to set
	 */
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *                the status to set
	 */
	@XmlElement
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}
}
