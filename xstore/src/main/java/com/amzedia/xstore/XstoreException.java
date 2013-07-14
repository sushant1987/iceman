/**
 * 
 */
package com.amzedia.xstore;

/**
 * @author Sushant
 * 
 */
public class XstoreException extends Exception {

	public XstoreException() {
		super();
	}

	public XstoreException(String message) {
		super(message);
	}

	public XstoreException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public XstoreException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public XstoreException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
