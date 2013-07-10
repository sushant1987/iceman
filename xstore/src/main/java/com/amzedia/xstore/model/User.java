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
//@XmlRootElement (name = "user")
public class User {

	private int id;
	private BasicInfo basicInfo;
	private Store store;
	private String userName;
	private String password;
	private String userType;
	private boolean newsLetter;

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
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *                the userType to set
	 */
	@XmlElement
	public void setUserType(String userType) {
		this.userType = userType;
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
}
