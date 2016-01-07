package com.sap.hcp.successfactors.lms.extensionfw.odata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.account.TenantContext;
import com.sap.core.connectivity.api.authentication.AuthenticationHeader;
import com.sap.core.connectivity.api.authentication.AuthenticationHeaderProvider;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import com.sap.hcp.successfactors.lms.extensionfw.multitenancy.CurrentTenantResolver;

public class ODataClientServiceImpl implements ODataClientService {

	private static final Logger logger = LoggerFactory.getLogger(ODataClientServiceImpl.class);

	private static final String HTTP_METHOD_PATCH = "PATCH";
	protected static final String HTTP_METHOD_GET = "GET";

	private static final String APPLICATION_JSON = "application/json; charset=utf-8";
	protected static final String APPLICATION_XML = "application/xml";
	protected static final String METADATA = "$metadata";
	protected static final String SEPARATOR = "/";
	protected static final String SERVICES = "services";

	private CurrentTenantResolver currentTenantResolver;
	
	//default destinationName
	private String destinationName = "SFLMS";

	public ODataClientServiceImpl(CurrentTenantResolver currentTenantResolver) {
		super();
		this.currentTenantResolver = currentTenantResolver;
	}
	
	public ODataClientServiceImpl(CurrentTenantResolver currentTenantResolver, String destinationName) {
        super();
        this.currentTenantResolver = currentTenantResolver;
        this.destinationName = destinationName;
    }

	private Map<String, TenantConnectionDetails> detailsForTenant = new HashMap<String, TenantConnectionDetails>();

	@Override
	public synchronized Edm getEdm() throws IOException, NamingException, EntityProviderException {

		String tenantId = currentTenantResolver.getCurrentTenantId();
		TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(tenantId);
		Edm edm = null;
		if (tenantConnectionDetails == null) {
			tenantConnectionDetails = new TenantConnectionDetails();
		}
		try {
			edm = tenantConnectionDetails.getEdm();
			if (edm == null) {

				// look up the connectivity configuration API
				// "connectivityConfiguration"
				Context ctx = new InitialContext();
				ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup("java:comp/env/connectivityConfiguration");

				TenantContext tenantContext = (TenantContext) ctx.lookup("java:comp/env/TenantContext");
				String currentTenantId = tenantContext.getTenant().getAccount().getId();
				// get destination configuration for "SFLMS"
				DestinationConfiguration destConfiguration;
				if (currentTenantId.equals("dev_default")) {
					// local testing
					destConfiguration = configuration.getConfiguration(this.destinationName);
				} else {
					destConfiguration = configuration.getConfiguration(currentTenantId, this.destinationName);
				}

				// get the sflms URL
				tenantConnectionDetails.setUrl(destConfiguration.getProperty("URL"));
				detailsForTenant.put(tenantId, tenantConnectionDetails);

				InputStream content = execute(tenantConnectionDetails.getUrl() + SEPARATOR + METADATA, APPLICATION_XML, HTTP_METHOD_GET);

				edm = EntityProvider.readMetadata(content, false);
				tenantConnectionDetails.setEdm(edm);
			}
		} catch (Exception e) {
			logger.error("something went wrong attempting to get the EDM"
					+ ", probably an issue with Framework, likely other things are about to fail.", e);
			throw e;
		}
		if (edm == null) {
			// we have an issue
			logger.error("something went wrong attempting to get the EDM"
					+ ", probably an issue with framework, likely other things are about to fail.");
		}
		return edm;
	}

	@Override
	public ODataFeed readFeed(String entitySetName, String expand, String filter, String selectFields) throws IOException, ODataException {
		TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());

		EdmEntityContainer entityContainer = tenantConnectionDetails.getEdm().getDefaultEntityContainer();
		String absolutUri = createUri(tenantConnectionDetails.getUrl(), entitySetName, null, expand, filter, filter != null, selectFields);

