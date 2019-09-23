package com.banking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usedevice")
public class UseDevice {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="device_id")
	private String device_id;
	@Column(name="device_name")
	private String device_name;
	
	
	public UseDevice(String device_id, String device_name) {
		super();
		this.device_id = device_id;
		this.device_name = device_name;
	}
	

	public UseDevice() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
