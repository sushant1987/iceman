/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hcp.successfactors.lms.extensionfw.multitenancy.CurrentTenantResolver;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientService;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientServiceImpl;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.CustomField;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Employee;

/**
 * @author I319792
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory
			.getLogger(EmployeeServiceImpl.class);

	private static final String XS_CUSTOM_CONFIG = "CustomFieldConfigs";

	private static final String XS_SF_USER = "SFUsers";

	ODataClientService oDataService;
	
	Map<String, String> empIdAndCustom13 = new HashMap<String, String>();

	@Autowired
	CurrentTenantResolver currentTenantResolver;
	
	private synchronized ODataClientService getODataService()
			throws EntityProviderException, IOException, NamingException {

		if (oDataService == null) {
			oDataService = new ODataClientServiceImpl(currentTenantResolver);
			oDataService.getEdm();
		}

		return oDataService; 

	}

	public List<Employee> getEmployeeData(String id, String companyCode,
			String date, String runId) {
		List<Employee> employeeList = new ArrayList<Employee>();
		if (companyCode == null || companyCode.equalsIgnoreCase("none"))
			return employeeList;
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed;
			String filter = "Custom03 eq '" + companyCode + "'";
			feed = oDataAccess.readFeed(XS_SF_USER, null, filter, null);
			for (ODataEntry entry : feed.getEntries()) {
				Employee employeeData = populdateEmployeeData(entry);
				if (validate(employeeData, id, date) == true) {
					employeeList.add(employeeData);
				}
			}
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}
		return employeeList;
	}

	public boolean validate(Employee employee, String id, String date) {
		if (id != null && !"none".equals(id)) {
			if (!id.equalsIgnoreCase(employee.getEmpId()))
				return false;
		}
		if (!"none".equals(date)) {
			try {
				Date startDate = changeDateFormat(date.substring(0, 10));
				Date endDate = changeDateFormat(date.substring(11));
				if (startDate.before(employee.getUpdatedOn())
						|| startDate.equals(employee.getUpdatedOn())) {
					if (endDate.after(employee.getUpdatedOn())
							|| endDate.equals(employee.getUpdatedOn())) {
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}
			} catch (ParseException e) {
				return false;
			}
		}
		return true;
	}

	private Date changeDateFormat(String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {

			Date date = formatter.parse(dateString);
			return date;

		} catch (ParseException e) {
			throw e;
		}

	}

	public ODataFeed getEmployeeData() {
		ODataFeed feed = null;
		try {
			ODataClientService oDataAccess = getODataService();
			feed = oDataAccess.readFeed(XS_SF_USER, null, null, null);
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}
		return feed;
	}

	/**
	 * 
	 * @return
	 * @throws ODataException
	 */
	private Map<String, CustomField> getCustomFieldMap() throws ODataException {
		// read all custom field from odata service <servierURL>/b/UITools.svc
		Map<String, CustomField> customFieldMap = null;
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed = oDataAccess.readFeed(XS_CUSTOM_CONFIG, null, null,
					null);
			customFieldMap = new HashMap<String, CustomField>();
			for (ODataEntry entry : feed.getEntries()) {
				CustomField field = populateCustomFieldConfig(entry);
				customFieldMap.put(field.getName(), field);
			}
		} catch (IOException | NamingException | ODataException e) {
			throw new ODataException(e.getMessage());
		}
		return customFieldMap;
	}

	/**
	 * 
	 * @param entry
	 * @return
	 */
	private CustomField populateCustomFieldConfig(ODataEntry entry) {
		CustomField field = new CustomField();
		Map<String, Object> entryDetail = entry.getProperties();
		field.setId(Long.parseLong((String) entryDetail.get("Id")));
		field.setDataType((String) entryDetail.get("DataType"));
		field.setName((String) entryDetail.get("Name"));
		field.setLength((int) (entryDetail.get("Length") == null ? entryDetail
				.get("Length") : 0));
		field.setHasValuelist((boolean) entryDetail.get("HasValueList"));
		field.setShortDesc((String) entryDetail.get("ShortDesc"));
		return field;
	}

	/**
	 * 
	 * @param entry
	 * @param customFieldMap
	 * @return
	 */
	private Employee populdateEmployeeData(ODataEntry entry) {
		Employee employeedata = new Employee();
		Map<String, Object> entryDetails = entry.getProperties();
//		if (entryDetails.get("EmpId") != null)
//			employeedata.setEmpId((String) entryDetails.get("EmpId"));
//		if (entryDetails.get("DateOfBirth") != null)
//			employeedata.setDateOfBirth(((GregorianCalendar) entryDetails
//					.get("DateOfBirth")).getTime());
//		if (entryDetails.get("FirstName") != null)
//			employeedata.setFirstName((String) entryDetails.get("FirstName"));
//		if (entryDetails.get("LastName") != null)
//			employeedata.setLastName((String) entryDetails.get("LastName"));
//		if (entryDetails.get("OrigHireDate") != null)
//			employeedata.setHireDate(((GregorianCalendar) entryDetails
//					.get("OrigHireDate")).getTime());
//		if (entryDetails.get("Id") != null)
//			employeedata.setId((Long) entryDetails.get("Id"));
//		/*
//		 * employeedata.setEmail((String) entryDetails.get("Email"));
//		 * employeedata.setNickName((String) entryDetails.get("Nickname"));
//		 */
		
		
		
		
		if (entryDetails.get("SSNID") != null)
			employeedata.setSsnid((String) entryDetails.get("SSNID"));

		if (entryDetails.get("Id") != null)
			employeedata.setId((Long) entryDetails.get("Id"));
		if (entryDetails.get("Tenant") != null)
			employeedata.setTenant((String) entryDetails.get("Tenant"));
		if (entryDetails.get("UpdatedOn") != null)
			employeedata.setUpdatedOn(((GregorianCalendar) entryDetails
					.get("UpdatedOn")).getTime());
		if (entryDetails.get("AddressLine1") != null)
			employeedata.setAddressLine1((String) entryDetails
					.get("AddressLine1"));
		if (entryDetails.get("AddressLine2") != null)
			employeedata.setAddressLine2((String) entryDetails
					.get("AddressLine2"));
		if (entryDetails.get("AddressLine3") != null)
			employeedata.setAddressLine3((String) entryDetails
					.get("AddressLine3"));
		if (entryDetails.get("BenchStrength") != null)
			employeedata.setBenchStrength((String) entryDetails
					.get("BenchStrength"));
		if (entryDetails.get("BonusBudgetAmount") != null)
			employeedata.setBonusBudgetAmount((Double) entryDetails
					.get("BonusBudgetAmount"));

		if (entryDetails.get("BonusTarget") != null)
			employeedata.setBonusTarget((Double) entryDetails
					.get("BonusTarget"));
		if (entryDetails.get("BusinessPhone") != null)
			employeedata.setBusinessPhone((String) entryDetails
					.get("businessPhone"));
		if (entryDetails.get("BusinessSegment") != null)
			employeedata.setBusinessSegment((String) entryDetails
					.get("BusinessSegment"));
		if (entryDetails.get("CellPhone") != null)
			employeedata.setCellPhone((String) entryDetails.get("CellPhone"));
		if (entryDetails.get("Citizenship") != null)
			employeedata.setCitizenship((String) entryDetails
					.get("Citizenship"));
		if (entryDetails.get("City") != null)
			employeedata.setCity((String) entryDetails.get("City"));
		if (entryDetails.get("CompensationBonusEligible") != null)
			employeedata.setCompensationBonusEligible((boolean) entryDetails
					.get("CompensationBonusEligible"));
		if (entryDetails.get("CompensationEligible") != null)
			employeedata.setCompensationEligible((boolean) entryDetails
					.get("CompensationEligible"));

		if (entryDetails.get("CompensationReadOnly") != null)
			employeedata.setCompensationReadOnly((boolean) entryDetails
					.get("CompensationReadOnly"));
		if (entryDetails.get("CompensationSalaryEligible") != null)
			employeedata.setCompensationSalaryEligible((boolean) entryDetails
					.get("CompensationSalaryEligible"));

		if (entryDetails.get("compensationSalaryRateType") != null)
			employeedata.setCompensationSalaryRateType((String) entryDetails
					.get("CompensationSalaryRateType"));
		if (entryDetails.get("CompensationSalaryRateUnits") != null)
			employeedata.setCompensationSalaryRateUnits((Long) entryDetails
					.get("CompensationSalaryRateUnits"));
		if (entryDetails.get("CompensationStockEligible") != null)
			employeedata.setCompensationStockEligible((boolean) entryDetails
					.get("CompensationStockEligible"));
		if (entryDetails.get("Competency") != null)
			employeedata.setCompetency((Float) entryDetails.get("Competency"));
		if (entryDetails.get("Country") != null)
			employeedata.setCountry((String) entryDetails.get("Country"));
		if (entryDetails.get("CriticalTalentComments") != null)
			employeedata.setCriticalTalentComments((String) entryDetails
					.get("CriticalTalentComments"));

		if (entryDetails.get("Custom01") != null)
			employeedata.setCustom01((String) entryDetails.get("Custom01"));
		if (entryDetails.get("Custom02") != null)
			employeedata.setCustom02((String) entryDetails.get("Custom02"));
		if (entryDetails.get("Custom03") != null)
			employeedata.setCustom03((String) entryDetails.get("Custom03"));
		if (entryDetails.get("Custom04") != null)
			employeedata.setCustom04((String) entryDetails.get("Custom04"));
		if (entryDetails.get("Custom05") != null)
			employeedata.setCustom05((String) entryDetails.get("Custom05"));
		if (entryDetails.get("Custom06") != null)
			employeedata.setCustom06((String) entryDetails.get("Custom06"));
		if (entryDetails.get("Custom07") != null)
			employeedata.setCustom07((String) entryDetails.get("Custom07"));
		if (entryDetails.get("Custom08") != null)
			employeedata.setCustom08((String) entryDetails.get("Custom08"));
		if (entryDetails.get("Custom09") != null)
			employeedata.setCustom09((String) entryDetails.get("Custom09"));
		if (entryDetails.get("Custom10") != null)
			employeedata.setCustom10((String) entryDetails.get("Custom10"));
		if (entryDetails.get("Custom11") != null)
			employeedata.setCustom11((String) entryDetails.get("Custom11"));
		if (entryDetails.get("Custom12") != null)
			employeedata.setCustom12((String) entryDetails.get("Custom12"));
		if (entryDetails.get("Custom13") != null){
			employeedata.setCustom13((String) entryDetails.get("Custom13"));
			String x = employeedata.getCustom13();
			if(Character.isDigit(x.charAt(0)))
				employeedata.setDocumentType("10");
			else 
				employeedata.setDocumentType("60");
		}
		if (entryDetails.get("Custom14") != null)
			employeedata.setCustom14((String) entryDetails.get("Custom14"));
		if (entryDetails.get("Custom15") != null)
			employeedata.setCustom15((String) entryDetails.get("Custom15"));

		if (entryDetails.get("DateOfBirth") != null)
			employeedata.setDateOfBirth(((GregorianCalendar) entryDetails
					.get("DateOfBirth")).getTime());
		if (entryDetails.get("DateOfCurrentPosition") != null)
			employeedata
					.setDateOfCurrentPosition(((GregorianCalendar) entryDetails
							.get("DateOfCurrentPosition")).getTime());
		if (entryDetails.get("DateOfPosition") != null)
			employeedata.setDateOfPosition(((GregorianCalendar) entryDetails
					.get("DateOfPosition")).getTime());

		if (entryDetails.get("DefaultLocale") != null)
			employeedata.setDefaultLocale((String) entryDetails
					.get("DefaultLocale"));
		if (entryDetails.get("Department") != null)
			employeedata.setDepartment((String) entryDetails.get("Department"));
		if (entryDetails.get("Division") != null)
			employeedata.setDivision((String) entryDetails.get("Division"));
		if (entryDetails.get("Email") != null)
			employeedata.setEmail((String) entryDetails.get("Email"));
		if (entryDetails.get("EmpId") != null){
			employeedata.setEmpId((String) entryDetails.get("EmpId"));
		}
		if (entryDetails.get("EmployeeClass") != null)
			employeedata.setEmployeeClass((String) entryDetails
					.get("EmployeeClass"));
		if (entryDetails.get("Ethnicity") != null)
			employeedata.setEthnicity((String) entryDetails.get("Ethnicity"));
		if (entryDetails.get("Fax") != null)
			employeedata.setFax((String) entryDetails.get("Fax"));

		if (entryDetails.get("FinalJobCode") != null)
			employeedata.setFinalJobCode((String) entryDetails
					.get("FinalJobCode"));
		if (entryDetails.get("FinalJobFamily") != null)
			employeedata.setFinalJobFamily((String) entryDetails
					.get("FinalJobFamily"));
		if (entryDetails.get("FinalJobRole") != null)
			employeedata.setFinalJobRole((String) entryDetails
					.get("FinalJobRole"));
		if (entryDetails.get("FirstName") != null)
			employeedata.setFirstName((String) entryDetails.get("FirstName"));
		if (entryDetails.get("FutureLeader") != null)
			employeedata.setFutureLeader((boolean) entryDetails
					.get("FutureLeader"));
		if (entryDetails.get("Gender") != null)
			employeedata.setGender((String) entryDetails.get("Gender"));
		if (entryDetails.get("HireDate") != null)
			employeedata.setHireDate(((GregorianCalendar) entryDetails
					.get("HireDate")).getTime());
		if (entryDetails.get("ImpactOfLoss") != null)
			employeedata.setImpactOfLoss((String) entryDetails
					.get("ImpactOfLoss"));

		if (entryDetails.get("ImpactOfLossComments") != null)
			employeedata.setImpactOfLossComments((String) entryDetails
					.get("ImpactOfLossComments"));
		if (entryDetails.get("IssueComments") != null)
			employeedata.setIssueComments((String) entryDetails
					.get("IssueComments"));
		if (entryDetails.get("JobCode") != null)
			employeedata.setJobCode((String) entryDetails.get("JobCode"));
		if (entryDetails.get("JobFamily") != null)
			employeedata.setJobFamily((String) entryDetails.get("JobFamily"));
		if (entryDetails.get("JobLevel") != null)
			employeedata.setJobLevel((String) entryDetails.get("JobLevel"));
		if (entryDetails.get("JobRole") != null)
			employeedata.setJobRole((String) entryDetails.get("JobRole"));
		if (entryDetails.get("JobTitle") != null)
			employeedata.setJobTitle((String) entryDetails.get("JobTitle"));
		if (entryDetails.get("KeyPosition") != null)
			employeedata.setKeyPosition((boolean) entryDetails
					.get("KeyPosition"));

		if (entryDetails.get("LastModified") != null)
			employeedata.setLastModified(((GregorianCalendar) entryDetails
					.get("LastModified")).getTime());
		if (entryDetails.get("LastModifiedDateTime") != null)
			employeedata
					.setLastModifiedDateTime(((GregorianCalendar) entryDetails
							.get("LastModifiedDateTime")).getTime());
		if (entryDetails.get("LastModifiedWithTZ") != null)
			employeedata
					.setLastModifiedWithTZ(((GregorianCalendar) entryDetails
							.get("LastModifiedWithTZ")).getTime());
		if (entryDetails.get("LastName") != null)
			employeedata.setLastName((String) entryDetails.get("LastName"));
		if (entryDetails.get("LastReviewDate") != null)
			employeedata.setLastReviewDate((String) entryDetails
					.get("LastReviewDate"));

		if (entryDetails.get("Level") != null)
			employeedata.setLevel((String) entryDetails.get("Level"));
		if (entryDetails.get("LocalCurrencyCode") != null)
			employeedata.setLocalCurrencyCode((String) entryDetails
					.get("LocalCurrencyCode"));
		if (entryDetails.get("Location") != null)
			employeedata.setLocation((String) entryDetails.get("Location"));
		if (entryDetails.get("Lumpsum2Target") != null)
			employeedata.setLumpsum2Target((Double) entryDetails
					.get("Lumpsum2Target"));
		if (entryDetails.get("LumpsumTarget") != null)
			employeedata.setLumpsumTarget((Double) entryDetails
					.get("LumpsumTarget"));
		if (entryDetails.get("Married") != null)
			employeedata.setMarried((boolean) entryDetails.get("Married"));
		if (entryDetails.get("Matrix1Label") != null)
			employeedata.setMatrix1Label((String) entryDetails
					.get("Matrix1Label"));
		if (entryDetails.get("Matrix2Label") != null)
			employeedata.setMatrix2Label((String) entryDetails
					.get("Matrix2Label"));

		if (entryDetails.get("MatrixManaged") != null)
			employeedata.setMatrixManaged((boolean) entryDetails
					.get("MatrixManaged"));
		if (entryDetails.get("MeritEffectiveDate") != null)
			employeedata
					.setMeritEffectiveDate(((GregorianCalendar) entryDetails
							.get("LocalCurrencyCode")).getTime());
		;
		if (entryDetails.get("MeritTarget") != null)
			employeedata.setMeritTarget(((GregorianCalendar) entryDetails
					.get("MeritTarget")).getTime());
		if (entryDetails.get("Mi") != null)
			employeedata.setMi((String) entryDetails.get("Mi"));
		if (entryDetails.get("Minority") != null)
			employeedata.setMinority((boolean) entryDetails.get("Minority"));
		if (entryDetails.get("Nationality") != null)
			employeedata.setNationality((String) entryDetails
					.get("Nationality"));
		if (entryDetails.get("NewToPosition") != null)
			employeedata.setNewToPosition((boolean) entryDetails
					.get("NewToPosition"));
		if (entryDetails.get("Nickname") != null)
			employeedata.setNickname((String) entryDetails.get("Nickname"));

		if (entryDetails.get("Objective") != null)
			employeedata.setObjective((Float) entryDetails.get("Objective"));
		if (entryDetails.get("OrigHireDate") != null)
			employeedata.setOrigHireDate(((GregorianCalendar) entryDetails
					.get("OrigHireDate")).getTime());
		;
		if (entryDetails.get("Password") != null)
			employeedata.setPassword((String) entryDetails.get("Password"));
		if (entryDetails.get("PayGrade") != null)
			employeedata.setPayGrade((String) entryDetails.get("PayGrade"));
		if (entryDetails.get("Performance") != null)
			employeedata
					.setPerformance((Float) entryDetails.get("Performance"));
		if (entryDetails.get("Potential") != null)
			employeedata.setPotential((Float) entryDetails.get("Potential"));
		if (entryDetails.get("PromotionAmount") != null)
			employeedata.setPromotionAmount((Double) entryDetails
					.get("PromotionAmount"));
		if (entryDetails.get("RaiseProrating") != null)
			employeedata.setRaiseProrating((Double) entryDetails
					.get("RaiseProrating"));

		if (entryDetails.get("ReasonForLeaving") != null)
			employeedata.setReasonForLeaving((String) entryDetails
					.get("ReasonForLeaving"));
		if (entryDetails.get("ReloComments") != null)
			employeedata.setReloComments((String) entryDetails
					.get("ReloComments"));
		;
		if (entryDetails.get("ReloLocation") != null)
			employeedata.setReloLocation((String) entryDetails
					.get("ReloLocation"));
		if (entryDetails.get("ReloWilling") != null)
			employeedata.setReloWilling((String) entryDetails
					.get("ReloWilling"));
		if (entryDetails.get("ReviewFreq") != null)
			employeedata.setReviewFreq((String) entryDetails.get("ReviewFreq"));
		if (entryDetails.get("RiskOfLoss") != null)
			employeedata.setRiskOfLoss((String) entryDetails.get("RiskOfLoss"));
		if (entryDetails.get("Salary") != null)
			employeedata.setSalary((Double) entryDetails.get("Salary"));
		if (entryDetails.get("SalaryBudgetExtra2Percentage") != null)
			employeedata.setSalaryBudgetExtra2Percentage((Double) entryDetails
					.get("SalaryBudgetExtra2Percentage"));

		if (entryDetails.get("SalaryBudgetExtraPercentage") != null)
			employeedata.setSalaryBudgetExtraPercentage((double) entryDetails
					.get("SalaryBudgetExtraPercentage"));
		if (entryDetails.get("SalaryBudgetFinalSalaryPercentage") != null)
			employeedata
					.setSalaryBudgetFinalSalaryPercentage((Double) entryDetails
							.get("SalaryBudgetFinalSalaryPercentage"));

		if (entryDetails.get("SalaryBudgetLumpsumPercentage") != null)
			employeedata.setSalaryBudgetLumpsumPercentage((Double) entryDetails
					.get("SalaryBudgetLumpsumPercentage"));
		if (entryDetails.get("SalaryBudgetMeritPercentage") != null)
			employeedata.setSalaryBudgetMeritPercentage((Double) entryDetails
					.get("SalaryBudgetMeritPercentage"));
		if (entryDetails.get("SalaryBudgetPromotionPercentage") != null)
			employeedata
					.setSalaryBudgetPromotionPercentage((Double) entryDetails
							.get("SalaryBudgetPromotionPercentage"));
		if (entryDetails.get("SalaryBudgetTotalRaisePercentage") != null)
			employeedata
					.setSalaryBudgetTotalRaisePercentage((Double) entryDetails
							.get("SalaryBudgetTotalRaisePercentage"));
		if (entryDetails.get("SalaryLocal") != null)
			employeedata.setSalaryLocal((Double) entryDetails
					.get("SalaryLocal"));
		if (entryDetails.get("SalaryProrating") != null)
			employeedata.setSalaryProrating((Double) entryDetails
					.get("SalaryProrating"));

		if (entryDetails.get("Salutation") != null)
			employeedata.setSalutation((String) entryDetails.get("Salutation"));
		if (entryDetails.get("SeatingChart") != null)
			employeedata.setSeatingChart((String) entryDetails
					.get("SeatingChart"));
		;
		if (entryDetails.get("ServiceDate") != null)
			employeedata.setServiceDate(((GregorianCalendar) entryDetails
					.get("ServiceDate")).getTime());
		if (entryDetails.get("State") != null)
			employeedata.setState((String) entryDetails.get("State"));
		if (entryDetails.get("Status") != null)
			employeedata.setStatus((String) entryDetails.get("Status"));
		if (entryDetails.get("StockBudgetOptionAmount") != null)
			employeedata.setStockBudgetOptionAmount((Double) entryDetails
					.get("StockBudgetOptionAmount"));
		if (entryDetails.get("StockBudgetOther1Amount") != null)
			employeedata.setStockBudgetOther1Amount((Double) entryDetails
					.get("StockBudgetOther1Amount"));

		if (entryDetails.get("StockBudgetOther2Amount") != null)
			employeedata.setStockBudgetOther2Amount((Double) entryDetails
					.get("StockBudgetOther2Amount"));
		if (entryDetails.get("StockBudgetOther3Amount") != null)
			employeedata.setStockBudgetOther3Amount((Double) entryDetails
					.get("StockBudgetOther3Amount"));
		if (entryDetails.get("StockBudgetStockAmount") != null)
			employeedata.setStockBudgetStockAmount((Double) entryDetails
					.get("StockBudgetStockAmount"));
		if (entryDetails.get("StockBudgetUnitAmount") != null)
			employeedata.setStockBudgetUnitAmount((Double) entryDetails
					.get("StockBudgetUnitAmount"));
		if (entryDetails.get("Suffix") != null)
			employeedata.setSuffix((String) entryDetails.get("Suffix"));
		if (entryDetails.get("SysCostOfSource") != null)
			employeedata.setSysCostOfSource((Float) entryDetails
					.get("SysCostOfSource"));

		if (entryDetails.get("SysSource") != null)
			employeedata.setSysSource((int) entryDetails.get("SysSource"));
		if (entryDetails.get("SysStartingSalary") != null)
			employeedata.setSysStartingSalary((Float) entryDetails
					.get("SysStartingSalary"));
		;
		if (entryDetails.get("TalentPool") != null)
			employeedata.setTalentPool((String) entryDetails.get("TalentPool"));
		if (entryDetails.get("TimeZone") != null)
			employeedata.setTimeZone((String) entryDetails.get("TimeZone"));
		if (entryDetails.get("Title") != null)
			employeedata.setTitle((String) entryDetails.get("Title"));
		if (entryDetails.get("TotalTeamSize") != null)
			employeedata.setTotalTeamSize((Long) entryDetails
					.get("TotalTeamSize"));
		if (entryDetails.get("UserId") != null)
			employeedata.setUserId((String) entryDetails.get("UserId"));
		if (entryDetails.get("Username") != null)
			employeedata.setUsername((String) entryDetails.get("Username"));
		;
		if (entryDetails.get("VeteranDisabled") != null)
			employeedata.setVeteranDisabled((boolean) entryDetails
					.get("VeteranDisabled"));

		if (entryDetails.get("VeteranMedal") != null)
			employeedata.setVeteranMedal((boolean) entryDetails
					.get("VeteranMedal"));

		if (entryDetails.get("VeteranProtected") != null)
			employeedata.setVeteranProtected((boolean) entryDetails
					.get("VeteranProtected"));

		if (entryDetails.get("VeteranSeparated") != null)
			employeedata.setVeteranSeparated((boolean) entryDetails
					.get("VeteranSeparated"));

		if (entryDetails.get("ZipCode") != null)
			employeedata.setZipCode((String) entryDetails.get("ZipCode"));

		// set the custom field value
		// if (customFieldMap != null && customFieldMap.size() > 0) {
		// for (String name : customFieldMap.keySet()) {
		// CustomField f = customFieldMap.get(name);
		// f.setValue(entryDetails.get(name).toString());
		// }
		// }
		
		return employeedata;
	}

	/**
	 * save custom field
	 */
	
	public void saveCustomFieldValue(Collection<Employee> employeeList) {
		/*try {
			if (employeeList != null && employeeList.size() > 0) {
				for (Employee employee : employeeList) {

					ODataClientService oDataAccess = getODataService();
					Map<String, Object> data = extendedDataToOData(employee);
					oDataAccess.patchEntry(employee.getId(), XS_SF_USER, data);
				}

			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Something wrong getting OData ref", e);
		}*/

	}
	
