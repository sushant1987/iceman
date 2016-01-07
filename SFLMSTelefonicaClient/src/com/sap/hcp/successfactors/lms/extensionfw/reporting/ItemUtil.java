/**
 * Utility class for Items
 */
package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static String dateConvert(Date date){
		Format formatter=new SimpleDateFormat("HH:mm");
		return formatter.format(date);
	}
	
	public static String dateConverter(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.format(date);
	}

}
