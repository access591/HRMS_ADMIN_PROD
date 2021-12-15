package com.hrms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
@Entity
@Table(name = "eservices")
public class Eservices implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8395857888222872479L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 500)
	@Column(name = "service_name")
	private String serviceName; 
	@Size(max = 1000)
	@Column(name = "service_description")
	private String serviceDesc;
	
	@Size(max = 10)
	@Column(name = "service_department")
	private String serviceDept;
	@Size(max = 500)
	@Column(name = "link")
	private String eLink;
	@Size(max = 50)
	@Column(name = "INS_BY", updatable = false)
	private String insBy;

	@Column(name = "INS_DATE", updatable = false)
	private Date insDate = new Date();
	@Size(max = 50)
	@Column(name = "UPD_BY", insertable = false)
	private String updBy;

	@Column(name = "UPD_DATE", insertable = false)
	private Date updDate = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getServiceDept() {
		return serviceDept;
	}

	public void setServiceDept(String serviceDept) {
		this.serviceDept = serviceDept;
	}

	public String geteLink() {
		return eLink;
	}

	public void seteLink(String eLink) {
		this.eLink = eLink;
	}

	public String getInsBy() {
		return insBy;
	}

	public void setInsBy(String insBy) {
		this.insBy = insBy;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	
	
	
	
	
}
