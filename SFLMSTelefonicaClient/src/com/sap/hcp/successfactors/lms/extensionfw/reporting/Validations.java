package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.olingo.odata2.api.ep.entry.ODataEntry;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.InstructorDetail;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Item;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Offering;

public class Validations{
	public List<ODataEntry> validateItem(List<ODataEntry> dataList){
		for(ODataEntry odataentry : dataList){
			Map<String, Object> map = odataentry.getProperties();
			if(itemCheck(map) == false)
				dataList.remove(odataentry);
		}
		return dataList;
	}
	public List<ODataEntry> validateNewOffering(List<ODataEntry> dataList){
		for(ODataEntry odataentry : dataList){
			Map<String, Object> map = odataentry.getProperties();
			if(offeringCheck(map) == false)
				dataList.remove(odataentry);
		}
		return dataList;
	}
	
	public List<ODataEntry> validateOfferingParticipant(List<ODataEntry> dataList){
		dataList = completedOfferingCheck(dataList);
		return dataList;
	}
	
	public boolean itemCheck(Map map){

		
		return true;
		// check1 to see if item is already been generated
		// to do
		// check for item length
		/*if((Float)map.get("itemLength") != ((Float)map.get("creditHoursOnline") + (Float)map.get("creditHoursScheduled")))
			return false;
		// check for type of item to be passed
		if(!((String)map.get("delMethod")).equalsIgnoreCase("onsite") || !((String)map.get("delMethod")).equalsIgnoreCase("onsite"))
			return false;
		// check for item length
		if((Float)map.get("itemLength") < 2)
			return false;
		return true;*/
	}
	
	
	public boolean offeringCheck(Map map){
		// any offering should be reported 7 days before offering start date
		Date todayDate = new Date(System.currentTimeMillis());
		long diff = ((Date)map.get("startDate")).getTime() - todayDate.getTime();
		if(TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS) < 7)
			return false;
		//checks if all the requires data is present
		if(((List)map.get("instructorDetails")).isEmpty() || ((String)map.get("facility")) == null)
			return false;
		// check to see if the offering length is same as item length 
		/*if((Float)map.get("offeringLength") != (Float)((Item)map.get("item")).getItemLength())
			return true;*/
		// check to see if all the instructors collectively spent correct hours in onsite offering 
		if(((Item)map.get("delMethod")).getDelMethod().equalsIgnoreCase("onsite")){
			Float total = 0.0f;
			for(InstructorDetail obj : ((List<InstructorDetail>)map.get("instructorDetails"))){
				total = total + obj.getTeachingDuration();
			}
			if((!(total.equals((Float)map.get("offeringLength")))) || (!(total.equals((Float)map.get("creditHours")))) || (!((Float)map.get("offeringLength")).equals((Float)map.get("creditHours"))))
				return false;
		}
		// check to see if correct hours have been mentioned in the online offerings
		else if(((Item)map.get("delMethod")).getDelMethod().equalsIgnoreCase("online")){
			Float total = 0.0f;
			for(InstructorDetail obj : ((List<InstructorDetail>)map.get("instructorDetails"))){
				total = total + obj.getTeachingDuration();
			}
			if(!(total.equals((Float)map.get("cpeHours"))))
				return false;
			total = total + (Float)map.get("contactHours");
			if(!(total.equals((Float)map.get("offeringLength"))))
				return false;
		}
		// check to see if correct hours have been mentioned in the blended offerings
		// problem with blended is that we don't have any way to determine if an instructor was an online or onsite
		// thus 2 checks are missing here which finds out if both kind spent correct hours separately or not
		else if(((Item)map.get("delMethod")).getDelMethod().equalsIgnoreCase("blended")){
			Float total = 0.0f;
			for(InstructorDetail obj : ((List<InstructorDetail>)map.get("instructorDetails"))){
				total = total + obj.getTeachingDuration();
			}
			total = total + (Float)map.get("contactHours");
			if(!(total.equals((Float)map.get("offeringLength"))))
				return false;
		}
		return true;
	}
	
	public List<ODataEntry> completedOfferingCheck(List<ODataEntry> dataList){
		// number of participants in an offering should not exceed 25
		/*Map<String,Integer> countmap = new HashMap<String,Integer>();
		int count = 0;
		for(ODataEntry odataentry : dataList){
			Map<String, Object> dataMap = odataentry.getProperties();
			if(((Offering)dataMap.get("offering")).getOfferingCode() != null){
				count = countmap.get(((Offering)dataMap.get("offering")).getOfferingCode());
				countmap.put(((Offering)dataMap.get("offering")).getOfferingCode(), ++count);
			}
			else
				countmap.put(((Offering)dataMap.get("offering")).getOfferingCode(), 1);
		}
		for(Map.Entry<String, Integer> entry : countmap.entrySet()){
			if(entry.getValue() > 25){
				for(ODataEntry odataentry : dataList){
					Map<String, Object> dataMap = odataentry.getProperties();
					if(((Offering)dataMap.get("offering")).getOfferingCode().equalsIgnoreCase(entry.getKey())){
						dataList.remove(odataentry);
					}
				}
			}
		}
		// check to see if correct participant document is being provided or not
		for(ODataEntry odataentry : dataList){
			Map<String, Object> dataMap = odataentry.getProperties();
			if(Character.isDigit(((String)dataMap.get("participantId")).charAt(0)) == true){
				if(!((String)dataMap.get("typeDocument")).equalsIgnoreCase("10")){
					dataList.remove(odataentry);
				}
			}
			else
				if(!((String)dataMap.get("typeDocument")).equalsIgnoreCase("60")){
					dataList.remove(odataentry);
				}
		}*/
		return dataList;
	}
}
