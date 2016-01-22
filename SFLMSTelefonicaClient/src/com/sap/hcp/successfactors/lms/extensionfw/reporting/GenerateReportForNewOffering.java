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

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Instructor;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Offering;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;

/**
 * @author I319792
 *
 */
public class GenerateReportForNewOffering {

	private Document document;
	private DocumentBuilder db;

	private Logger newOfferingLogger = LoggerFactory.getLogger(GenerateReportForNewOffering.class);
	Map<String, String> parameter = new HashMap<String, String>();

	public Document generateXMLReport(List<Offering> datalist,
			List<Parametrised> parameterizedList, String legalEntity) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		for(Parametrised parametrised : parameterizedList) {
			parameter.put(parametrised.getParamName(), parametrised.getParamValue());
		}
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			newOfferingLogger.error("Parsing exception in class GenerateReportForNewOffering of method generateXMLReport --> " + e.getMessage());
		}

		document = db.newDocument();
		Element rootNode = document.createElement("grupos");
		rootNode.setAttribute("xmlns", "http://www.fundaciontripartita.es/schemas");
		document.appendChild(rootNode);

		
		for(Offering entry : datalist) {
			xmlForOffering(entry, rootNode, legalEntity);
		}

		return document;
	}
	
	private void xmlForOffering(Offering entry, Element rootNode, String legalEntity) {
		String taxid = null;
		String nombreCentro = null;
		String taxIdLoc = null;
		String taxIdPlatform = null;
		if("Tsol".equals(legalEntity)) {
			taxid = parameter.get("TaxIDFacilityTsol");
			nombreCentro = parameter.get("nombreCentroTsol");
			taxIdLoc = parameter.get("TaxIDCodeLocTsol");
			taxIdPlatform = parameter.get("TaxIdPlatformTsol");
		}
		if("TdE".equals(legalEntity)) {
			taxid = parameter.get("TaxIDFacilityTdE");
			nombreCentro = parameter.get("nombreCentroTdE");
			taxIdLoc = parameter.get("TaxIDCodeLocTdE");
			taxIdPlatform = parameter.get("TaxIdPlatformTdE");
		}
		if("TME".equals(legalEntity)) {
			taxid = parameter.get("TaxIDFacilityTME");
			nombreCentro = parameter.get("nombreCentroTME");
			taxIdLoc = parameter.get("TaxIDCodeLocTME");
			taxIdPlatform = parameter.get("TaxIdPlatformTME");
		}
		
		if(document!=null){
		Element group = document.createElement("grupo");
		rootNode.appendChild(group);
		
		if(entry.getItemCode() != null){
			Element itemId = document.createElement("idAccion");
			itemId.appendChild(document.createTextNode(entry.getItemCode1()));
			group.appendChild(itemId);
		}
		
		if(entry.getOfferingId() != null){
			Element offeringId = document.createElement("idGrupo");
			offeringId.appendChild(document.createTextNode(String.valueOf(entry.getOfferingId())));
			group.appendChild(offeringId);
		}
		
		//come from item
		if(entry.getItemTitle() != null) {
			Element descElement = document.createElement("descripcion");
			descElement.appendChild(document.createTextNode(entry.getItemTitle()));
			group.appendChild(descElement);
		}
		
		if(parameter.get("PrivateContribution") != null) {
			Element privateContru = document.createElement("cumAportPrivada");
			privateContru.appendChild(document.createTextNode(parameter.get("PrivateContribution")));
			group.appendChild(privateContru);
		}
		
		Element trainingInfo = document.createElement("tipoFormacion");
		group.appendChild(trainingInfo);
		
		/*if(entry.getInterInsIndicator() != null){
			Element intInst = document.createElement("mediosPropios");
			intInst.appendChild(document.createTextNode(entry.getInterInsIndicator()));
			trainingInfo.appendChild(intInst);
		}*/
		if(entry.getInterInsIndicator() != null){
			Element intInst = document.createElement("medios");
			intInst.appendChild(document.createTextNode("Propios"));
			trainingInfo.appendChild(intInst);
		}
		/*if(parameter.get("PrivateContribution") != null) {
			Element organizingEntity = document.createElement("mediosEntidadOrganizadora");
			organizingEntity.appendChild(document.createTextNode(parameter.get("PrivateContribution")));
			trainingInfo.appendChild(organizingEntity);
		}
		if(entry.getExtInsIndicator() != null){
			Element extInst = document.createElement("mediosCentro");
			extInst.appendChild(document.createTextNode(entry.getExtInsIndicator()));
			trainingInfo.appendChild(extInst);
		}*/
		
		Element noOfParticipant = document.createElement("NumeroParticipante");
		if("ONLINE".equals(entry.getDeliveryMethod())) {
			noOfParticipant.appendChild(document.createTextNode("80"));
		} else {
			noOfParticipant.appendChild(document.createTextNode("25"));
		}
		group.appendChild(noOfParticipant);
		
		
		if(entry.getOfferingStartDate() != null){
		Element startDate = document.createElement("fechaInicio");
		startDate.appendChild(document.createTextNode(ItemUtil.dateConverter(entry.getOfferingStartDate())));
		group.appendChild(startDate);
		}
		
		if(entry.getOfferingEndDate() != null){
		Element endDate = document.createElement("fechaFin");
		endDate.appendChild(document.createTextNode(ItemUtil.dateConverter(entry.getOfferingEndDate())));
		group.appendChild(endDate);
		}
		
		if(entry.getScheduleOfferingContact() != null) {
			Element responsible = document.createElement("responsable");
			responsible.appendChild(document.createTextNode(entry.getScheduleOfferingContact()));
			group.appendChild(responsible);
		}
		
		if(parameter.get("ContactNumber") != null) {
			Element phone = document.createElement("telefonoContacto");
			phone.appendChild(document.createTextNode(parameter.get("ContactNumber")));
			group.appendChild(phone);
		}
		
		
		if(!"ONLINE".equals(entry.getDeliveryMethod())) {
			Element facilityDetail = document.createElement("jornadaPresencial");
			group.appendChild(facilityDetail);
			Element center = document.createElement("centro");
			facilityDetail.appendChild(center);
			
			if(taxid != null) {
				Element taxId = document.createElement("cif");
				taxId.appendChild(document.createTextNode(taxid));
				center.appendChild(taxId);
			}
			
			if(nombreCentro != null) {
				Element facilityName = document.createElement("nombreCentro");
				facilityName.appendChild(document.createTextNode(nombreCentro));
				center.appendChild(facilityName);
			}
			
			Element trainingLocation = document.createElement("lugarImparticion");
			facilityDetail.appendChild(trainingLocation);
			
			if(entry.getFacilityComments() != null) {
				Element taxIdLocation = document.createElement("cif");
				taxIdLocation.appendChild(document.createTextNode(entry.getFacilityComments()));
				trainingLocation.appendChild(taxIdLocation);
			}
			
			if(entry.getFacilityDesc() != null) {
				Element location = document.createElement("nombreCentro");
				location.appendChild(document.createTextNode(entry.getFacilityDesc()));
				trainingLocation.appendChild(location);
			}
			
			
			if(entry.getFacilityAddress() != null) {
				Element address = document.createElement("direccionDetallada");
				address.appendChild(document.createTextNode(entry.getFacilityAddress()));
				trainingLocation.appendChild(address);
			}
			
			if(entry.getFacilityPostal() != null){
				Element postalCode = document.createElement("codPostal");
				postalCode.appendChild(document.createTextNode(entry.getFacilityPostal()));
				trainingLocation.appendChild(postalCode);
			}
			
			if(entry.getFacilityCity() != null) {
				Element city = document.createElement("localidad");
				city.appendChild(document.createTextNode(entry.getFacilityCity()));
				trainingLocation.appendChild(city);
			}
			
			if(entry.getFacilityCountry() != null) {
				Element country = document.createElement("pais");
				country.appendChild(document.createTextNode(entry.getFacilityCountry()));
				trainingLocation.appendChild(country);
			}
			
			Element schedule = document.createElement("horario");
			facilityDetail.appendChild(schedule);
			
			// conditional
			
			if(entry.getCreditHours() != null) {
				Element itemHrs = document.createElement("horaTotales");
				
				Double itemHours=entry.getCreditHours();
				if(itemHours!=null){
					if(itemHours>itemHours.intValue())
						itemHrs.appendChild(document.createTextNode(String.valueOf(itemHours)));
					else
						itemHrs.appendChild(document.createTextNode(String.valueOf(itemHours.intValue())));
				
					schedule.appendChild(itemHrs);
				}
			}
			
			if(entry.getFirstDayMorningStartDateTime() != null){
				Element startTime = document.createElement("horaInicioMañana");
				startTime.appendChild(document.createTextNode(ItemUtil.dateConvertCet(entry.getFirstDayMorningStartDateTime())));
				schedule.appendChild(startTime);
			}
			
			if(entry.getFirstDayMorningEndDateTime() != null){
				Element endtime = document.createElement("horaFinMañana");
				if(entry.getFirstDayAfternoonStartDateTime() != null) {
					endtime.appendChild(document.createTextNode(ItemUtil.dateConvert(entry.getFirstDayMorningEndDateTime())));
				} else {
					String dte = ItemUtil.dateConvertCet(entry.getFirstDayMorningEndDateTime());
					
					if("04:00".equals(dte)) {
						Element startAfter = document.createElement("horaInicioTarde");  
						startAfter.appendChild(document.createTextNode("03:01"));
						schedule.appendChild(startAfter);
						Element endAfter = document.createElement("horaFinTarde");
						endAfter.appendChild(document.createTextNode("04:00"));
						schedule.appendChild(endAfter);
						dte = "03:00";
					}
					endtime.appendChild(document.createTextNode(dte));
					
				}
				schedule.appendChild(endtime);
			}
			
			if(entry.getFirstDayAfternoonStartDateTime() != null){
				Element startAfter = document.createElement("horaInicioTarde");  
				startAfter.appendChild(document.createTextNode(ItemUtil.dateConvert(entry.getFirstDayAfternoonStartDateTime())));
				schedule.appendChild(startAfter);
			}
			
			if(entry.getFirstDayAfternoonEndDateTime() != null){
				Element endAfter = document.createElement("horaFinTarde");
				endAfter.appendChild(document.createTextNode(ItemUtil.dateConvertCet(entry.getFirstDayAfternoonEndDateTime())));
				schedule.appendChild(endAfter);
			}
			
			if(entry.getDaysOfTeaching() != null){
				Element daysOfTeach = document.createElement("dias");
				daysOfTeach.appendChild(document.createTextNode(String.valueOf(entry.getDaysOfTeaching())));
				schedule.appendChild(daysOfTeach);
			}
			
		}
		
		//online
		
		/*Element daysOfTeach = document.createElement("dias");
		if(entry.getDaysOfTeaching() != null)
		daysOfTeach.appendChild(document.createTextNode(String.valueOf(entry.getDaysOfTeaching())));
		schedule.appendChild(daysOfTeach);*/
		
		
		if("ONLINE".equals(entry.getDeliveryMethod()) || "BLENDED".equals(entry.getDeliveryMethod())) {
			Element onlineCourses = document.createElement("distanciaTeleformacion");
			group.appendChild(onlineCourses);
			Element teleassistance = document.createElement("asistenciaTeleformacion");
			onlineCourses.appendChild(teleassistance);
			
			Element centre = document.createElement("Centro");
			teleassistance.appendChild(centre);
			if(taxIdPlatform != null) {
				Element taxIdOnline = document.createElement("cif");
				taxIdOnline.appendChild(document.createTextNode(taxIdPlatform));
				centre.appendChild(taxIdOnline);
			}
			
			if(parameter.get("ResPersonNamePf") != null) {
				Element centreName = document.createElement("nombreCentro");
				centreName.appendChild(document.createTextNode(parameter.get("ResPersonNamePf")));
				centre.appendChild(centreName);
			}
			
			if(parameter.get("AddressFacilityPf") != null) {
				Element addressOnline = document.createElement("direccionDetallada");
				addressOnline.appendChild(document.createTextNode(parameter.get("AddressFacilityPf")));
				centre.appendChild(addressOnline);
			}
			if(parameter.get("AddressPFPOCode") != null) {
				Element postCode = document.createElement("codPostal");
				postCode.appendChild(document.createTextNode(parameter.get("AddressPFPOCode")));
				centre.appendChild(postCode);
			}
			
			if(parameter.get("AddressPFCity") != null) {
				Element state = document.createElement("localidad");
				state.appendChild(document.createTextNode(parameter.get("AddressPFCity")));
				centre.appendChild(state);
			}
			
			if(parameter.get("AddressPFCountry") != null) {
				Element countryOnline = document.createElement("pais");
				countryOnline.appendChild(document.createTextNode(parameter.get("AddressPFCountry")));
				centre.appendChild(countryOnline);
			}
			
			if(parameter.get("AddressPFTelephone") != null) {
				Element telephone = document.createElement("telefono");
				telephone.appendChild(document.createTextNode(parameter.get("AddressPFTelephone")));
				teleassistance.appendChild(telephone);
			}
			
			Element schedule = document.createElement("horario");
			onlineCourses.appendChild(schedule);
			
			// conditional
			
			if(entry.getCreditHours() != null) {
				Element itemHrs = document.createElement("horaTotales");
				
				Double itemHours=entry.getCreditHours();
				if(itemHours!=null){
					if(itemHours>itemHours.intValue())
						itemHrs.appendChild(document.createTextNode(String.valueOf(itemHours)));
					else
						itemHrs.appendChild(document.createTextNode(String.valueOf(itemHours.intValue())));
				
					schedule.appendChild(itemHrs);
				}
			}
			
			if(entry.getFirstDayMorningStartDateTime() != null){
			Element startTime = document.createElement("horaInicioMañana");
			startTime.appendChild(document.createTextNode(ItemUtil.dateConvertCet(entry.getFirstDayMorningStartDateTime())));
			schedule.appendChild(startTime);
			}
			
			if(entry.getFirstDayMorningEndDateTime() != null){
			Element endtime = document.createElement("horaFinMañana");
			if(entry.getFirstDayAfternoonStartDateTime() != null) {
				endtime.appendChild(document.createTextNode(ItemUtil.dateConvert(entry.getFirstDayMorningEndDateTime())));
			} else {
				endtime.appendChild(document.createTextNode(ItemUtil.dateConvertCet(entry.getFirstDayMorningEndDateTime())));
			}
			schedule.appendChild(endtime);
			}
			
			if(entry.getFirstDayAfternoonStartDateTime() != null){
				Element startAfter = document.createElement("horaInicioTarde");
				startAfter.appendChild(document.createTextNode(ItemUtil.dateConvert(entry.getFirstDayAfternoonStartDateTime())));
				schedule.appendChild(startAfter);
			}
			
			if(entry.getFirstDayAfternoonEndDateTime() != null){
				Element endAfter = document.createElement("horaFinTarde");
				endAfter.appendChild(document.createTextNode(ItemUtil.dateConvertCet(entry.getFirstDayAfternoonEndDateTime())));
				schedule.appendChild(endAfter);
			}
			
			if(entry.getDaysOfTeaching() != null) {
				Element daysOfTeach = document.createElement("dias");
				daysOfTeach.appendChild(document.createTextNode(String.valueOf(entry.getDaysOfTeaching())));
				schedule.appendChild(daysOfTeach);
			}
			
			
			
			
			for(Instructor inst : entry.getInstructor()) {
				Element instructor = document.createElement("Tutor");
				onlineCourses.appendChild(instructor);
				
				if(inst.getHoursPerInstructor() != null) {
					Element hrsInst = document.createElement("numeroHoras");
					hrsInst.appendChild(document.createTextNode(inst.getHoursPerInstructor()));
					instructor.appendChild(hrsInst);
				}
				
				if(inst.getInstructorID() != null) {
					Element nationalId = document.createElement("nif");
					nationalId.appendChild(document.createTextNode(inst.getInstructorID()));
					instructor.appendChild(nationalId);
				}
				
				if(inst.getInstructorFName() != null) {
					Element fName = document.createElement("nombre");
					fName.appendChild(document.createTextNode(inst.getInstructorFName()));
					instructor.appendChild(fName);
				}
				
				if(inst.getInstructorLName() != null) {
					Element lName = document.createElement("apellido1");
					lName.appendChild(document.createTextNode(inst.getInstructorLName()));
					instructor.appendChild(lName);
				}
				
				if(inst.getInstructorMName() != null) {
					Element mName = document.createElement("apellido2");
					mName.appendChild(document.createTextNode(inst.getInstructorMName()));
					instructor.appendChild(mName);
				}
			}
			
			if(entry.getObservations() != null ) {
				Element obeservation = document.createElement("Observaciones");
				obeservation.appendChild(document.createTextNode(entry.getObservations()));
				group.appendChild(obeservation);
			}
			
		}
		
		
		
		
		/*Element scheduleonlinee = document.createElement("horario");
		onlineCourses.appendChild(scheduleonlinee);
		//
		
		// conditional
		
		if(entry.getCpeHours() != null) {
			Element cPEHoursOnline = document.createElement("horaTotales");
			cPEHoursOnline.appendChild(document.createTextNode(String.valueOf(entry.getCpeHours())));
			scheduleonlinee.appendChild(cPEHoursOnline);
		}
		
		Element startTimeOnline = document.createElement("horaInicioMañana");
		if(entry.getOfferingStartDate() != null) {
			startTimeOnline.appendChild(document.createTextNode(entry.getOfferingStartDate().toString()));
		}
		scheduleonlinee.appendChild(startTimeOnline);
		
		Element endtimeOnline = document.createElement("horaFinMañana");
		if(entry.getOfferingEndDate() != null) {
			endtimeOnline.appendChild(document.createTextNode(entry.getOfferingEndDate().toString()));
		}
		scheduleonlinee.appendChild(endtimeOnline);*/
		
		/*Element startAftOnline = document.createElement("horaInicioTarde");
		if(entry.getOfferingEndDate() != null) {
			startAftOnline.appendChild(document.createTextNode(entry.getOfferingEndDate().toString()));
		}
		scheduleonlinee.appendChild(startAftOnline);
		
		Element endAftOnline = document.createElement("horaFinTarde");
		if(entry.getOfferingEndDate() != null) {
			endAftOnline.appendChild(document.createTextNode(entry.getOfferingEndDate().toString()));
		}
		scheduleonlinee.appendChild(endAftOnline);*/
		
		/*Element daysofteachOnline = document.createElement("dias");
		if(entry.getDaysOfTeaching() != null) {
			daysofteachOnline.appendChild(document.createTextNode(String.valueOf(entry.getDaysOfTeaching())));
		}
		scheduleonlinee.appendChild(daysofteachOnline);*/
		
		//
		
		
		
		}
		
		
	}

}
