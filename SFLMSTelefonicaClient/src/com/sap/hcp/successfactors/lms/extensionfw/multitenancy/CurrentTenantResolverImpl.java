package com.sap.hcp.successfactors.lms.extensionfw.multitenancy;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import com.sap.cloud.account.TenantContext;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

@Service
public class CurrentTenantResolverImpl implements CurrentTenantResolver {

	@Override
	public String getCurrentTenantId() {

		try {
			Context ctx = new InitialContext();

			TenantContext tenantContext = (TenantContext) ctx.lookup("java:comp/env/TenantContext");
			String currentTenant = tenantContext.getTenant().getId();

			if (currentTenant == null) {
				throw new NullPointerException();
			}
			return currentTenant;
		} catch (NullPointerException | NamingException e) {
			String host = null;
			InitialContext ctx;
			ConnectivityConfiguration configuration;
			try {
				ctx = new InitialContext();
				configuration = (ConnectivityConfiguration) ctx.lookup("java:comp/env/connectivityConfiguration");

				// get destination configuration for "myDestinationName"
				DestinationConfiguration destConfiguration = configuration.getConfiguration("FallbackTenantName");
				if (destConfiguration != null) {
					// get the tenant name if a destination has been configured
					host = destConfiguration.getProperty("FallbackTenantName");
				}
			} catch (RuntimeException e3) {
			    host=null;
			  }catch (Exception e2) {
				host = null;
			}

			if (host == null) {
				host = "NOT_CURRENTLY_MULTI_TENANT";
				try {
					host = InetAddress.getLocalHost().toString();
					if (host.length() > 36) {
						host = host.substring(0, 35);
					}
				} catch (UnknownHostException e1) {
					// can't resolve local host name
				} //
			}
			return host;
		}
	}

}
