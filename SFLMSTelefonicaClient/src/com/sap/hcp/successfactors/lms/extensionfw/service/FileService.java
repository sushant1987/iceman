package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.File;

public interface FileService {

	List<File> getFileData();
	
	boolean insertFileData(File file);
}
