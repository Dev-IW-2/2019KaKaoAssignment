package com.banking.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BankingInfo {
	
	@JsonInclude(Include.NON_NULL)
	private String year;
	@JsonInclude(Include.NON_NULL)
	private String rate;
	@JsonInclude(Include.NON_NULL)
	private String device_id;
	@JsonInclude(Include.NON_NULL)
	private String device_name;
	@JsonInclude(Include.NON_NULL)
	private String device_rate;
	
	public String getDevice_rate() {
		return device_rate;
	}
	public void setDevice_rate(String device_rate) {
		this.device_rate = device_rate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
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
	
	

}
