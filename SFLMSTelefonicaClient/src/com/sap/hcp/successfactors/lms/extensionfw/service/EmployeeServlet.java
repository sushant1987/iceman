/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.w3c.dom.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Employee;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.EmployeeListId;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.OverviewScreen;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.ReportInfo;
import com.sap.hcp.successfactors.lms.extensionfw.reporting.GenerateReportForEmployee;

/**
 * @author I319792
 *
 */
@Controller
public class EmployeeServlet extends MultiActionController{
    
    /**
     * logger
     */
    private static final Logger employeeservletLogger = LoggerFactory.getLogger(EmployeeServlet.class);
    
    @Autowired
    EmployeeService employeeService;
    
    @Autowired
    EmployeeListIdService employeelistidservice;
    
    @Autowired
    ParametrisedService parametrisedService;
    
    @Autowired
	ReportInfoService reportInfoService;

	@RequestMapping(value = ApiValues.PARTICIPANTSREPORT, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void generateEmployeeReport(@PathVariable String id,
			@PathVariable String companyCode, @PathVariable String date,
			@PathVariable String runId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		ServletOutputStream out = response.getOutputStream();
		List<Parametrised> parameterizedList = null;
		List<Employee> employeeList;
		Map<String, String> employeeidmap = new HashMap<String, String>();
		HttpSession batman = request.getSession(false);
		if("none".equalsIgnoreCase(runId)){
			if(batman.getAttribute("employeeList") != null){
				employeeList = (List<Employee>)batman.getAttribute("employeeList");
			}
			else{
				employeeList = employeeService.getEmployeeData(id, companyCode, date, runId);
			}
			if(batman.getAttribute("employeeidmap") != null){
				employeeidmap = (Map<String, String>)batman.getAttribute("employeeidmap");
			}
			else{
				List<EmployeeListId> employeelistid;
				employeelistid = employeelistidservice.getAll();
				employeeidmap = convertEmployeeListToMap(employeelistid);
			}
			List<Employee> tempList = new ArrayList<Employee>();
			tempList.addAll(employeeList);

			if (tempList != null) { 
				for (Employee employee : tempList) {
						if(employeeidmap.get(employee.getEmpId()) != null && employeeidmap.get(employee.getEmpId()).equalsIgnoreCase("x")){
							employeeList.remove(employee);
						}
				}
			}
		}else{
			ReportInfo obj = reportInfoService.getById(Long.parseLong(runId));
			employeeList = employeeService.getEmployeeData(obj.getCriteriaId(),
					obj.getLegalEntity(), obj.getDate(), runId);
		}
		
		parameterizedList = parametrisedService.getAllParametrisedData();
		/*List<ODataEntry> dataList=  itemList.getEntries();
		Validations validate = new Validations();
		dataList = validate.validateItem(dataList);*/
		
		/*List<ReportStruct> reportStructList;
		reportStructList = reportStructService.getReportStructData();
		
		List<TagMapping> tagMappingList;
		tagMappingList = tagmappingservice.getTagMappingData();
		Map<String, String> tagmap = ListToMap.convertFromListToMap(tagMappingList);*/
		
		GenerateReportForEmployee gr = new GenerateReportForEmployee();
		Document document = gr.generateXMLReport(employeeList, parameterizedList);
		
		DOMSource domSource = new DOMSource(document);
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		
		StreamResult result = new StreamResult(bos);
		TransformerFactory tf = TransformerFactory.newInstance();
		/*tf.setAttribute("http://javax.xml.XMLConstants/feature/secure-processing", true);
		tf.setAttribute("http://xml.org/sax/features/external-general-entities", false);
		tf.setAttribute("http://xml.org/sax/features/external-parameter-entities", false);	*/	
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "true");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			employeeservletLogger.error("TransformerException exception in class EmployeeServlet of method generateEmployeeReport --> " + e.getMessage());
		}
		
		ReportInfo rinfo = new ReportInfo();
		rinfo.setCreatedDate(new Date(System.currentTimeMillis()));
		if (id != null)
			rinfo.setCriteriaId(id);
		if (companyCode != null)
			rinfo.setLegalEntity(companyCode);
		rinfo.setReportType("Participant");
		rinfo.setDate(date);
		reportInfoService.save(rinfo);
		
