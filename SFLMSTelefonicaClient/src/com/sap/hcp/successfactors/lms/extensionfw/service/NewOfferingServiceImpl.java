package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Instructor;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.InstructorDetail;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Item;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Offering;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.ODataToListConverter;

@Service
public class NewOfferingServiceImpl implements NewOfferingService {

	private static final Logger logger = LoggerFactory
			.getLogger(NewOfferingServiceImpl.class);

	private static final String XS_OFFERING_TABLE = "Offerings";

	ODataClientService oDataService;

	@Autowired
	CurrentTenantResolver currentTenantResolver;
	
	@Autowired
	EmployeeService employeeService;

	private synchronized ODataClientService getODataService()
			throws EntityProviderException, IOException, NamingException {

		if (oDataService == null) {
			oDataService = new ODataClientServiceImpl(currentTenantResolver);
			oDataService.getEdm();
		}

		return oDataService;

	}

	@Override
	public List<Offering> getOfferingData(String id, String legalEntity,
			String date, String days, boolean checkForInstructor) {
		List<Offering> allOfferingData = new ArrayList<Offering>();
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed;
			String filter = null;
			if (!"none".equals(id)) {
				filter = "OfferingID eq '" + id + "'";
				feed = oDataAccess.readFeed(XS_OFFERING_TABLE, "InstructorDetails", filter,
						null);
			} else {
				feed = oDataAccess
						.readFeed(XS_OFFERING_TABLE, "InstructorDetails", null, null);
			}
			List<Offering> offeringDataList = removeDuplicates(feed);
			if (offeringDataList != null && offeringDataList.size() != 0) {
				List<String> empIds = new ArrayList<String>();
				for (Offering offeringData : offeringDataList) {

					for(Instructor instructor : offeringData.getInstructor()) {
						empIds.add(instructor.getInstructorID());
					}
					if (validate(id, legalEntity, date, offeringData)) {
						if(days != null && !"none".equalsIgnoreCase(days)){
							
							if (offeringCheck(offeringData, days)) {
								allOfferingData.add(offeringData);
							}
						}
						else
							allOfferingData.add(offeringData);
					}

				}
				if(checkForInstructor) {
					Map<String, String> empIdAndCustom13 = employeeService.getEmployeeById(empIds);
					for(Offering offer: allOfferingData){
						
						if("true".equals(offer.getInterInsIndicator())) {
							for(Instructor instructor : offer.getInstructor()) {
								instructor.setInstructorID(empIdAndCustom13.get(instructor.getInstructorID()));
							}
						} else if("true".equals(offer.getExtInsIndicator())) {
							for(Instructor instructor : offer.getInstructor()) {
								instructor.setInstructorID(offer.getExternalInstructorID());
							}
							//custom for external
						}
					}
				}
				
			}

		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}

