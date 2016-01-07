/**
 * 
 */
package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * @author I319792
 *
 */
@Entity
@Table(name = "REPORTINFO")
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", length = 36)
public class ReportInfo implements Serializable {

	private static final long serialVersionUID = 307634402596035329L;
	
	@Id
	@SequenceGenerator(name = "reportinfo_seq", sequenceName = "SEQ_REPORTINFO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportinfo_seq")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "REPORT_TYPE")
	private String reportType;
	
	@Column(name = "TENANT_ID", updatable = false, insertable = false, length = 36)
	private String tenant;
	
	/*@ElementCollection
	private Set<String> reportItemId;*/
	
	@Column(name = "LEGAL_ENTITY")
	private String legalEntity;
	
	@Column(name = "CRITERIA_ID")
	private String criteriaId;
	
	@Column(name = "DATE")
	private String date;
	
	@Column(name = "DAYS")
	private String days;
	
	
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Column(name = "CREATED_BY_USER")
	private String createdByUser;

	@Column(name = "MODIFIED_ON")
	@Temporal(TemporalType.DATE)
	private Date modifiedOn;

	@Column(name = "MODIFIED_BY_USER")
	private String modifiedByUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}


	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}

	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}

	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	public Date getModifiedOn() {
		return (Date)modifiedOn.clone();
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = (Date)modifiedOn.clone();
	}

	public String getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(String modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(String criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	/*public Set<String> getReportItemId() {
		return reportItemId;
	}

	public void setReportItemId(Set<String> reportItemId) {
		this.reportItemId = reportItemId;
	}*/

}
