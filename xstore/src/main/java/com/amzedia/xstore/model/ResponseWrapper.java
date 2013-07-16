/**
 * 
 */
package com.amzedia.xstore.model;

/**
 * @author Sushant
 * 
 */
public class ResponseWrapper {

	private String status;
	private String message;
	private Object result;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *                the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result
	 *                the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

}
