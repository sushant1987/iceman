package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OFFERINGLISTID")
public class OfferingListId { 

	@Id
	@SequenceGenerator(name = "itemlistid_seq", sequenceName = "SEQ_ITEMLISTID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemlistid_seq")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "OFFERING_ID")
	private Long offeringId;
	
	@Column(name = "LEGAL_ENTITY")
	private String legalEntity;
	
	@Column(name = "REPORT_ID")
	private Long reportId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(Long offeringId) {
		this.offeringId = offeringId;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
}
