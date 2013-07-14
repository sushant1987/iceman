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
//@XmlRootElement (name = "customer")
public class Customer {

	private int id;
	private BasicInfo basicInfo;
	private Store store;
	private Group group;
	private String customerName;
	private String password;
	private String customerType;
	private boolean newsLetter;
	private boolean status;

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
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store
	 *                the store to set
	 */
	@XmlElement
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *                the customerName to set
	 */
	@XmlElement
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
}
