package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLReportgenerator {
	public static int generate (Document dom){
		Transformer tr;
		try {
			tr = TransformerFactory.newInstance().newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			try {
				tr.transform(new DOMSource(dom), new StreamResult(new File("C:\\Users\\I319792\\file.xml")));
				FileInputStream in = null;
				//in = new FileInputStream(fileToBeSent);
				return 0;
			} catch (TransformerException e) {
			
				e.printStackTrace();
				return 1;
			}
		} catch (TransformerConfigurationException e) {
			
			e.printStackTrace();
			return 1;
		} catch (TransformerFactoryConfigurationError e) {
			
			e.printStackTrace();
			return 1;
		}
	}
}
