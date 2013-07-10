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
//@XmlRootElement (name = "store")
public class Store {

	private int id;
	private String name;
	private Client client;
	private String currency;
	private String timeZone;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *                the name to set
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client
	 *                the client to set
	 */
	@XmlElement
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *                the currency to set
	 */
	@XmlElement
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone
	 *                the timeZone to set
	 */
	@XmlElement
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
