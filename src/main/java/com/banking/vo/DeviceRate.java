package com.banking.vo;

public class DeviceRate {

	private String device_id;
	private String device_name;
	private String rate;
	
	public DeviceRate(String device_id, String device_name, String rate) {
		super();
		this.device_id = device_id;
		this.device_name = device_name;
		this.rate = rate;
	}
	
	public DeviceRate() {
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	
}
