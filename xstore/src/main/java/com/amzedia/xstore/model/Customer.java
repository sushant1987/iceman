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
// @XmlRootElement (name = "customer")
public class Customer {

	private int id;
	private BasicInfo basicInfo;
	private String password;
	private String customerType;
	private boolean newsLetter;
	private boolean status;
	private String username;

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
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *                the customerType to set
	 */
	@XmlElement
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the newsLetter
	 */
	public boolean isNewsLetter() {
		return newsLetter;
	}

	/**
	 * @param newsLetter
	 *                the newsLetter to set
	 */
	@XmlElement
	public void setNewsLetter(boolean newsLetter) {
		this.newsLetter = newsLetter;
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
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *                the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