		return readODataFromURI(entitySetName, entityContainer, absolutUri);

	}

	private ODataFeed readODataFromURI(String entitySetName, EdmEntityContainer entityContainer, String absolutUri) throws IOException,
			EdmException, UnsupportedEncodingException, EntityProviderException {
		InputStream content = execute(absolutUri, APPLICATION_JSON, HTTP_METHOD_GET);
		// just for logging
		byte[] returnedOData = streamToArray(content);
		InputStream responseContent = new ByteArrayInputStream(returnedOData);
		ODataFeed feed = null;
		try {
			feed = EntityProvider.readFeed(APPLICATION_JSON, entityContainer.getEntitySet(entitySetName), responseContent,
					EntityProviderReadProperties.init().build());
		} catch (EntityProviderException e) {
			String feedContent = new String(returnedOData, "UTF-8");
			logger.error("Issue reading feed with URL: " + absolutUri + "/n returned values: " + feedContent);
			throw e;
		}
		return feed;
	}

	@Override
	public ODataFeed readFeed(String entitySetName, String expand, String filter, String selectFields, String orderBy, int numberResults)
			throws IOException, ODataException {
		TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());

		EdmEntityContainer entityContainer = tenantConnectionDetails.getEdm().getDefaultEntityContainer();
		String absolutUri = createUri(tenantConnectionDetails.getUrl(), entitySetName, null, expand, filter, true, selectFields, orderBy,
				numberResults, false, 0);

		return readODataFromURI(entitySetName, entityContainer, absolutUri);
	}

	@Override
	public ODataEntry patchEntry(Long id, String entitySetName, Map<String, Object> data) throws Exception {
		TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());
		String absolutUri = createUri(tenantConnectionDetails.getUrl(), entitySetName, id, null, null, false, null);
		return writeEntity(absolutUri, entitySetName, data, APPLICATION_JSON, HTTP_METHOD_PATCH);
	}

	@Override
	public ODataEntry readEntry(String entitySetName, Long keyValue, String expandRelationName, String selectFields) throws IOException,
			ODataException {
		// working with the default entity container
		TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());

		EdmEntityContainer entityContainer = tenantConnectionDetails.getEdm().getDefaultEntityContainer();
		// create absolute uri based on service uri, entity set name with its
		// key property value and optional expanded relation name
		String absolutUri = createUri(tenantConnectionDetails.getUrl(), entitySetName, keyValue, expandRelationName, null, false,
				selectFields);

		InputStream content = execute(absolutUri, APPLICATION_JSON, HTTP_METHOD_GET);

		return EntityProvider.readEntry(APPLICATION_JSON, entityContainer.getEntitySet(entitySetName), content,
				EntityProviderReadProperties.init().build());
		
	}

	private ODataEntry writeEntity(String absolutUri, String entitySetName, Map<String, Object> data, String contentType, String httpMethod)
			throws Exception {

		ODataEntry entry;
		TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());
		EdmEntityContainer entityContainer;
		EdmEntitySet entitySet;
		URI rootUri;
		ExpandSelectTreeNode node;
		try {
			entityContainer = tenantConnectionDetails.getEdm().getDefaultEntityContainer();
			entitySet = entityContainer.getEntitySet(entitySetName);
			rootUri = new URI(absolutUri.substring(0, absolutUri.lastIndexOf("/") + 1));

			node = ExpandSelectTreeNode.entitySet(entitySet).selectedProperties(new ArrayList<String>(data.keySet())).build();
		} catch (EdmException e) {
			// try refreshing edm may have added a new field

			tenantConnectionDetails.setEdm(null);
			getEdm();
			entityContainer = tenantConnectionDetails.getEdm().getDefaultEntityContainer();
			entitySet = entityContainer.getEntitySet(entitySetName);
			rootUri = new URI(absolutUri.substring(0, absolutUri.lastIndexOf("/") + 1));

			node = ExpandSelectTreeNode.entitySet(entitySet).selectedProperties(new ArrayList<String>(data.keySet())).build();

		}

		EntityProviderWriteProperties properties = EntityProviderWriteProperties.serviceRoot(rootUri).omitJsonWrapper(true)
				.contentOnly(true).expandSelectTree(node).build();

		try {
			// serialize data into ODataResponse object
			ODataResponse response = EntityProvider.writeEntry(contentType, entitySet, data, properties);

			// get (http) entity which is for default Olingo implementation an
			// InputStream
			InputStream entity = response.getEntityAsStream();

			byte[] sendBuffer = streamToArray(entity);
			// just for logging
			String patchContent = new String(sendBuffer, "UTF-8");

			logger.debug("Sending " + httpMethod + " to Odata:" + patchContent);

			String serverResponse = "";
			try (CloseableHttpClient client = HttpClients.custom().build()) {
				HttpEntityEnclosingRequestBase request;

				request = new HttpPatch(absolutUri);
				setAppToAppSSO(absolutUri, request);

				request.setEntity(new StringEntity(patchContent, "UTF-8"));
				request.setHeader("Accept", contentType);
				request.setHeader("Content-Type", contentType);
				logger.debug("Connecting to OData with query:" + absolutUri);
				HttpResponse postResponse = client.execute(request);

				if (postResponse.getStatusLine().getStatusCode() == 201) {
					InputStream content = postResponse.getEntity().getContent();
					byte[] responseBuffer = streamToArray(content);
					InputStream responseContent = new ByteArrayInputStream(responseBuffer);
					serverResponse = new String(responseBuffer, "UTF-8");
					logger.debug("Response from OData server :" + serverResponse);

					entry = EntityProvider.readEntry(contentType, entitySet, responseContent, EntityProviderReadProperties.init().build());

					return entry;
				}
				if (postResponse.getStatusLine().getStatusCode() != 204) {
					InputStream content = postResponse.getEntity().getContent();
					byte[] responseBuffer = streamToArray(content);
					serverResponse = new String(responseBuffer, "UTF-8");
					logger.error("Non 201 or 204 status from server on POST/PUT - probable error: " + serverResponse);
					logger.error("Request that caused error: " + absolutUri + "\n Sending " + httpMethod + " to Odata:" + patchContent);
					throw new ODataException();
				} else {
					logger.debug("204 from server " + httpMethod + " call was successful but returned no body");
				}

			} catch (IOException e) {
				logger.error("Error connecting to XS OData - using " + httpMethod + " " + absolutUri + " with content " + patchContent
						+ " and returning response: " + serverResponse, e);

			}
			return null;
		} catch (EntityProviderException e) {
			logger.error("Error forming OData - using " + contentType + " " + entitySetName + " with content " + data.toString(), e);
			throw e;
		}
	}

	private void setAppToAppSSO(String uri, HttpRequest request) {
		try {
			Context ctx = new InitialContext();
			AuthenticationHeaderProvider authHeaderProvider = (AuthenticationHeaderProvider) ctx.lookup("java:comp/env/authHeaderProvider");
			AuthenticationHeader appToAppSSOHeader = authHeaderProvider.getAppToAppSSOHeader(uri);
			String sflmsHeaderValue = appToAppSSOHeader.getValue();
			request.setHeader(appToAppSSOHeader.getName(), sflmsHeaderValue);
			logger.debug("using authenication: " + sflmsHeaderValue);
		} catch (NamingException e) {
			logger.error("problem getting reference to context");
		}
	}

	private String createUri(String serviceUri, String entitySetName, Long id, String expand, String filter, boolean applyFilter,
			String select) {

		return createUri(serviceUri, entitySetName, id, expand, filter, applyFilter, select, null, 0, false, 0);
	}

	private String createUri(String serviceUri, String entitySetName, Long id, String expand, String filter, boolean applyFilter,
			String select, String orderBy, int numberResults, boolean returnCount, int skip) {

		String encodedFilter = null;
		if (applyFilter) {
			try {
				if (filter == null) {
					filter = "";
				}
				encodedFilter = URLEncoder.encode(filter, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("problem encoding OData URL");
				return null;
			}
		}

		final StringBuilder absolutUri = new StringBuilder(serviceUri).append(SEPARATOR).append(entitySetName);
		if (id != null) {
			absolutUri.append("(").append(id).append(")");
		}
		if (applyFilter) {
			absolutUri.append("/?$filter=").append(encodedFilter);
			if (expand != null) {
				absolutUri.append("&$expand=").append(expand);
			}
			if (select != null) {
				absolutUri.append("&$select=").append(select);
			}
			if (numberResults > 0) {
				absolutUri.append("&$top=").append(numberResults);
			}
			if( skip > 0){
			    absolutUri.append("&$skip=").append(skip);
			}
			if (orderBy != null) {
				absolutUri.append("&$orderby=").append(orderBy);
			}
		} else {
			if (expand != null) {
				absolutUri.append("?&$expand=").append(expand);
			}
		}
		if (returnCount) {
			if (applyFilter || (expand != null)) {
				absolutUri.append("&$inlinecount=allpages");
			} else {
				absolutUri.append("?&$inlinecount=allpages");
			}
		}
		return absolutUri.toString();
	}

	protected InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {

		String serverResponse = "";
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			HttpRequestBase request;

			request = new HttpGet(relativeUri);

			request.setHeader("Accept", contentType);

			setAppToAppSSO(relativeUri, request);

			logger.debug("Connecting to OData with query:" + httpMethod + " - " + relativeUri);

			HttpResponse response = client.execute(request);

			InputStream content = response.getEntity().getContent();
			byte[] buffer = streamToArray(content);
			serverResponse = new String(buffer, "UTF-8");
			logger.debug("Response from OData server :" + serverResponse);
			return new ByteArrayInputStream(buffer);

		} catch (IOException e) {
			logger.error("Error connecting to XS OData - using " + httpMethod + " " + relativeUri + " and returning response: "
					+ serverResponse, e);

		}

		return null;
	}

	private byte[] streamToArray(InputStream stream) throws IOException {
		byte[] result = new byte[0];
		byte[] tmp = new byte[8192];
		int readCount = stream.read(tmp);
		while (readCount >= 0) {
			byte[] innerTmp = new byte[result.length + readCount];
			System.arraycopy(result, 0, innerTmp, 0, result.length);
			System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
			result = innerTmp;
			readCount = stream.read(tmp);
		}
		stream.close();
		return result;
	}

	@Override
	public ODataFeed readFeed(String entitySetName, String expand, String filter, String selectFields, String orderBy, int numberResults,
			boolean count) throws IOException, ODataException {
		TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());

		EdmEntityContainer entityContainer = tenantConnectionDetails.getEdm().getDefaultEntityContainer();
		String absolutUri = createUri(tenantConnectionDetails.getUrl(), entitySetName, null, expand, filter, true, selectFields, orderBy,
				numberResults, false, 0);

		InputStream content = execute(absolutUri, APPLICATION_JSON, HTTP_METHOD_GET);
		return EntityProvider.readFeed(APPLICATION_JSON, entityContainer.getEntitySet(entitySetName), content, EntityProviderReadProperties
				.init().build());
	}
	
	@Override
    public ODataFeed readFeed(String entitySetName, String expand, String filter, String selectFields, String orderBy,
            int numberResults, int skip) throws IOException, ODataException {
	    TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());

        EdmEntityContainer entityContainer = tenantConnectionDetails.getEdm().getDefaultEntityContainer();
        String absolutUri = createUri(tenantConnectionDetails.getUrl(), entitySetName, null, expand, filter, true, selectFields, orderBy,
                numberResults, false, skip);

        InputStream content = execute(absolutUri, APPLICATION_JSON, HTTP_METHOD_GET);
        return EntityProvider.readFeed(APPLICATION_JSON, entityContainer.getEntitySet(entitySetName), content, EntityProviderReadProperties
                .init().build());
    }
	
    @Override
    public Long countFeed(String entitySetName, String filter) throws EdmException, IOException {
        
        TenantConnectionDetails tenantConnectionDetails = detailsForTenant.get(currentTenantResolver.getCurrentTenantId());
        String encodedFilter = "";
        Long count = 0L; 
        try {
            if (filter == null) {
                filter = "";
            }
            encodedFilter = URLEncoder.encode(filter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("problem encoding OData URL");
            return count;
        }
        
        StringBuilder absolutUri = new StringBuilder(tenantConnectionDetails.getUrl()).append(SEPARATOR).append(entitySetName);
        absolutUri = absolutUri.append(SEPARATOR).append("$count");
        absolutUri.append("/?$filter=").append(encodedFilter);
        InputStream content = execute(absolutUri.toString(), APPLICATION_JSON, HTTP_METHOD_GET);
        try{
            count = Long.valueOf(IOUtils.toString(content));
        }catch(NumberFormatException e){
            logger.error("count response is as expected" + IOUtils.toString(content));
        }
        return count;
    }

    

}
