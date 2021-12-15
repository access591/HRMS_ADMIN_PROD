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
@Table(name ="contact_us")
public class ContactUs implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7917015900162501624L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(max = 50)
	@Column(name = "Designation")
	private String designation;
	@Size(max = 50)
	@Column(name = "Email")
	private String email;
	@Size(max = 50)
	@Column(name = "Name")
	private String name;
	@Size(max = 500)
	@Column(name = "O_Address")
	private String oAddress;
	@Size(max = 30)
	@Column(name = "O_Ext")
	private String oXit;
	@Size(max = 50)
	@Column(name = "Phone_no")
	private String phoneNo;
	@Size(max = 5)
	@Column(name = "cid")
	private String cid;
	@Size(max = 200)
	@Column(name = "pic")
	private String pic;
	@Size(max = 5)
	@Column(name = "pid")
	private String pId;
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


	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getoAddress() {
		return oAddress;
	}

	public void setoAddress(String oAddress) {
		this.oAddress = oAddress;
	}

	public String getoXit() {
		return oXit;
	}

	public void setoXit(String oXit) {
		this.oXit = oXit;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	
	
	
	


	
	
}
