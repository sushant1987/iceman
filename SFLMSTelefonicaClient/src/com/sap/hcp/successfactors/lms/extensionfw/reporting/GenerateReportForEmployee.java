/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Employee;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;

/**
 * @author I319792
 *
 */
public class GenerateReportForEmployee {

	private Document document;
	private DocumentBuilder db;
	
	private static final Logger employeeLogger = LoggerFactory.getLogger(GenerateReportForEmployee.class);
	
	Map<String, String> parameter = new HashMap<String, String>();
	
	public Document generateXMLReport(List<Employee> datalist,
			List<Parametrised> parameterizedList) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		for(Parametrised parametrised : parameterizedList) {
			parameter.put(parametrised.getParamName(), parametrised.getParamValue());
		}
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			employeeLogger.error("Parsing exception in class GenerateReportForEmployee of method generateXMLReport --> " + e.getMessage());
		}

		document = db.newDocument();
		Element rootNode = document.createElement("participantes");
		rootNode.setAttribute("xmlns", "http://www.fundaciontripartita.es/schemas");		
		document.appendChild(rootNode);
		for(Employee entry : datalist) {
			XMLForemployee(entry, rootNode);
		}
		
		return document;
	}
	
	private void XMLForemployee(Employee employee, Element rootNode) {

		if(document!=null){
		Element trainingAction = document.createElement("participante");
		rootNode.appendChild(trainingAction);
		
		Element participantId = document.createElement("D_DOCUMENTO");
		if(employee.getCustom13() != null)
			participantId.appendChild(document.createTextNode(employee.getCustom13()));
		trainingAction.appendChild(participantId);
		
		Element typeDocument = document.createElement("N_TIPO_DOCUMENTO");
		if(employee.getCustom13() != null){
			String x = employee.getCustom13();
			if(Character.isDigit(x.charAt(0))) {
				typeDocument.appendChild(document.createTextNode("10"));
			} else {
				typeDocument.appendChild(document.createTextNode("60"));
			}
		}
		trainingAction.appendChild(typeDocument); 
		
		Element lName = document.createElement("D_APELLIDO1");
		if(employee.getLastName() != null)
			lName.appendChild(document.createTextNode(employee.getLastName()));
		trainingAction.appendChild(lName);
		
		Element fName = document.createElement("D_NOMBRE");
		if(employee.getFirstName() != null)
			fName.appendChild(document.createTextNode(employee.getFirstName()));
		trainingAction.appendChild(fName);
		
		
		Element ssnid = document.createElement("D_NISS");
		if(employee.getSsnid() != null)
			ssnid.appendChild(document.createTextNode(employee.getSsnid()));
		trainingAction.appendChild(ssnid);
		
		Element gender = document.createElement("B_SEXO");
		if(employee.getGender() != null)	
			gender.appendChild(document.createTextNode(employee.getGender()));
		trainingAction.appendChild(gender);
		
		Element dob = document.createElement("F_NACIMIENTO");
		if(employee.getCustom12() != null)
			dob.appendChild(document.createTextNode((employee.getCustom12().toString())));
		trainingAction.appendChild(dob);
	
		}
		
	}
}