		return allOfferingData;
	}

	public boolean offeringCheck(Offering offering,String days){
		// any offering should be reported "day" days before offering start date
		Date todayDate = new Date(System.currentTimeMillis());
		long diff = (offering.getOfferingStartDate()).getTime() - todayDate.getTime();
		try{
			int day=Integer.parseInt(days);
			if(TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS) > day){
				return false;
			}
			if(TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS) < 0)
				return false;
		}catch(NumberFormatException e){
			return false;
		}
		
		
		
		return true;
		//checks if all the requires data is present
	/*	if(offering.getInstructorID() != null){
			if(offering.getInstructorID().isEmpty()){
				return false;
			}
		}
		else{
			return false;
		}
		if(offering.getFacilityName() != null){
			if(offering.getFacilityName().isEmpty()){
				return false;
			}
		}
		else{
			return false;
		}
		// check to see if the offering length is same as item length 
		/*if((Float)map.get("offeringLength") != (Float)((Item)map.get("item")).getItemLength())
			return true;*/
		// check to see if all the instructors collectively spent correct hours in onsite offering 
		/*if(offering.getDeliveryMethod().equalsIgnoreCase("onsite")){
			float total = 0.0f;
			for(InstructorDetail obj : offering.getInstructor()){
				total = total + obj.getTeachingDuration();
			}
			if(total != offering.getLengthOfOffering() || total != offering.getCreditHours()|| offering.getLengthOfOffering() != (Float)map.get("creditHours"))
				return false;
		}*/
		// check to see if correct hours have been mentioned in the online offerings
		/*else if(((Item)map.get("delMethod")).getDelMethod().equalsIgnoreCase("online")){
			float total = 0.0f;
			for(InstructorDetail obj : ((List<InstructorDetail>)map.get("instructorDetails"))){
				total = total + obj.getTeachingDuration();
			}
			if(total != (Float)map.get("cpeHours"))
				return false;
			total = total + (Float)map.get("contactHours");
			if(total != (Float)map.get("offeringLength"))
				return false;
		}*/
		// check to see if correct hours have been mentioned in the blended offerings
		// problem with blended is that we don't have any way to determine if an instructor was an online or onsite
		// thus 2 checks are missing here which finds out if both kind spent correct hours separately or not
		/*else if(((Item)map.get("delMethod")).getDelMethod().equalsIgnoreCase("blended")){
			float total = 0.0f;
			for(InstructorDetail obj : ((List<InstructorDetail>)map.get("instructorDetails"))){
				total = total + obj.getTeachingDuration();
			}
			total = total + (Float)map.get("contactHours");
			if(total != (Float)map.get("offeringLength"))
				return false;
		}*/
		//return true;
	}
	
	private List<Offering> removeDuplicates(ODataFeed feed){
		List<Offering> offeringlist = new ArrayList<Offering>();
		for (ODataEntry entry : feed.getEntries()){
			Offering offeringData = ODataToListConverter.oDataEntryToOfferingData(entry);
			offeringlist.add(offeringData);
		}
		Map<Integer, Offering> map = new HashMap<Integer, Offering>();
		List<Offering> tempList = new ArrayList<Offering>();
		for(Offering offering : offeringlist){
			if(map.get(offering.getOfferingCode()) == null){
				map.put(offering.getOfferingCode(), offering);
			}
			else{
				if(map.get(offering.getOfferingCode()).getCreationDate().compareTo(offering.getCreationDate()) > 0){
					continue;
				}
				else{
					map.put(offering.getOfferingCode(), offering);
				}
			}
		}
		for(Map.Entry<Integer, Offering> entry : map.entrySet()){
			tempList.add(entry.getValue());
		}
		return tempList;
	}

	private boolean validate(String id, String legalEntity, String date,
			Offering offeringData) {
		if (!"none".equals(id) && !"none".equals(legalEntity)) {
			if (legalEntity.equals(offeringData.getLegalEntity())) {
				return validateDate(date, offeringData);
			} else {
				return false;
			}
		} else if (!"none".equals(id)) {
			return validateDate(date, offeringData);
		} else if (!"none".equals(legalEntity)) {
			return validateDate(date, offeringData);
		} else {
			return validateDate(date, offeringData);
		}

	}

	private boolean validateDate(String date, Offering offeringData) {
		if (!"none".equals(date)) {
			try {
				Date startDate = changeDateFormat(date.substring(0, 10));
				Date endDate = changeDateFormat(date.substring(11));
				if (startDate.before(offeringData.getOfferingStartDate())
						|| startDate
								.equals(offeringData.getOfferingStartDate())) {
					if (endDate.after(offeringData.getOfferingStartDate())
							|| endDate.equals(offeringData
									.getOfferingStartDate())) {
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}

			} catch (ParseException e) {
				logger.error("Something wrong with ParseException", e);
				return false;
			}

		} else {
			return true;
		}
	}

	private Date changeDateFormat(String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		try {

			Date date = formatter.parse(dateString);
			return date;

		} catch (ParseException e) {
			throw e;
		}

	}

	@Override
	public ODataFeed getOfferingData() {
		ODataFeed feed = null;
		try {
			ODataClientService oDataAccess = getODataService();
			feed = oDataAccess.readFeed(XS_OFFERING_TABLE, null, null, null);
		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}
		return feed;
	}

	@Override
	public void addCustomFieldValue(Offering offeringCustomValue) {
		try {
			ODataClientService oDataAccess = getODataService();

			Map<String, Object> data = extendedDataToOData(offeringCustomValue);

			oDataAccess.patchEntry(offeringCustomValue.getId(),
					XS_OFFERING_TABLE, data);

		} catch (Exception e) {
			logger.error("Something wrong getting OData ref", e);
		}

	}

	private Map<String, Object> extendedDataToOData(Offering offeringCustomvalue) {
		Map<String, Object> odata = new HashMap<String, Object>();

		odata.put("CustomData", offeringCustomvalue.getCustomData());
		odata.put("Id", offeringCustomvalue.getId());
		return odata;
	}

	@Override
	public List<Offering> getInvalidOfferingData(String code,
			List<Parametrised> param) {

		List<Offering> allOfferingData = new ArrayList<Offering>();
		try {
			ODataClientService oDataAccess = getODataService();
			ODataFeed feed;

			feed = oDataAccess.readFeed(XS_OFFERING_TABLE, null, null, null);

			if (feed != null) {
				for (ODataEntry entry : feed.getEntries()) {

					Offering offeringData = ODataToListConverter
							.oDataEntryToOfferingData(entry);
					if (!validate(offeringData, code, param)) {
						allOfferingData.add(offeringData);
					}
				}
			}

		} catch (IOException | NamingException | ODataException e) {
			logger.error("Something wrong getting OData ref", e);
		}

		return allOfferingData;

	}

	private boolean validate(Offering offeringData, String code,
			List<Parametrised> params) {
		Map<String, String> mapOfPrams = new HashMap<String, String>();
		for (Parametrised param : params) {
			mapOfPrams.put(param.getParamName(), param.getParamValue());
		}
		if ("04".equals(code)) {
			if (offeringData.getInstructorID() == null) {
				return false;
			}
		} else if ("05".equals(code)) {
			if (mapOfPrams.get("ResPersonNamePf") == null) {
				return false;
			}

		} else if ("06".equals(code)) {
			if (offeringData.getFacilityName() != null) {
				return false;
			}

		} else if ("07".equals(code)) {
			return true;

		}

		return true;

	}

}
