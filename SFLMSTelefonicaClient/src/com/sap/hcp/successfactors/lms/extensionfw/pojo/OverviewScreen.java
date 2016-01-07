/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.pojo;


/**
 * @author I319792
 *
 */
	public class OverviewScreen {
		
		private Long id;
		
		private String legalEntity;
		
		private String reportType;
		
		private  int noUnRepRec;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getLegalEntity() {
			return legalEntity;
		}

		public void setLegalEntity(String legalEntity) {
			this.legalEntity = legalEntity;
		}

		public String getReportType() {
			return reportType;
		}

		public void setReportType(String reportType) {
			this.reportType = reportType;
		}

		public int getNoUnRepRec() {
			return noUnRepRec;
		}

		public void setNoUnRepRec(int noUnRepRec) {
			this.noUnRepRec = noUnRepRec;
		}

	}
