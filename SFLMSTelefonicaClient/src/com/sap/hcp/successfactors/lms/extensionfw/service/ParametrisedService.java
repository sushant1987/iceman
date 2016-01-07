package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.Date;
import java.util.List;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;

public interface ParametrisedService {

	List<Parametrised> getAllParametrisedData();
	
	Parametrised mergeParametrisedData(Parametrised parametrised);
	
	void deleteParametrisedData(Parametrised parametrised);
	
	List<Parametrised> getParametrisedDataByType(String paramType);

	void saveParametriseddata(Parametrised parametrised);

	List<Parametrised> getParametrisedDataForReport(String paramType);
}
