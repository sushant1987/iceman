/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.TagMapping;

public class ListToMap {
	public static Map<String, String> convertFromListToMap(List<TagMapping> list){
		Map<String, String> map = new HashMap<String, String>();
		for(TagMapping obj : list){
			map.put(obj.getColumnName(), obj.getTagName());
		}
		return map;
	}
}
