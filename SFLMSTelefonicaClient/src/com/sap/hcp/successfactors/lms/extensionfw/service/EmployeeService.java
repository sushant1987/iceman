/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.ep.feed.ODataFeed;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Employee;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Item;


/**
 * @author I319792
 *
 */
public interface EmployeeService {
	
	ODataFeed getEmployeeData();

	//void addCustomFieldValue(Employee employeecustomvalue);

	List<Employee> getEmployeeData(String id, String legalEntity, String date,
			String runId);
	
    public void saveCustomFieldValue(Collection<Employee> employeeList);
    
    public Map<String, String> getEmployeeById(List<String> empIds);

}
