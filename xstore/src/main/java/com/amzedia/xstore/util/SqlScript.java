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
	public static final String ADD_STORE = "INSERT INTO store (NAME, BRAND_ID, CURRENCY, TIME_ZONE, STATUS, UPDATED_BY, CREATED_BY, "
			+ "UPDATED_DATE, CREATED_DATE) VALUES (:storeName, :groupId, :currency, :timeZone, :status, 'admin', 'admin', sysdate(),"
			+ "sysdate())";

	/**
	 * Activate or Deactivate Client
	 */

	public static final String DEACTIVATE_OR_ACTIVATE_CLIENT = "UPDATE CLIENT SET STATUS = :status WHERE ID = :ID";

	/**
	 * Update Store
	 */
	public static final String UPDATE_STORE = "UPDATE STORE SET NAME = :name, CURRENCY = :currency, TIME_ZONE = :timeZone, STATUS = :status, "
			+ "UPDATED_DATE = sysdate() WHERE ID = :ID";

	/**
	 * Activate or Deactivate Store
	 */
	public static final String DEACTIVATE_OR_ACTIVATE_STORE = "UPDATE STORE SET STATUS = :status WHERE ID = :ID";

	/**
	 * Get Store
	 */
	public static final String GET_STORE = "SELECT NAME, CURRENCY, TIME_ZONE, STATUS "
			+ "FROM STORE WHERE ID = :ID";

	/**
	 * Get Group
	 */
	public static final String GET_GROUP = "SELECT ID, NAME, STATUS FROM BRAND WHERE ID = :ID";

	/**
	 * Add Group
	 */
	public static final String SAVE_GROUP = "INSERT INTO BRAND (NAME, CLIENT_ID, STATUS, UPDATED_BY,CREATED_BY,UPDATED_DATE,CREATED_DATE) "
			+ "VALUES (:groupName, :clientId, :status,'admin','admin',sysdate(),sysdate())";

	/**
	 * Activate or Deactivate the Group
	 */
	public static final String DEACTIVATE_OR_ACTIVATE_GROUP = "UPDATE BRAND SET STATUS = :status WHERE ID = :ID";

	/**
	 * update the group info
	 */
	public static final String UPDATE_GROUP = "UPDATE BRAND SET NAME = :name, STATUS = :status, UPDATED_DATE = sysdate() WHERE ID = :ID";

	/**
	 * Get Customer
	 */
	public static final String GET_CUSTOMER = "SELECT U.ID, U.USER_NAME, U.USER_TYPE, U.NEWSLETTER, U.STATUS, BD.ID BID, BD.FIRST_NAME, BD.MIDDLE_NAME, BD.LAST_NAME, BD.PHONE_NUMBER, "
			+ "BD.EMAIL, BD.ADDRESS, BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE, BD.FAX FROM CUSTOMER U, BASIC_DETAIL BD WHERE "
			+ "U.BASIC_DETAIL_ID = BD.ID AND U.ID=:ID";

	/**
	 * 
	 */
	public static final String ADD_CUSTOMER = "INSERT INTO CUSTOMER (BASIC_DETAIL_ID, BRAND_ID, USER_NAME, PASSWORD, USER_TYPE, NEWSLETTER, "
			+ "STATUS, UPDATED_BY, CREATED_BY, UPDATED_DATE, CREATED_DATE) VALUES (:basicDetailId, :brandId, :userName, :password, :userType,"
			+ ":newsletter, :status, 'admin', 'admin', sysdate(), sysdate())";

	/**
	 * 
	 */
	public static final String LOGIN_CUSTOMER = "SELECT C.ID, C.USER_NAME, C.USER_TYPE, C.NEWSLETTER, C.STATUS, C.BASIC_DETAIL_ID, "
			+ "BD.ID BID, BD.FIRST_NAME, BD.MIDDLE_NAME, BD.LAST_NAME, BD.PHONE_NUMBER, BD.EMAIL, BD.ADDRESS, "
			+ "BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE, BD.FAX FROM CUSTOMER C, BASIC_DETAIL BD WHERE C.USER_NAME = "
			+ ":userName AND C.PASSWORD = :password AND BD.ID = C.BASIC_DETAIL_ID";

	/**
	 * 
	 */
	public static final String GET_GROUPS_BY_CLIENT = "SELECT B.ID BID, B.NAME, B.STATUS, C.ID CID FROM BRAND B, CLIENT C WHERE B.CLIENT_ID = C.ID AND C.ID = :ID";

	public static final String GET_DEACTIVATED_GROUPS_BY_CLIENT = "SELECT B.ID BID, B.NAME, B.STATUS FROM BRAND B, CLIENT C WHERE B.CLIENT_ID = C.ID AND C.ID = :ID AND B.STATUS = FALSE";

	public static final String GET_ACTIVATED_GROUPS_BY_CLIENT = "SELECT B.ID BID, B.NAME, B.STATUS FROM BRAND B, CLIENT C WHERE B.CLIENT_ID = C.ID AND C.ID = :ID AND B.STATUS = TRUE";

	public static final String GET_STORES_BY_GROUP = "SELECT S.ID, S.NAME, S.CURRENCY, S.TIME_ZONE, S.STATUS FROM STORE S, BRAND B WHERE S.BRAND_ID = B.ID AND B.ID = :ID";

	public static final String GET_ACTIVATED_STORES_BY_GROUP = "SELECT S.ID, S.NAME, S.CURRENCY, S.TIME_ZONE, S.STATUS FROM STORE S, BRAND B WHERE S.BRAND_ID = B.ID AND B.ID = :ID AND S.STATUS = TRUE";

	public static final String GET_DEACTIVATED_STORES_BY_GROUP = "SELECT S.ID, S.NAME, S.CURRENCY, S.TIME_ZONE, S.STATUS FROM STORE S, BRAND B WHERE S.BRAND_ID = B.ID AND B.ID = :ID AND S.STATUS = FALSE";

	public static final String GET_CUSTOMERS_BY_GROUP = "SELECT BD.FIRST_NAME, BD.MIDDLE_NAME, BD.LAST_NAME, BD.PHONE_NUMBER, BD.EMAIL, BD.ADDRESS, BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE, BD.FAX, C.ID,"
			+ " C.USER_NAME, C.USER_TYPE, C.NEWSLETTER, C.STATUS FROM CUSTOMER C, BRAND B, BASIC_DETAIL BD WHERE C.BRAND_ID = B.ID AND C.BASIC_DETAIL_ID = BD.ID AND B.ID = :ID";

	public static final String GET_ACTIVATED_CUSTOMERS_BY_GROUP = "SELECT BD.FIRST_NAME, BD.MIDDLE_NAME, BD.LAST_NAME, BD.PHONE_NUMBER, BD.EMAIL, BD.ADDRESS, BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE, BD.FAX, C.ID,"
			+ " C.USER_NAME, C.USER_TYPE, C.NEWSLETTER, C.STATUS FROM CUSTOMER C, BRAND B, BASIC_DETAIL BD WHERE C.BRAND_ID = B.ID AND C.BASIC_DETAIL_ID = BD.ID AND C.STATUS = TRUE AND B.ID = :ID";

	public static final String GET_DEACTIVATED_CUSTOMERS_BY_GROUP = "SELECT BD.FIRST_NAME, BD.MIDDLE_NAME, BD.LAST_NAME, BD.PHONE_NUMBER, BD.EMAIL, BD.ADDRESS, BD.CITY, BD.STATE, BD.COUNTRY, BD.PIN_CODE, BD.FAX, C.ID,"
			+ " C.USER_NAME, C.USER_TYPE, C.NEWSLETTER, C.STATUS FROM CUSTOMER C, BRAND B, BASIC_DETAIL BD WHERE C.BRAND_ID = B.ID AND C.BASIC_DETAIL_ID = BD.ID AND C.STATUS = FALSE AND B.ID = :ID";

	public static final String ADD_PRODUCT = "INSERT INTO PRODUCT (PRODUCT_NAME, DESCRIPTION, STORE_ID, UPDATED_BY, CREATED_BY, UPDATED_DATE, CREATED_DATE) VALUES (:productName, :productDescription, :storeId, 'admin', 'admin', "
			+ "sysdate(), sysdate())";

	/**
	 * Get Tag
	 */
	public static final String GET_TAG = "SELECT ID, NAME, LEVEL, PARENT_ID FROM TAG WHERE ID = :ID";
}
