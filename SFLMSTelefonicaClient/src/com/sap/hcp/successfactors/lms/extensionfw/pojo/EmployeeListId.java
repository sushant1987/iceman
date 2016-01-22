package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEELISTID")
public class EmployeeListId {

	@Id
	@SequenceGenerator(name = "itemlistid_seq", sequenceName = "SEQ_ITEMLISTID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemlistid_seq")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "REPORT_ID")
	private Long reportId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
}
