package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Item;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Offering;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.OfferingParticipant;

public class DisplayColumns {
	
	public List<String> getColumns(Object obj){
		List<String> columnNames = new ArrayList<String>();
		for(Field field : obj.getClass().getDeclaredFields()){
			Column column = field.getAnnotation(Column.class);
			if(column != null){
				columnNames.add(column.name());
			}
		}
		columnNames.remove("TENANT_ID");
		columnNames.remove("ID");
		return columnNames;
	}
	public List<String> displayColumns(String tableName){
		
		switch(tableName){
		case "item":
			Item it = new Item();
			return getColumns(it);
		case "newoffering":
			Offering of = new Offering();
			return getColumns(of);
		case "completedoffering":
			OfferingParticipant cof = new OfferingParticipant();
			return getColumns(cof);
		case "participant":
			OfferingParticipant opc = new OfferingParticipant();
			return getColumns(opc);
		default:
			return null;
		}
	}
}
