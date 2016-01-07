package com.sap.hcp.successfactors.lms.extensionfw.odata;

import org.apache.olingo.odata2.api.edm.Edm;

public class TenantConnectionDetails {

	private Edm edm;
	private String url;

	public Edm getEdm() {
		return edm;
	}

	public void setEdm(Edm edm) {
		this.edm = edm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
