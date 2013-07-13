/**
 * 
 */
package com.amzedia.xstore.model;

/**
 * @author Sushant
 * 
 */
public class Mail {

	private String sender;
	private String password;
	private String host;
	private String port;
	private String reciver;

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *                the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
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
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *                the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *                the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the reciver
	 */
	public String getReciver() {
		return reciver;
	}

	/**
	 * @param reciver
	 *                the reciver to set
	 */
	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *                the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *                the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	private String subject;
	private String message;
}
