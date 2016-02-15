/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.service;

public class ApiValues {

	public static final String ADD_CUSTOM = "/AddCustom";
	public static final String OFFERING = "/OfferingReport";
	public static final String CUSTOMDATA = "/CustomData";
	public static final String OFFERINGPARTICIPANT = "/OfferingParticipantReport";
	public static final String PARAMETRISED = "/Parametrised";
	public static final String PARAMETRISED_BY_TYPE = "/Parametrised/{paramType}";
	public static final String FILE = "/File";
	public static final String EMPLOYEE = "/Employee";
	public static final String GETITEMDATA = "/GetItemData/{legalEntity}/{id}/{date}";
	public static final String GETOFFERINGDATA = "/getOfferingData/";
	public static final String GETCOMPLETEDOFFERINGDATA = "/getCompletedOfferingData";
	public static final String OVERVIEWSCREEN = "/OverviewScreen";
	public static final String REPORT_INFO_DELETE_ALL = "/DeleteAllReports";

	public static final String OVERVIEWSCREEN_TTEMS = "/OverviewScreen/Items/{legalEntity}/{id}/{date}";
	public static final String OVERVIEWSCREEN_NEW_OFFERING = "/OverviewScreen/NewOffering/{legalEntity}/{id}/{days}/{date}";
	public static final String OVERVIEWSCREEN_COMPLETED_OFFERING = "/OverviewScreen/CompletedOffering/{legalEntity}/{id}/{date}";
	public static final String OVERVIEWSCREEN_PARTICIPANTS = "/OverviewScreen/Participants/{companyCode}/{id}/{date}";

	public static final String ITEM = "/Items/{legalEntity}/{id}/{date}/{runId}";
	public static final String NEWOFFERING = "/NewOffering/{legalEntity}/{id}/{days}/{date}/{runId}";
	public static final String COMPLETEDOFFERING = "/CompletedOffering/{legalEntity}/{id}/{date}/{runId}";
	public static final String PARTICIPANTS = "/Participants/{companyCode}/{id}/{date}/{runId}";

	public static final String ITEMREPORT = "/ItemReport/{legalEntity}/{id}/{date}/{runId}";
	public static final String NEWOFFERINGREPORT = "/NewOfferingReport/{legalEntity}/{id}/{days}/{date}/{runId}";
	public static final String COMPLETEDOFFERINGREPORT = "/CompletedOfferingReport/{legalEntity}/{id}/{date}/{runId}";
	public static final String PARTICIPANTSREPORT = "/ParticipantsReport/{companyCode}/{id}/{date}/{runId}";

	public static final String DELETE_ME = "/deleteme";
	public static final String GET_ALL_ITEM_LIST_ID = "/getallitemlistid";
	public static final String GET_ALL_OFFERING_LIST_ID = "/getallofferinglistid";
	public static final String DELETE_OFFERING_ME = "/deleteofferingme";
	public static final String DELETE_EMPLOYEE = "/deleteemployeeme";
	public static final String GET_ALL_EMPLOYEE_LIST_ID = "/getallemployeelistid";
	public static final String DELETE_REPORT_HISTORY = "/deletereporthistory";

	public static final String INVALIDNEWOFFERING = "/Validation/NewOffering/{code}";
	public static final String INVALIDCOMPLETEDOFFERING = "/Validation/CompletedOffering/{code}";

	public static final String CUSTOM_FIELD_CONFIG = "/CustomFieldConfig";
	public static final String CUSTOM_FIELD_EMLOYEE_DATA = "/EmployeeData/{companyCode}";


	public static final String REPORT_INFO_GET = "/reportinfoget/{id}";
	public static final String REPORT_INFO_GET_BY_TYPE = "/reportinfogetbyId/{reportType}";
	public static final String REPORT_INFO = "/ReportInfo/{legalEntity}/{id}/{date}";

}
