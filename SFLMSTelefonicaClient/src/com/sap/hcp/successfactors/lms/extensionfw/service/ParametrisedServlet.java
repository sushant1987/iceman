package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;

@Controller
public class ParametrisedServlet {

	@Autowired
	ParametrisedService parametrisedservice;

	@RequestMapping(value = ApiValues.PARAMETRISED, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody List<Parametrised> getParametrisedData(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<Parametrised> parametrisedList = parametrisedservice
				.getAllParametrisedData();
		response.setContentType("application/json; charset=utf-8");
		return parametrisedList;
	}
	
	@RequestMapping(value = ApiValues.PARAMETRISED_BY_TYPE, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody List<Parametrised> getParametrisedDataByType(@PathVariable String paramType,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<Parametrised> parametrisedList = parametrisedservice
				.getParametrisedDataByType(paramType);
		response.setContentType("application/json; charset=utf-8");
		return parametrisedList;
	}

	// @RequestMapping(value = ApiValues.PARAMETRISED, method =
	// RequestMethod.GET, produces = "application/json; charset=utf-8")
	// @ResponseBody
	// public String getAll(HttpServletRequest request, HttpServletResponse
	// response) throws IOException{
	// ParamClass p = new ParamClass();
	// GsonBuilder builder = new GsonBuilder();
	// Gson gson = builder.registerTypeAdapter(Date.class,
	// new UTCDateTypeAdapter()).create();
	// List<Parametrised> parametrisedList;
	// parametrisedList = p.getParametrisedData();
	// response.setContentType("application/json; charset=utf-8");
	// return gson.toJson(parametrisedList);
	// }

	// @RequestMapping(value = ApiValues.PARAMETRISED, method =
	// RequestMethod.POST, produces = "application/json; charset=utf-8")
	// @ResponseBody
	// public void save(@RequestBody Parametrised parametrised,
	// HttpServletRequest request, HttpServletResponse response) {
	// ParamClass p = new ParamClass();
	//
	// try {
	// p.createParameterized(parametrised);
	// } catch (JsonSyntaxException e) {
	// // not able to read the rollback data from body of POST
	// }
	//
	// }

	@RequestMapping(value = ApiValues.PARAMETRISED, method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody Parametrised saveParametrisedData(
			@RequestBody Parametrised parametrised, HttpServletRequest request,
			HttpServletResponse response) {

		Parametrised parametrisedInserted = parametrisedservice
				.mergeParametrisedData(parametrised);
		response.setContentType("application/json; charset=utf-8");
		return parametrisedInserted;
	}
	
	@RequestMapping(value = ApiValues.PARAMETRISED, method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	public @ResponseBody Parametrised updateParametrisedData(
			@RequestBody Parametrised parametrised, HttpServletRequest request,
			HttpServletResponse response) {

		Parametrised parametrisedInserted = parametrisedservice
				.mergeParametrisedData(parametrised);
		response.setContentType("application/json; charset=utf-8");
		return parametrisedInserted;
	}
	
	@RequestMapping(value = ApiValues.PARAMETRISED, method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public @ResponseBody String deleteParametrised(
			@RequestBody Parametrised parametrised, HttpServletRequest request,
			HttpServletResponse response) {

		parametrisedservice
				.deleteParametrisedData(parametrised);
		response.setContentType("application/json; charset=utf-8");
		return "success";
	}
}
