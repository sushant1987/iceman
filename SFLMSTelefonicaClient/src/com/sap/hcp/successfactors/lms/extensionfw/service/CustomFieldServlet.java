package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.CustomField;


@Controller
public class CustomFieldServlet extends MultiActionController {
    
    @Autowired
    private CustomFieldService  customFieldService;
    
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomFieldServlet.class);

    @RequestMapping(value = ApiValues.CUSTOM_FIELD_EMLOYEE_DATA, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> getEmployeeData(
            HttpServletRequest request,
            @PathVariable(value = "companyCode") String companyCode,
            @RequestParam(value = "skip", defaultValue = "0", required = false) int skip,
            @RequestParam(value = "top", defaultValue = "100", required = false) int top) {
        
        return customFieldService.getEmployeeAndCustomFieldData(request, companyCode, skip, top);
    }
    
    
    @RequestMapping(value = ApiValues.CUSTOM_FIELD_CONFIG, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<CustomField> getCustomeFieldConfig(HttpServletRequest request){
        return customFieldService.getCustomFieldConfig(request);
    }
    
    @RequestMapping(value = "downloadEMPCSV/{companyCode}", method = RequestMethod.GET)
    public void downloadEMPCSV(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "companyCode") String companyCode) {
        
        response.setContentType("text/comma-separated-values");
        response.setHeader("Content-Disposition", "attachment;filename=custom_data_template.csv");
        
        List<CustomField> customFieldList = customFieldService.getCustomFieldConfig(request);
        
        List<Map<String, Object>> employeeAndCustomFieldList = new ArrayList<Map<String, Object>>();
        
        int dataCount = customFieldService.countEmployees(companyCode).intValue();
        int skip = 0;
        int top = 100;
        
        while (skip < dataCount) {
            Map<String, Object> employeeAndCustomFieldMap = customFieldService.getEmployeeAndCustomFieldData(request, companyCode, skip, top);
            employeeAndCustomFieldList.addAll((List<Map<String, Object>>) employeeAndCustomFieldMap.get("data"));
            skip += top;
        }
        
        PrintWriter out = null;
        try {
            out = response.getWriter();
            
            if(employeeAndCustomFieldList != null && !employeeAndCustomFieldList.isEmpty()){
                
                //write header
                StringBuilder buf = new StringBuilder();
                for (CustomField customField : customFieldList) {
                    buf.append(customField.getName());
                    buf.append(",");
                }
                buf.deleteCharAt(buf.length() - 1);
                out.println(buf.toString());
                
                
                for (Map<String, Object> empMap : employeeAndCustomFieldList) {
                    buf = new StringBuilder();
                    for (CustomField customField : customFieldList) {
                        //write content
                        buf.append(empMap.get(customField.getName()));
                        buf.append(",");
                    }
                    buf.deleteCharAt(buf.length() - 1);
                    out.println(buf.toString());
                }
            }
        } catch (IOException e) {
            logger.error("Problem updating - data provided is null");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
    
    @RequestMapping(value = "uploadEMPCSV", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadEMPSCV(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("customDataFileUpload") MultipartFile clientFile) throws Exception {
        
        Map<String, String> resultMap = new HashMap<String, String>();
        
        if (!clientFile.isEmpty()) {
            String contentType = clientFile.getContentType();
            if (contentType.equalsIgnoreCase("text/comma-separated-values") || contentType.equalsIgnoreCase("text/csv")
        || contentType.equalsIgnoreCase("application/vnd.ms-excel")) {
                // deal with csv upload
                InputStream is = clientFile.getInputStream();
                Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader bufferReader = null;
                // need to parse the content
                String currentLine = null;
                try {
                    bufferReader = new BufferedReader(reader);

                    currentLine = bufferReader.readLine();
                    if (currentLine != null) {
                        String[] headers = currentLine.split(",");
                        String[] contents = null;
                        
                        List<Map<String, Object>> employeeList = new ArrayList<Map<String, Object>>();
                        
                        // parse the header line
                        while ((currentLine = bufferReader.readLine()) != null) {
                            // parse the content
                            contents = currentLine.split(",");

                            if (headers.length != contents.length) {
                                continue;
                            }
                            
                            Map<String, Object> empMap = new HashMap<String, Object>();
                            
                            for (int i = 0; i < contents.length; i++) {
                                empMap.put(headers[i], contents[i]);
                            }
                            employeeList.add(empMap);
                        }
                        
                        resultMap = customFieldService.uploadEmployeeData(employeeList);
                        
                    }
                    
                    return resultMap;
                    
                } catch (Exception e) {
                    
                    resultMap.put("message", "Error " + e.getMessage());
                    resultMap.put("status", "failed");
                    return resultMap;
                } finally {
                    try {
                        if (bufferReader != null) {
                            bufferReader.close();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        }
        resultMap.put("status", "failed");
        resultMap.put("message", "File is empty or file type is not correct");
        return resultMap;
    }
}
