/**
 * Utility class for Items
 */
package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author I319792
 *
 */
public class ItemUtil {
	
	private ItemUtil() {
		
	}
	
	public static String getDeliveryMethod(String deliveryMethod) {
		String methodCode;
		switch (deliveryMethod) {
		case "ONSITE" :
			methodCode = "7";
			break;
			
		case "ONLINE" :
			methodCode = "10";
			break;
			
		case "BLENDED" :
			methodCode = "9";  
			break;
			
		case "VIRTUAL" :
			methodCode = "7";
			break;
			
		default :
			methodCode = "";
				
		}
		return methodCode;
	}
	
	public static String dateConvertCet(Date date){
		DateFormat formatter=new SimpleDateFormat("HH:mm");
		formatter.setTimeZone(TimeZone.getTimeZone("CET"));
		return formatter.format(date);
	}
	
	public static String dateConvert(Date date){
		DateFormat formatter=new SimpleDateFormat("HH:mm");
		return formatter.format(date);
	}
	
	public static String dateConverter(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.format(date);
	}

}
