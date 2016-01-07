/**
 * Utility class for new offering
 */
package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.util.Calendar;
import java.util.Date;

/**
 * @author I319792
 *
 */
public class OfferingUtil {
	
	private OfferingUtil() {
		
	}

	public static String getWorkingDate(Calendar startCal, Calendar endCal) {

		int f2 = 0, f3 = 0, f4 = 0, f5 = 0, f6 = 0;
		StringBuilder daysWorked = new StringBuilder();
		for (Date date = startCal.getTime(); startCal.before(endCal); startCal
				.add(Calendar.DATE, 1), date = startCal.getTime()) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if ((dayOfWeek == 2) && (f2 == 0)) {
				daysWorked.append("L");
				f2 = 1;
			} else if ((dayOfWeek == 3) && (f3 == 0)) {
				daysWorked.append("M");
				f3 = 1;
			} else if ((dayOfWeek == 4) && (f4 == 0)) {
				daysWorked.append("X");
				f4 = 1;
			} else if ((dayOfWeek == 5) && (f5 == 0)) {
				daysWorked.append("J");
				f5 = 1;
			} else if ((dayOfWeek == 6) && (f6 == 0)) {
				daysWorked.append("V");
				f6 = 1;
			}
		}
		return daysWorked.toString();
	}

	public static String getHour(String hour) {
		String instructorHour;
		instructorHour = hour.substring(hour.indexOf("=") + 1,
				hour.indexOf("=") + 3);
		return instructorHour;
	}

}
