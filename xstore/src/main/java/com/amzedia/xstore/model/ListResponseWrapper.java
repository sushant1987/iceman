/**
 * 
 */
package com.amzedia.xstore.model;

import java.util.List;

/**
 * @author sushant
 *
 */
public class ListResponseWrapper {

	private String status;
	private String message;
	private List<Object> result;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
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
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the result
	 */
	public List<Object> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(List<Object> result) {
		this.result = result;
	}
}
