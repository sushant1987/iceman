package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.CustomField;

public interface CustomFieldService {
    
    Map<String, Object> getEmployeeAndCustomFieldData(HttpServletRequest request, String companyCode, int skip, int top);
    
    List<CustomField> getCustomFieldConfig(HttpServletRequest request);
    
    Long countEmployees(String companyCode);
    
    Map<String, String> uploadEmployeeData(List<Map<String, Object>> employeeList) throws Exception;
}
