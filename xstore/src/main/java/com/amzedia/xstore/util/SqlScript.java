/**
 * 
 */
package com.amzedia.xstore.util;

/**
 * @author Sushant
 * 
 */
public final class SqlScript {

	private SqlScript() {

	}

	/**
	 * Save dummy data
	 */
	public static final String DUMMY = "INSERT INTO BASIC_DETAIL (FIRST_NAME,"
			+ "MIDDLE_NAME,LAST_NAME,PHONE_NUMBER,EMAIL,ADDRESS,CITY,"
			+ "STATE,COUNTRY,PIN_CODE,FAX,UPDATED_BY,CREATED_BY,UPDATED_DATE,CREATED_DATE)"
			+ "VALUES('Sushant','Kumar','Singh',9793234212,'sushant1887@gmail.com','aa','Bangalore',"
			+ "'Karnataka','India','240001','13213213','admin','admin',sysdate(),sysdate())";

	/**
	 * Save basic details
	 */
	public static final String SAVE_BASIC_DETAILS = "INSERT INTO BASIC_DETAIL (FIRST_NAME,"
			+ "MIDDLE_NAME,LAST_NAME,PHONE_NUMBER,EMAIL,ADDRESS,CITY,"
			+ "STATE,COUNTRY,PIN_CODE,FAX,UPDATED_BY,CREATED_BY,UPDATED_DATE,CREATED_DATE)"
			+ "VALUES(:firstName,:middleName,:lastName,:phoneNumber,:email,:address,"
			+ ":city,:state,:country,:pin,:fax,'admin','admin',sysdate(),sysdate())";

	/**
	 * Save Client
	 */
	public static final String SAVE_CLIENT = "INSERT INTO CLIENT (BASIC_DETAIL_ID,"
			+ "USER_NAME,PASSWORD,PLAN_TYPE,STATUS,UPDATED_BY,CREATED_BY,UPDATED_DATE,CREATED_DATE)"
			+ "VALUES(:basicDetailId,:userName,:password,:planType,:status,'admin','admin',sysdate(),sysdate())";
	/**
	 * Update Client
	 */
	public static final String UPDATE_CLIENT = "UPDATE CLIENT SET USER_NAME = :userName, "
			+ "STATUS = :status,UPDATED_DATE = sysdate() WHERE ID = :ID";

	/**
	 * Update Basic details
	 */
	public static final String UPDATE_BASIC_DETAILS = "UPDATE BASIC_DETAIL SET FIRST_NAME = :firstName,MIDDLE_NAME = :middleName,"
			+ "LAST_NAME = :lastName,PHONE_NUMBER = :phoneNumber,EMAIL = :email,ADDRESS = :address,CITY = :city,"
			+ "STATE = :state,COUNTRY = :country,PIN_CODE = :pin,FAX = :fax,UPDATED_DATE = sysdate() WHERE ID = :ID";

	/**
	 * Get Client
	 */
	public static final String GET_CLIENT = "SELECT C.ID, C.USER_NAME, C.STATUS, C.PLAN_TYPE, BD.ID BID, BD.FIRST_NAME, BD.MIDDLE_NAME, "
			+ "BD.LAST_NAME, BD.PHONE_NUMBER, "
			+ "BD.EMAIL, BD.ADDRESS, BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE, BD.FAX "
			+ "FROM CLIENT C, BASIC_DETAIL BD WHERE C.BASIC_DETAIL_ID = BD.ID AND C.ID=:ID";

	/**
	 * TODO
	 */
	public static final String LOGIN_CLIENT = "SELECT C.ID, C.USER_NAME, C.STATUS, C.PLAN_TYPE, BD.ID BID, BD.FIRST_NAME, BD.MIDDLE_NAME, "
			+ "BD.LAST_NAME, BD.PHONE_NUMBER, BD.EMAIL, BD.ADDRESS, BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE,"
			+ " BD.FAX FROM CLIENT C, BASIC_DETAIL BD WHERE C.USER_NAME = :userName AND C.PASSWORD = :password "
			+ "AND BD.ID = C.BASIC_DETAIL_ID";

	/**
	 * Add Store TODO
	 */
	public static final String ADD_STORE = "INSERT INTO store (NAME, CLIENT_ID, CURRENCY, TIME_ZONE, STATUS, UPDATED_BY, CREATED_BY, "
			+ "UPDATED_DATE, CREATED_DATE) VALUES (:storeName, :clientId, :currency, :timeZone, :status, 'admin', 'admin', sysdate(),"
			+ "sysdate())";

	/**
	 * TODO
	 */
	public static final String DEACTIVATE_OR_ACTIVATE_CLIENT = "UPDATE CLIENT SET STATUS = :status WHERE ID = :ID";
	public static final String UPDATE_SOTRE = "update client set user_name= :userName where id = :ID";

	public static final String DEACTIVATE_OR_ACTIVATE_STORE = "UPDATE STORE SET STATUS = :status WHERE ID = :ID";

	public static final String GET_STORE = "SELECT NAME, CLIENT_ID, CURRENCY, TIME_ZONE, STATUS "
			+ "FROM STORE WHERE ID = :ID";
	
	/**
	 * Get User
	 */	
	public static final String GET_USER  = "SELECT U.ID, U.STORE_ID, U.USER_NAME, U.USER_TYPE, U.NEWSLETTER, U.STATUS, BD.ID, BD.FIRST_NAME, BD.MIDDLE_NAME, BD.LAST_NAME, BD.PHONE_NUMBER, "
			+ "BD.EMAIL, BD.ADDRESS, BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE, BD.FAX FROM STORE_USER U, BASIC_DETAIL BD WHERE "
			+ "U.BASIC_DETAIL_ID = BD.ID AND U.ID=:ID";
	
	/**
	 * Get Group
	 */
	public static final String GET_GROUP = "SELECT ID, NAME, CLIENT_ID, STATUS FROM BRAND WHERE ID = :ID";
	
	/**
	 * Add Group
	 */
	public static final String SAVE_GROUP = "INSERT INTO BRAND (NAME, CLIENT_ID, STATUS, UPDATED_BY,CREATED_BY,UPDATED_DATE,CREATED_DATE) "
			+ "VALUES (:groupName, :clientId, :status,'admin','admin',sysdate(),sysdate())";
}
