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
//@XmlRootElement (name = "basicInfo")
public class BasicInfo {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String address;
	private String city;
	private String state;
	private String country;
	private String pin;
	private String fax;

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *                the firstName to set
	 */
	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *                the middleName to set
	 */
	@XmlElement
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *                the lastName to set
	 */
	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *                the phoneNumber to set
	 */
	@XmlElement
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *                the email to set
	 */
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *                the address to set
	 */
	@XmlElement
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *                the city to set
	 */
	@XmlElement
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *                the state to set
	 */
	@XmlElement
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *                the country to set
	 */
	@XmlElement
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin
	 *                the pin to set
	 */
	@XmlElement
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *                the fax to set
	 */
	@XmlElement
	public void setFax(String fax) {
		this.fax = fax;
	}
}
