package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.GenerateReport;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.GenerateReportForNewOffering;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.GenerateReportFotCompletedOffering;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.ListToMap;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.ODataToListConverter;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.ReportingValues;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.Validations;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.File;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Item;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.OfferingParticipant;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.OverviewScreen;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.ReportStruct;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.TagMapping;

@Controller
public class OfferingParticipantServlet {

	@Autowired
	OfferingParticipantService offeringparticipantservice;
	
	@Autowired
	ReportStructService reportStructService;
	
	@Autowired
	TagMappingService tagmappingservice;
	
	@Autowired
	FileService fileservice;
	
	@RequestMapping(value = ApiValues.OFFERINGPARTICIPANT, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String generateOfferingParticipantReport(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		ODataFeed itemList;
		itemList = offeringparticipantservice.getOfferingParticipantData();
		List<ODataEntry> dataList=  itemList.getEntries();
		Validations validate = new Validations();
		dataList = validate.validateOfferingParticipant(dataList);
		
		List<ReportStruct> reportStructList;
		reportStructList = reportStructService.getReportStructData();
		
		List<TagMapping> tagMappingList;
		tagMappingList = tagmappingservice.getTagMappingData();
		Map<String, String> tagmap = ListToMap.convertFromListToMap(tagMappingList);
		
		GenerateReport gr = new GenerateReport();
		int flag = gr.generateXMLReport(reportStructList, dataList, tagmap);
		
		File file  = new File();
		file.setFileType("OFFERINGPARTICIPANT");
		file.setCreatedDate(new Date(System.currentTimeMillis()));
		file.setAcceptanceStatus(0);
		file.setStartDateSelected(new Date(System.currentTimeMillis()));
		file.setEndDateSelected(new Date(System.currentTimeMillis()));
		fileservice.insertFileData(file);
		
		response.setContentType("application/json; charset=utf-8");
		if(flag == 1){
			return ReportingValues.ERRORONE;
		}
		
		
		return ReportingValues.SUCCESSONE;
	}
	
	@RequestMapping(value = ApiValues.COMPLETEDOFFERING, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getOfferingParticipantData(@PathVariable String id, @PathVariable String legalEntity, @PathVariable String date, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.registerTypeAdapter(Date.class,
				new UTCDateTypeAdapter()).create();
		ODataFeed feed = offeringparticipantservice.getOfferingParticipantData(id, legalEntity, date);
		List<OfferingParticipant> dataList;
		dataList = ODataToListConverter.convertToOfferingParticipantList(feed);
		response.setContentType("application/json; charset=utf-8");
		return gson.toJson(dataList);
	}
	
	@RequestMapping(value = ApiValues.OVERVIEWSCREEN_COMPLETED_OFFERING, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getCompletedOfferingOverview(@PathVariable String id, @PathVariable String legalEntity, @PathVariable String date, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.registerTypeAdapter(Date.class,
				new UTCDateTypeAdapter()).create();
		ODataFeed feed = offeringparticipantservice.getOfferingParticipantData(id, legalEntity, date);
		List<OfferingParticipant> dataList;
		dataList = ODataToListConverter.convertToOfferingParticipantList(feed);
		List<OverviewScreen> overviewScreenList = new ArrayList<OverviewScreen>();
		createOverviewList(dataList, overviewScreenList);
		response.setContentType("application/json; charset=utf-8");
		return gson.toJson(overviewScreenList);
	}
	
	private void createOverviewList(List<OfferingParticipant> dataList, List<OverviewScreen> overviewScreenList) {
		OverviewScreen o1 = new OverviewScreen();
		OverviewScreen o2 = new OverviewScreen();
		OverviewScreen o3 = new OverviewScreen();
		o1.setId(1L);
		o1.setLegalEntity("AUS");
		o1.setNoUnRepRec(6);
		o1.setReportType("Completed Offering");
		o2.setId(2L);
		o2.setLegalEntity("GER");
		o2.setNoUnRepRec(62);
		o2.setReportType("Completed Offering");
		o3.setId(3L);
		o3.setLegalEntity("TOE");
		o3.setNoUnRepRec(6);
		o3.setReportType("Completed Offering");
		
		overviewScreenList.add(o1);
		overviewScreenList.add(o2);
		overviewScreenList.add(o3);
		overviewScreenList.add(o3);
		
	}
	
	@RequestMapping(value = ApiValues.COMPLETEDOFFERINGREPORT, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String generateNewOfferingReportP(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		ServletOutputStream out = response.getOutputStream();
		List<Parametrised> parameterizedList=new ArrayList<Parametrised>();
		ODataFeed offeringList;
		offeringList = offeringparticipantservice.getOfferingParticipantData();
		List<ODataEntry> dataList=  offeringList.getEntries();
		//Validations validate = new Validations();
		/*dataList = validate.validateNewOffering(dataList);*/
		
		/*List<ReportStruct> reportStructList;
		reportStructList = reportStructService.getReportStructData();
		
		List<TagMapping> tagMappingList;
		tagMappingList = tagmappingservice.getTagMappingData();
		Map<String, String> tagmap = ListToMap.convertFromListToMap(tagMappingList);*/
		
		GenerateReportFotCompletedOffering gr = new GenerateReportFotCompletedOffering();
		Document document = gr.generateXMLReport(dataList, parameterizedList);
		
		/*File file  = new File();
		file.setFileType("NEWOFFERING");
		file.setCreatedDate(new Date(System.currentTimeMillis()));
		file.setAcceptanceStatus(0);
		file.setStartDateSelected(null);
		file.setEndDateSelected(null);
		fileservice.insertFileData(file);
		*/
		response.setContentType("application/json; charset=utf-8");
		
		DOMSource domSource = new DOMSource(document);
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		
		StreamResult result = new StreamResult(bos);
		TransformerFactory tf = TransformerFactory.newInstance();
		/*tf.setAttribute("http://javax.xml.XMLConstants/feature/secure-processing", true);
		tf.setAttribute("http://xml.org/sax/features/external-general-entities", false);
		tf.setAttribute("http://xml.org/sax/features/external-parameter-entities", false);*/
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			
			e.printStackTrace();
		}
		
		byte []array=bos.toByteArray();
		 
		out.write(array, 0, array.length);
		
		
		return ReportingValues.SUCCESSONE;
	}
	
}
