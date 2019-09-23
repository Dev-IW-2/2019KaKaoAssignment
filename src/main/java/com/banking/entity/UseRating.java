package com.banking.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.banking.vo.DeviceRate;
import com.vladmihalcea.hibernate.type.json.JsonStringType;


@Entity
@Table(name="userating")
@TypeDef(
	    name = "json", 
	    typeClass = JsonStringType.class
)
public class UseRating implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="year")
	private String year;
	@Column(name="rate")
	private String rate;
	
	@Type(type = "json")
    @Column(columnDefinition = "json", name="device_rate")
    private String device_rate;
	
	@Transient
	private DeviceRate maxDeviceRate;
	
	@Transient
	private List<DeviceRate> useDeviceList;

	public UseRating(String year, String rate, List<DeviceRate> useDeviceList) {
		super();
		this.year = year;
		this.rate = rate;
		this.useDeviceList = useDeviceList;
		
		JSONArray jsonArray = new JSONArray();
		
		for(DeviceRate ud : useDeviceList) {
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("device_id", ud.getDevice_id());
			jsonObject.put("device_name", ud.getDevice_name());
			jsonObject.put("rate", ud.getRate());
			jsonArray.add(jsonObject);
		}
		
		
		this.device_rate = jsonArray.toString(); 
	}
	
	public UseRating(String year, String rate, DeviceRate maxDeviceRate) {
		super();
		this.year = year;
		this.rate = rate;
		this.maxDeviceRate = maxDeviceRate;
	}

	public UseRating() {
		// TODO Auto-generated constructor stub
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
	

	public String getDevice_rate() {
		
		return device_rate;
	}

	public void setDevice_rate(String device_rate) {
		this.device_rate = device_rate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<DeviceRate> getUseDeviceList() {
		return useDeviceList;
	}


	public void setUseDeviceList(List<DeviceRate> useDeviceList) {
		this.useDeviceList = useDeviceList;
	}

	public DeviceRate getMaxDeviceRate() {
		return maxDeviceRate;
	}

	public void setMaxDeviceRate(DeviceRate maxDeviceRate) {
		this.maxDeviceRate = maxDeviceRate;
	}
	
}
