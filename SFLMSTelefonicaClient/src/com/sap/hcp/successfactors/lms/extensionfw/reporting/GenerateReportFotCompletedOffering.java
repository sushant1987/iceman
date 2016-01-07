/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;

/**
 * @author I319792
 *
 */
public class GenerateReportFotCompletedOffering {



	private Document document;
	//private Element e = null;
	private DocumentBuilder db;
	//Element rootelement = null;

	public Document generateXMLReport(List<ODataEntry> datalist,
			List<Parametrised> parameterizedList) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		}

		document = db.newDocument();
		Element rootNode = document.createElement("grupos");
		document.appendChild(rootNode);

		for(ODataEntry entry : datalist) {
			XMLForitem(entry.getProperties(), rootNode);
		}
		
		

		return document;
	}
	
	private void XMLForitem(Map<String,Object> entry, Element rootNode) {
		if(document!=null){
		Element trainingAction = document.createElement("grupo");
		rootNode.appendChild(trainingAction);
		
		/*String itemCodes = (String) entry.get("ItemCode") == null ? "" : (String) entry.get("ItemCode");
		String itemTitles = (String) entry.get("ItemTitle") == null? "" : (String) entry.get("ItemTitle");
		String observationss = (String) entry.get("Observations") == null? "" : (String) entry.get("Observations");
		String goalss = (String) entry.get("Objectives") == null? "" : (String) entry.get("Objectives");
		//String selfStudyHrss = (String) entry.get("ItemLength") == null ? "" : (String) entry.get("ItemLength");
		String deliveryMethods = (String) entry.get("DelMethod") == null? "" : (String) entry.get("DelMethod");
		//String creditHourss = (String) entry.get("CreditHoursScheduled") == null? "" : (String) entry.get("CreditHoursScheduled");
		//String contactHourss = (String) entry.get("CreditHoursOnline") == null? "" : (String) entry.get("CreditHoursOnline");
		String selfStudyHrss ="";
		String creditHourss = "";
		String contactHourss = "";

		Element itemCode = document.createElement("codAccion");
		itemCode.appendChild(document.createTextNode(itemCodes));
		trainingAction.appendChild(itemCode);
		Element itemTitle = document.createElement("nombreAccion");
		itemTitle.appendChild(document.createTextNode(itemTitles));
		trainingAction.appendChild(itemTitle);
		Element offeringCode = document.createElement("codGrupoAccion");
		trainingAction.appendChild(offeringCode);
		Element simpleMode = document.createElement("modalidadSimple");
		trainingAction.appendChild(simpleMode);
		Element selfStudyHrs = document.createElement("horas");
		selfStudyHrs.appendChild(document.createTextNode(selfStudyHrss));
		simpleMode.appendChild(selfStudyHrs);
		Element deliveryMethod = document.createElement("modalidad");
		deliveryMethod.appendChild(document.createTextNode(deliveryMethods));
		simpleMode.appendChild(deliveryMethod);
		Element mixedMode = document.createElement("ModalidadMixta");
		trainingAction.appendChild(mixedMode);

		Element creditHours = document.createElement("horasPr");
		creditHours.appendChild(document.createTextNode(creditHourss));
		mixedMode.appendChild(creditHours);
		Element contactHours = document.createElement("horasTe");
		contactHours.appendChild(document.createTextNode(contactHourss));
		mixedMode.appendChild(contactHours);
		Element teleMixedMode = document
				.createElement("ModalidadTeleformaciónyMixta");
		trainingAction.appendChild(teleMixedMode);

		Element uRL = document.createElement("uri");
		teleMixedMode.appendChild(uRL);
		Element user = document.createElement("Usuario");
		teleMixedMode.appendChild(user);
		Element password = document.createElement("Password");
		teleMixedMode.appendChild(password);
		Element observations = document.createElement("Observaciones");
		observations.appendChild(document.createTextNode(observationss));
		trainingAction.appendChild(observations);
		Element typeOfParam = document.createElement("tipoAccion");
		trainingAction.appendChild(typeOfParam);
		Element difficultyLevel = document.createElement("nivelFormacion");
		trainingAction.appendChild(difficultyLevel);
		Element goals = document.createElement("objetivos");
		observations.appendChild(document.createTextNode(goalss));
		trainingAction.appendChild(goals);
		Element userMaterial = document.createElement("contenidos");
		trainingAction.appendChild(userMaterial);
		Element participants = document.createElement("empParticipantes");
		trainingAction.appendChild(participants);
		Element taxID = document.createElement("CIF");
		participants.appendChild(taxID);
		Element tradeUnion = document.createElement("infRLT");
		participants.appendChild(tradeUnion);
		Element informTU = document.createElement("InformaRLT");
		tradeUnion.appendChild(informTU);
		Element informTUV = document.createElement("informe");
		tradeUnion.appendChild(informTUV);
		Element confirmTU = document.createElement("valorinf");
		informTUV.appendChild(confirmTU);*/
		}
	}

}