		// create entry in employeeListId table
		
		List<EmployeeListId> employeelistid = new ArrayList<EmployeeListId>();
		for (Employee employee : employeeList) {
			EmployeeListId obj = new EmployeeListId();
			obj.setEmployeeId(employee.getEmpId());
			employeelistid.add(obj);
		}
		employeelistidservice.save(employeelistid);
		batman.removeAttribute("employeeList");
		batman.removeAttribute("employeeidmap");
		byte []array=bos.toByteArray();
		 
		out.write(array, 0, array.length);
		
		response.setContentType("application/xml; charset=utf-8");
		
	}
	
	
	
	@RequestMapping(value = ApiValues.OVERVIEWSCREEN_PARTICIPANTS, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getEmployeeOverview(@PathVariable String id, @PathVariable String companyCode, @PathVariable String date, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.registerTypeAdapter(Date.class,
				new UTCDateTypeAdapter()).create();
		
		List<Employee> employeeDataList;
		employeeDataList = employeeService.getEmployeeData(id, companyCode, date, "none");
		List<EmployeeListId> employeeidlist;
		employeeidlist = employeelistidservice.getAll();
		Map<String, String> employeeidmap = new HashMap<String, String>();
		employeeidmap = convertEmployeeListToMap(employeeidlist);
		HttpSession batman = request.getSession(true);
		batman.setAttribute("employeeList", employeeDataList);
		batman.setAttribute("employeeidmap", employeeidmap);
		List<OverviewScreen> overviewScreenList = new ArrayList<OverviewScreen>();
		createOverviewList(employeeDataList, overviewScreenList, companyCode, employeeidmap); 
		response.setContentType("application/json; charset=utf-8");
		return gson.toJson(overviewScreenList);
	}
	
	private void createOverviewList(List<Employee> employeeDataList, List<OverviewScreen> overviewScreenList, String companyCode, Map<String, String> employeeidmap) {
		List<Employee> tempList = new ArrayList<Employee>();
		tempList.addAll(employeeDataList);
		for(Employee employee: tempList){
			if(employeeidmap.get(employee.getEmpId()) != null && employeeidmap.get(employee.getEmpId()).equalsIgnoreCase("x")){
				employeeDataList.remove(employee);
			}
		}
		OverviewScreen os = new OverviewScreen();
		os.setReportType("Participant");
		os.setNoUnRepRec(employeeDataList.size());
		os.setLegalEntity(companyCode);
		overviewScreenList.add(os);
	}
	

	@RequestMapping(value = ApiValues.PARTICIPANTS, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getEmployeeData(@PathVariable String id,
			@PathVariable String companyCode, @PathVariable String date,
			@PathVariable String runId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.registerTypeAdapter(Date.class,
				new UTCDateTypeAdapter()).create();

		List<Employee> employeeList;
		Map<String, String> employeeidmap = new HashMap<String, String>();
		HttpSession batman = request.getSession(false);
		if("none".equalsIgnoreCase(runId)){
			if(batman.getAttribute("employeeList") != null){
				employeeList = (List<Employee>)batman.getAttribute("employeeList");
			}
			else{
				employeeList = employeeService.getEmployeeData(id, companyCode, date, runId);
			}
			if(batman.getAttribute("employeeidmap") != null){
				employeeidmap = (Map<String, String>)batman.getAttribute("employeeidmap");
			}
			else{
				List<EmployeeListId> employeelistid;
				employeelistid = employeelistidservice.getAll();
				employeeidmap = convertEmployeeListToMap(employeelistid);
			}
			List<Employee> tempList = new ArrayList<Employee>();
			tempList.addAll(employeeList);

			if (tempList != null) { 
				for (Employee employee : tempList) {
						if(employeeidmap.get(employee.getEmpId()) != null && employeeidmap.get(employee.getEmpId()).equalsIgnoreCase("x")){
							employeeList.remove(employee);
						}
				}
			}
		}else{
			ReportInfo obj = reportInfoService.getById(Long.parseLong(runId));
			employeeList = employeeService.getEmployeeData(obj.getCriteriaId(),
					obj.getLegalEntity(), obj.getDate(), runId);
		}

		response.setContentType("application/json; charset=utf-8");
		return gson.toJson(employeeList);
	}
    
   /* @RequestMapping(value = ApiValues.EMPLOYEE, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Employee> getEmployeeData(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Employee> employeeDataList;
        employeeDataList = employeeService.getEmployeeData();
        return employeeDataList;
    }*/

    @RequestMapping(value = ApiValues.ADD_CUSTOM, method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void updateCustomData(@RequestBody List<Employee> employeeList, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        employeeService.saveCustomFieldValue(employeeList);

    }

  /*  @RequestMapping(value = "downloadEMPCSV", method = RequestMethod.GET)
    public void downloadEMPCSV(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/comma-separated-values");
        response.setHeader("Content-Disposition", "attachment;filename=custom_data_template.csv");
        try {
            PrintWriter out = response.getWriter();
            
            List<Employee> empList = employeeService.getEmployeeData("none","none","none","none");
            if(empList != null && empList.size() > 0){
                
                //write header
                StringBuffer buf = new StringBuffer("ID,");
                List<CustomField> fields = empList.get(0).getCustomData();
                Collections.sort(fields);
                for (CustomField customField : fields) {
                    buf.append(customField.getName());
                    buf.append(",");
                }
                buf.deleteCharAt(buf.length() - 1);
                out.println(buf.toString());
                
                
                for (Employee employee : empList) {
                    fields = employee.getCustomData();
                    Collections.sort(fields);
                    
                    //write content
                    buf = new StringBuffer(employee.getId() + ",");
                    for (CustomField f : fields) {
                        buf.append(f.getValue());
                        buf.append(",");
                    }
                    buf.deleteCharAt(buf.length() - 1);
                    out.println(buf.toString());
                }
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("Problem updating - data provided is null");
        }
    }*/

    /*@RequestMapping(value = "uploadEMPCSV", method = RequestMethod.POST)
    public void uploadEMPSCV(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("customDataFileUpload") MultipartFile clientFile) throws IOException, ServletException {

        if (!clientFile.isEmpty()) {
            String contentType = clientFile.getContentType();
            if (contentType.equalsIgnoreCase("text/comma-separated-values") || contentType.equalsIgnoreCase("text/csv")) {
                // deal with csv upload
                InputStream is = clientFile.getInputStream();
                Reader reader=new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader bufferReader = null;
                // need to parse the content
                String currentLine = null;
                try{
                bufferReader= new BufferedReader(reader);
             
                currentLine = bufferReader.readLine();
                if(currentLine!=null){
                String[] headers = currentLine.split(",");
                String[] contents = null;
                
                
                Map<Long, Employee> empMap = null;
                // parse the header line
                while ((currentLine = bufferReader.readLine()) != null) {
                    // parse the content
                    contents = currentLine.split(",");

                    if (headers.length != contents.length) {
                        continue;
                    }
                    
                    List<Employee> employeeDataList = employeeService.getEmployeeData("none","none","none","none");
                    empMap = this.convertListToMap(employeeDataList);
                    
                    Employee emp = empMap.get(Long.parseLong(contents[0]));
                    
                    for(int i = 1; i < contents.length; i++){
                        setCustomDataValue(emp, headers[i], contents[i]);
                    }
                }
                
                
                employeeService.saveCustomFieldValue(empMap.values());
                }
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
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
   }*/ 
    
    /**
     * convert list to map
     * @param empList
     * @return
     */
   /* public Map<Long, Employee> convertListToMap(List<Employee> empList){
        if(empList != null){
            Map<Long, Employee> empMap = new HashMap<Long, Employee>();
            for (Employee employee : empList) {
                empMap.put(employee.getId(), employee);
            }
        }
        return null;
    }*/
    public Map<String, String> convertEmployeeListToMap(List<EmployeeListId> list){
		Map<String, String> itemidmap = new HashMap<String, String>();
		for(EmployeeListId employeeid : list){
			String key = String.valueOf(employeeid.getEmployeeId());
			itemidmap.put(key,"x");
		}
		return itemidmap;
	}
	
   /* public void setCustomDataValue(Employee emp, String name, String value){
        List<CustomField> fields = emp.getCustomData();
        if(fields != null){
            for (CustomField customField : fields) {
                if(customField.getName().equals(name)){
                    customField.setValue(value);
                }
            }
        }
    }*/
}
