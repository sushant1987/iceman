/**
 * 
 */
package com.amzedia.xstore.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sushant
 *
 */
//@XmlRootElement (name = "changePassword")
public class ChangePassword {

	private int id;
	private String userName;
	private String oldPassword;
	private String newPassword;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	@XmlElement
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * @param oldPassword the oldPassword to set
	 */
	@XmlElement
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	@XmlElement
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
