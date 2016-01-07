package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.core.ep.entry.ODataEntryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hcp.successfactors.lms.extensionfw.multitenancy.CurrentTenantResolver;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientService;
import com.sap.hcp.successfactors.lms.extensionfw.odata.ODataClientServiceImpl;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.CustomField;

@Service
public class CustomFieldServiceImpl implements CustomFieldService{
    
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomFieldServiceImpl.class);
            
    private static final String XS_CUSTOM_CONFIG = "CustomFieldConfigs";

    private static final String XS_SF_USER = "SFUsers";
    
    private static final String EXTEND_TABLE_SFUSER = "SFUser";
    
    private static final String SESSION_KEY_CUSTOM_FIELD_CONFIG = "SESSION_KEY_CUSTOM_FIELD_CONFIG";
    
    /**
     * backend service
     */
    ODataClientService oDataService;
    
    /**
     * odata ui backend service
     */
    ODataClientService oDataUIService;

    @Autowired
    CurrentTenantResolver currentTenantResolver;
    
    private Set<String> initEmployeeColumns(){
        
        Set<String> employeeDataColumns = new HashSet<String>();
        employeeDataColumns.add("Id");
        employeeDataColumns.add("FirstName");
        employeeDataColumns.add("LastName");
        employeeDataColumns.add("Gender");
        employeeDataColumns.add("Email");
        employeeDataColumns.add("Title");
        employeeDataColumns.add("JobTitle");
        return employeeDataColumns;
    }
    

    private synchronized ODataClientService getODataService() throws EntityProviderException, IOException,
            NamingException {

        if (oDataService == null) {
            oDataService = new ODataClientServiceImpl(currentTenantResolver);
            oDataService.getEdm();
        }
        return oDataService;

    }

    private synchronized ODataClientService getODataUIService() throws EntityProviderException, IOException,
            NamingException {
        if (oDataUIService == null) {
            oDataUIService = new ODataClientServiceImpl(currentTenantResolver, "SFLMSUI");
            oDataUIService.getEdm();
        }
        return oDataUIService;
    }
    
    @Override
    public Map<String, Object> getEmployeeAndCustomFieldData(HttpServletRequest request, String companyCode, int skip, int top) {
        Map<String, Object> fullData = new HashMap<String, Object>();
        
        //read customconfigFields
        Set<String> employeeDataColumns = this.initEmployeeColumns();
        List<CustomField> fieldList = this.getCustomFieldConfig(request);
        if(fieldList != null){
            for (CustomField customField : fieldList) {
               employeeDataColumns.add(customField.getName());
            }
        }
        StringBuilder columnSels = new StringBuilder();
        for (String col : employeeDataColumns) {
            columnSels.append(col);
            columnSels.append(",");
        }
        columnSels.deleteCharAt(columnSels.length() - 1);
        
        List<Map<String, Object>> employeeData = this.readEmployeeData(companyCode, columnSels.toString(), skip, top);
        fullData.put("data", employeeData);
        
        Long totalCount = this.countEmployees(companyCode);
        fullData.put("count", totalCount);
        return fullData;
    }
    
    @Override
    public List<CustomField> getCustomFieldConfig(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<CustomField> customFieldList = (List<CustomField>) request.getSession().getAttribute(SESSION_KEY_CUSTOM_FIELD_CONFIG);
        if(customFieldList == null){
            customFieldList = this.readCustomFieldConfig();
            request.getSession().setAttribute(SESSION_KEY_CUSTOM_FIELD_CONFIG, customFieldList);
        }
        return customFieldList;
    }
    
    @Override
    public Map<String, String> uploadEmployeeData(List<Map<String, Object>> employeeList) throws Exception {
        
        ODataClientService oDataAccess = getODataService();
        Map<String, String> resultMap = new HashMap<String, String>();
        int successCount = 0;
        int failedCount = 0;
        String firstErrorMessage = "";
        for (Map<String, Object> empMap : employeeList) {
            Long id = Long.valueOf(String.valueOf(empMap.get("Id")));
            empMap.remove("Id");
            try {
                oDataAccess.patchEntry(id, XS_SF_USER, empMap);
                successCount ++;
            } catch (ODataException e) {
                failedCount ++;
                if(failedCount == 1){
                    firstErrorMessage = e.getMessage();
                }
            }
        }
        
        resultMap.put("successCount",""+successCount);
        resultMap.put("failedCount",""+failedCount);
        resultMap.put("firstError", firstErrorMessage);
        return resultMap;
    }
    
    @Override
    public Long countEmployees(String companyCode) {
        Long count = 0L;
        try {
            ODataClientService oDataAccess = getODataService();
            String filter = "Custom03 eq '" + companyCode+"'";
            count = oDataAccess.countFeed(XS_SF_USER, filter);
        } catch (EntityProviderException | IOException | NamingException | EdmException e) {
          
            e.printStackTrace();
        }
        return count;
    }
    
    
    private List<Map<String, Object>> readEmployeeData(String companyCode,String selectFields, int skip, int top){
        List<Map<String, Object>> employeeData = new ArrayList<Map<String, Object>>();
        try {
            ODataClientService oDataAccess = getODataService();
            String filter = "Custom03 eq '" + companyCode+"'";
            ODataFeed feed = oDataAccess.readFeed(XS_SF_USER, null, filter, selectFields, null, top, skip);
            if(feed != null && feed.getEntries() != null){
                for (ODataEntry entry : feed.getEntries()) {
                    employeeData.add(entry.getProperties());
                }
            }
        } catch (IOException | NamingException | ODataException e) {
            logger.error("Something wrong getting OData ref", e);
        }
        
        return employeeData;
    }
    
    public List<CustomField> readCustomFieldConfig( ){
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        //add Id by default
        CustomField field = new CustomField();
        field.setName("Id");
        field.setShortDesc("ID");
        field.setDataType("Int64");
        field.setHasValuelist(false);
        customFieldList.add(field);
        
        try{
            ODataClientService oDataAccess = getODataUIService();
            ODataFeed feed = oDataAccess.readFeed(XS_CUSTOM_CONFIG, "ExtendedTableDetails", null, null);
            if(feed != null && feed.getEntries() != null){
                for (ODataEntry entry : feed.getEntries()) {
                    Map<String, Object> props = entry.getProperties();
                    ODataEntryImpl extendedTable =  (ODataEntryImpl) props.get("ExtendedTableDetails");
                    if(extendedTable != null && EXTEND_TABLE_SFUSER.equals(extendedTable.getProperties().get("Name"))){
                        field = this.populateCustomField(props);
                        customFieldList.add(field);
                    }
                }
            }
        }catch (IOException | NamingException | ODataException e) {
            logger.error("Something wrong getting OData ref", e);
        }
        return customFieldList;
    }
    
    
    private CustomField populateCustomField(Map<String, Object> props){
        CustomField field = new CustomField();
        if(props.get("Id") != null){
            field.setId((Long) props.get("Id"));
        }
        if(props.get("HasValueList") != null){
            field.setHasValuelist((boolean) props.get("HasValueList"));
        }
        if(props.get("DataType") != null){
            field.setDataType((String) props.get("DataType"));
        }
        if(props.get("Name") != null){
            field.setName((String) props.get("Name"));
        }
        if(props.get("ShortDesc") != null){
            field.setShortDesc((String) props.get("ShortDesc"));
        }
        return field;
    }

}