public Map<String, String> getEmployeeById(List<String> empIds) {
		
		if (empIds.isEmpty())
			return empIdAndCustom13;
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed;
			List<ODataFeed> bigfeed = new ArrayList<ODataFeed>();
			StringBuilder sb = new StringBuilder();
			boolean whetherFirst = true;
			int count = 0;
			for(String empId : empIds) {
				if(!whetherFirst) {
					sb.append(" or EmpId eq '");
					sb.append(empId);
					sb.append("'");
				} else {
					sb.append("EmpId eq '");
					sb.append(empId);
					sb.append("'");
					whetherFirst = false;
				}
				count++;
				if(count == 10){
					String filter = sb.toString();
					feed = oDataAccess.readFeed(XS_SF_USER, null, filter, null);
					bigfeed.add(feed);
					sb = new StringBuilder();
					whetherFirst = true;
					count = 0;
				}
			}
			for(ODataFeed finalfeed : bigfeed){
				for (ODataEntry entry : finalfeed.getEntries()) {
					//populdateEmployeeData(entry);
                    Map<String, Object> entryDetails = entry.getProperties();
                    String cust = (String) entryDetails.get("Custom13");
                    String employeeId = (String) entryDetails.get("EmpId");
                    empIdAndCustom13.put(employeeId ,cust);
				}
			}
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting Employee Data by ID's", e);
		}
		return empIdAndCustom13;
		
	}

	/*private Map<String, Object> extendedDataToOData(Employee extendedData) {
		Map<String, Object> odata = new HashMap<String, Object>();

		List<CustomField> fList = extendedData.getCustomData();
		if (fList != null && fList.size() > 0)
			for (CustomField customField : fList) {
				// the value type may not much , TBD
				odata.put(customField.getName(), customField.getValue());
				odata.put("Id", extendedData.getId());
			}
		return odata;
	}*/

}
