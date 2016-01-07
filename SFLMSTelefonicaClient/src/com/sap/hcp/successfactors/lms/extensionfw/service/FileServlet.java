package com.sap.hcp.successfactors.lms.extensionfw.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileServlet {
	
	@Autowired
	FileService fileservice;
	
	@RequestMapping(value = ApiValues.FILE, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFileData(HttpServletRequest request , HttpServletResponse response){
		return null;
	}
	
}
