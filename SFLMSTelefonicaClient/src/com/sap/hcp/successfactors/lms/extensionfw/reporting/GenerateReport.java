package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.ReportStruct;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GenerateReport {
	private Document dom;
	private Element e = null;
	private DocumentBuilder db;
	Element rootelement = null;
	
	public int generateXMLReport(List<ReportStruct> arr, List<ODataEntry> datalist, Map<String, String> tagmap){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		}
		dom = db.newDocument();
		createSingleStructure(arr.get(0), arr);  // creates structure of the xml file with one instance
		for(int i=0;i<(datalist.size() - 1);i++){  // this loop will clone the structure created in the previous loop to make the complete file
			rootelement.appendChild(rootelement.getFirstChild().cloneNode(true));
		}
		
		Element elem = (Element) rootelement.getFirstChild();
		for(int i = 0; i < datalist.size(); i++){
			Map<String , Object> map = datalist.get(i).getProperties();
			for(Map.Entry<String, Object> entry : map.entrySet()){
				elem.getElementsByTagName(tagmap.get(entry.getKey()) != null ? tagmap.get(entry.getKey()): entry.getKey()).item(0).appendChild(dom.createTextNode((String)entry.getValue()));
			}
			//Item obj = datalist.get(i);
			/*elem.getElementsByTagName(tagmap.get(datalist.get(i))).item(0).appendChild(dom.createTextNode(obj.getItemCode()));
			elem.getElementsByTagName(tagmap.get(ReportingValues.ITEM_TITLE)).item(0).appendChild(dom.createTextNode(obj.getItemTitle()));
			elem.getElementsByTagName(tagmap.get(ReportingValues.ITEM_LENGTH)).item(0).appendChild(dom.createTextNode(Float.toString(obj.getItemLength())));
			elem.getElementsByTagName(tagmap.get(ReportingValues.DELIVERY_METHOD)).item(0).appendChild(dom.createTextNode(obj.getDelMethod()));
			elem.getElementsByTagName(tagmap.get(ReportingValues.CREDIT_HRS_SCHEDULED)).item(0).appendChild(dom.createTextNode(Float.toString(obj.getCreditHoursScheduled())));
			elem.getElementsByTagName(tagmap.get(ReportingValues.CREDIT_HRS_ONLINE)).item(0).appendChild(dom.createTextNode(Float.toString(obj.getCreditHoursOnline())));
			elem.getElementsByTagName(tagmap.get(ReportingValues.OBSERVATIONS)).item(0).appendChild(dom.createTextNode(obj.getObservations()));
			elem.getElementsByTagName(tagmap.get(ReportingValues.OBJECTIVES)).item(0).appendChild(dom.createTextNode(obj.getObjectives()));
			elem.getElementsByTagName(tagmap.get(ReportingValues.CON_INDEX)).item(0).appendChild(dom.createTextNode(obj.getConIndex()));*/
			
			/*for(CustomData cd: obj.getCd()){
				elem.getElementsByTagName(cdc.getCustomdatadefinition().getCustomFieldName()).item(0).appendChild(dom.createTextNode(cdc.getText()));
			}
			
			for(Parametrised param: paramList){
				elem.getElementsByTagName(param.getParamName()).item(0).appendChild(dom.createTextNode(param.getParamValue()));
			}*/
			elem = (Element) elem.getNextSibling();
		}
		int flag = XMLReportgenerator.generate(dom);
		return flag;
	}
	
	/*public int generateOfferingReport(List<ReportStruct> arr, List<Offering> datalist, Map<String, String> map, List<Parametrised> paramList){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		}
		dom = db.newDocument();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		createSingleStructure(arr.get(0), arr);
		for(int i=0;i<(datalist.size() - 1);i++){  // this loop will clone the structure created in the previous loop to make the complete file
			rootelement.appendChild(rootelement.getFirstChild().cloneNode(true));
		}
		Element elem = (Element) rootelement.getFirstChild();
		for(int i = 0; i < datalist.size(); i++){
			Offering obj = datalist.get(i);
			elem.getElementsByTagName(map.get(ReportingValues.ITEM_CODE)).item(0).appendChild(dom.createTextNode(obj.getItem().getItemCode()));
			elem.getElementsByTagName(map.get(ReportingValues.OFFERING_CODE)).item(0).appendChild(dom.createTextNode(obj.getOfferingCode()));
			elem.getElementsByTagName(map.get(ReportingValues.DESCRIPTION)).item(0).appendChild(dom.createTextNode(obj.getDescription()));
			elem.getElementsByTagName(map.get(ReportingValues.MAXREGISTRATIONCOUNT)).item(0).appendChild(dom.createTextNode(Long.toString(obj.getMaxRegistrationCount())));
			elem.getElementsByTagName(map.get(ReportingValues.STARTDATE)).item(0).appendChild(dom.createTextNode(df.format(obj.getStartDate())));
			elem.getElementsByTagName(map.get(ReportingValues.ENDDATE)).item(0).appendChild(dom.createTextNode(df.format(obj.getEndDate())));
			elem.getElementsByTagName(map.get(ReportingValues.STARTBREAK)).item(0).appendChild(dom.createTextNode(df.format(obj.getStartBreak())));
			elem.getElementsByTagName(map.get(ReportingValues.ENDBREAK)).item(0).appendChild(dom.createTextNode(df.format(obj.getEndBreak())));
			elem.getElementsByTagName(map.get(ReportingValues.OFFERINGLENGTH)).item(0).appendChild(dom.createTextNode(Long.toString(obj.getOfferingLength())));
			elem.getElementsByTagName(map.get(ReportingValues.ONLINETUTORIZEDHOURS)).item(0).appendChild(dom.createTextNode(Long.toString(obj.getOnlineTutorizedHours())));
			elem.getElementsByTagName(map.get(ReportingValues.OBSERVATIONS)).item(0).appendChild(dom.createTextNode(obj.getObservations()));
			
			
			for(CustomDataCopy cdc: obj.getCd()){
				elem.getElementsByTagName(cdc.getCustomdatadefinition().getCustomFieldName()).item(0).appendChild(dom.createTextNode(cdc.getText()));
			}
			
			for(Parametrised param: paramList){
				elem.getElementsByTagName(param.getParamName()).item(0).appendChild(dom.createTextNode(param.getParamValue()));
			}
			elem = (Element) elem.getNextSibling();
		}
		int flag = XMLReportgenerator.generate(dom);
		return 0;
	}
	
	public int generatePartipantsReport(List<ReportStruct> arr, List<Participant> datalist, Map<String, String> map, List<Parametrised> paramList){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		
			e.printStackTrace();
		}
		dom = db.newDocument();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		createSingleStructure(arr.get(0), arr);
		for(int i=0;i<(datalist.size() - 1);i++){  // this loop will clone the structure created in the previous loop to make the complete file
			rootelement.appendChild(rootelement.getFirstChild().cloneNode(true));
		}
		Element elem = (Element) rootelement.getFirstChild();
		for(int i = 0; i < datalist.size(); i++){
			Participant obj = datalist.get(i);
			elem.getElementsByTagName(map.get("participantid")).item(0).appendChild(dom.createTextNode(obj.getParticipantId()));
			elem.getElementsByTagName(map.get("name")).item(0).appendChild(dom.createTextNode(obj.getName()));
			elem.getElementsByTagName(map.get("firstsurname")).item(0).appendChild(dom.createTextNode(obj.getFirstSurname()));
			elem.getElementsByTagName(map.get("secondsurname")).item(0).appendChild(dom.createTextNode(obj.getSecondSurname()));
			elem.getElementsByTagName(map.get("sex")).item(0).appendChild(dom.createTextNode(obj.getSex()));
			elem.getElementsByTagName(map.get("dateofbirth")).item(0).appendChild(dom.createTextNode(df.format(obj.getDateOfBirth())));
			elem.getElementsByTagName(map.get("studylevel")).item(0).appendChild(dom.createTextNode(obj.getStudylevel()));
			elem.getElementsByTagName(map.get("email")).item(0).appendChild(dom.createTextNode(obj.getEmail()));
			elem.getElementsByTagName(map.get("phonenumber")).item(0).appendChild(dom.createTextNode(obj.getPhoneNumber()));
			elem.getElementsByTagName(map.get("contributiongroup")).item(0).appendChild(dom.createTextNode(obj.getCg()));
			for(CustomData cd: obj.getCd()){
				elem.getElementsByTagName().item(0).appendChild(dom.createTextNode());
			}
			for(Parametrised param: paramList){
				elem.getElementsByTagName(param.getParamName()).item(0).appendChild(dom.createTextNode(param.getParamValue()));
			}
			elem = (Element) elem.getNextSibling();
		}
		int flag = XMLReportgenerator.generate(dom);
		return 0;
	}
	
	public int generateCompletedOfferingReport(List<ReportStruct> arr, List<OfferingParticipant> datalist, Map<String, String> map, List<Parametrised> paramList){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {  
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
	
			e.printStackTrace();
		}
		dom = db.newDocument();
		createSingleStructure(arr.get(0), arr);
		for(int i=0;i<(datalist.size() - 1);i++){  // this loop will clone the structure created in the previous loop to make the complete file
			rootelement.appendChild(rootelement.getFirstChild().cloneNode(true));
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Element elem = (Element) rootelement.getFirstChild();
		for(int i = 0; i < datalist.size(); i++){
			OfferingParticipant obj = datalist.get(i);
			elem.getElementsByTagName(map.get("offeringcode")).item(0).appendChild(dom.createTextNode(obj.getOffering().getOfferingCode()));
			elem.getElementsByTagName(map.get("itemcode")).item(0).appendChild(dom.createTextNode(obj.getItem().getItemCode()));
			elem.getElementsByTagName(map.get("participantid")).item(0).appendChild(dom.createTextNode(obj.getParticipantId()));
			elem.getElementsByTagName(map.get("name")).item(0).appendChild(dom.createTextNode(obj.getName()));
			elem.getElementsByTagName(map.get("firstsurname")).item(0).appendChild(dom.createTextNode(obj.getFirstSurname()));
			elem.getElementsByTagName(map.get("secondsurname")).item(0).appendChild(dom.createTextNode(obj.getSecondSurname()));
			elem.getElementsByTagName(map.get("sex")).item(0).appendChild(dom.createTextNode(obj.getSex()));
			elem.getElementsByTagName(map.get("dateofbirth")).item(0).appendChild(dom.createTextNode(df.format(obj.getDateOfBirth())));
			elem.getElementsByTagName(map.get("email")).item(0).appendChild(dom.createTextNode(obj.getEmail()));
			elem.getElementsByTagName(map.get("phonenumber")).item(0).appendChild(dom.createTextNode(obj.getPhoneNumber()));
			
			for(CustomDataCopy cdc: obj.getCd()){
				elem.getElementsByTagName(cdc.getCustomdatadefinition().getCustomFieldName()).item(0).appendChild(dom.createTextNode(cdc.getText()));
			}
			
			for(Parametrised param: paramList){
				elem.getElementsByTagName(param.getParamName()).item(0).appendChild(dom.createTextNode(param.getParamValue()));
			}
			elem = (Element) elem.getNextSibling();
		}
		int flag = XMLReportgenerator.generate(dom);
		return 0;
	}*/
	
	
	public void createSingleStructure(ReportStruct obj,List<ReportStruct> arr){   //recursive function
		if(dom!=null){
		if(dom.getElementsByTagName(obj.getTagName()) != null){
			return;
		}
		/*if(obj.getValue() != null && obj.getShow() == false){  //first check is to see if this is a leaf node and 2nd is the flag to show
			return;
		}*/

		if(obj.getParentTagName().equalsIgnoreCase("na")){    //goes into this if this is the root tag
			if(rootelement == null){   // enters into if root tag does not exist
				rootelement = dom.createElement(obj.getTagName());
				dom.appendChild(rootelement);
			}
			return;
		}
		if(dom.getElementsByTagName(obj.getParentTagName()).getLength() == 0){    //goes into if parent tag of this node does not exist
			ReportStruct temp = null;
			for(ReportStruct sd : arr){
				if(sd.getTagName().equalsIgnoreCase(obj.getParentTagName())){
					temp = sd;
					break;
				}
			}
			createSingleStructure(temp,arr);
		}
		if(dom!=null){
		if(dom.createElement(obj.getTagName())!=null)
		e = dom.createElement(obj.getTagName());
		
		NodeList nl = dom.getElementsByTagName(obj.getParentTagName());
		nl.item(0).appendChild(e);
		}
		}
		return ;
	}
}
