package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
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
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "PARAMETRISED")
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", length = 36)
@Cacheable(value = false)
public class Parametrised implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3076344025960353297L;

	@Id
	@SequenceGenerator(name = "parameterised_seq", sequenceName = "SEQ_PARAMETERISED", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameterised_seq")
	@Column(name = "ID")
	private Long id;

	@Column(name = "TENANT_ID", updatable = false, insertable = false, length = 36)
	private String tenant;

	@Column(name = "PARAM_NAME")
	private String paramName;

	@Column(name = "PARAM_VALUE")
	private String paramValue;

	@Column(name = "PARAM_TYPE")
	private String paramType;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tenant
	 */
	public String getTenant() {
		return tenant;
	}

	/**
	 * @param tenant
	 *            the tenant to set
	 */
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName
	 *            the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue
	 *            the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * @return the paramType
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * @param paramType
	 *            the paramType to set
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	/**
	 * @return the startDate
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return (Date)startDate.clone();
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = (Date)startDate.clone();
	}

	/**
	 * @return the endDate
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return (Date)endDate.clone();
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = (Date)endDate.clone();
	}

	/**
	 * @return the createdDate
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}

	/**
	 * @return the createdByUser
	 */
	public String getCreatedByUser() {
		return createdByUser;
	}

	/**
	 * @param createdByUser
	 *            the createdByUser to set
	 */
	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	/**
	 * @return the modifiedOn
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getModifiedOn() {
		return (Date)modifiedOn.clone();
	}

	/**
	 * @param modifiedOn
	 *            the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn =(Date)modifiedOn.clone();
	}

	/**
	 * @return the modifiedByUser
	 */
	public String getModifiedByUser() {
		return modifiedByUser;
	}

	/**
	 * @param modifiedByUser
	 *            the modifiedByUser to set
	 */
	public void setModifiedByUser(String modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
