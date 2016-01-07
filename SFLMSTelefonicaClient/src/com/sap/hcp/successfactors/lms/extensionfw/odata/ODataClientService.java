package com.sap.hcp.successfactors.lms.extensionfw.odata;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;

import javax.naming.NamingException;

import java.io.IOException;
import java.util.Map;

public interface ODataClientService {
	Edm getEdm() throws IOException, NamingException, EntityProviderException;

	ODataFeed readFeed(String entitySetName, String expand, String filter,
			String selectFields) throws IOException, ODataException;

	ODataFeed readFeed(String entitySetName, String expand, String filter,
			String selectFields, String orderBy, int numberResults)
			throws IOException, ODataException;
	
	ODataFeed readFeed(String entitySetName, String expand, String filter,
            String selectFields, String orderBy, int numberResults, int skip)
            throws IOException, ODataException;
	
	Long countFeed(String entitySetName, String filter) throws EdmException, IOException;

	ODataFeed readFeed(String entitySetName, String expand, String filter,
			String selectFields, String orderBy, int numberResults,
			boolean count) throws IOException, ODataException;

	ODataEntry readEntry(String entitySetName, Long keyValue,
			String expandRelationName, String selectFields) throws IOException,
			ODataException;

	ODataEntry patchEntry(Long id, String entitySetName,
			Map<String, Object> data) throws Exception;

}
