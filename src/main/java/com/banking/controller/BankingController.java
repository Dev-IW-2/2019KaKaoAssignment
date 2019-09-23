package com.banking.controller;


import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.UseDevice;
import com.banking.entity.UseRating;
import com.banking.service.IBankingService;
import com.banking.vo.DeviceRate;


@RestController
public class BankingController {
	
	@Autowired
	private IBankingService bankingService;
	
	
	//Save Data
	@PostMapping(value= "/add", produces= { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addData() {
	
		try {
			boolean flag = bankingService.addData();
	        if (flag == false) {
	        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	        }
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	//Get All Devices
	@GetMapping(value= "/device", produces= { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<JSONObject> getAllDevice() {
		
		JSONObject jobj = new JSONObject();
		
		try {
			List<BankingInfo> responseDeviceList = new ArrayList<>();
			List<UseDevice> deviceList = bankingService.getAllDevice();
			
			for(UseDevice ud : deviceList) {
				BankingInfo bi = new BankingInfo();
				BeanUtils.copyProperties(ud, bi);
				responseDeviceList.add(bi);
			}
			jobj.put("devices", responseDeviceList);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<JSONObject>(jobj, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<JSONObject>(jobj, HttpStatus.OK);
		
	}
	
	//Get Max DeviceRate Per Year
	@GetMapping(value= "/device/max", produces= { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<JSONObject> getMaxDeviceRatePerYear() {
		
		JSONObject jobj = new JSONObject();
		
		try {
			List<BankingInfo> responseDeviceList = new ArrayList<>();
			List<UseRating> useRateList = bankingService.getMaxDevicePerYear();
				
			for(UseRating ur: useRateList) {
				BankingInfo bi = new BankingInfo();
				bi.setYear(ur.getYear());
				
				DeviceRate maxDr = ur.getMaxDeviceRate();
				
				bi.setRate(maxDr.getRate());
				bi.setDevice_id(maxDr.getDevice_id());
				bi.setDevice_name(maxDr.getDevice_name());
				responseDeviceList.add(bi);
			}
			jobj.put("devices", responseDeviceList);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<JSONObject>(jobj, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<JSONObject>(jobj, HttpStatus.OK);
	
	}
	
	//Get Max DeviceRate Per Year
	@GetMapping(value= "/device/{year}/max", produces= { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<JSONObject> getMaxDeviceRate(@PathVariable("year") String year) {
		
		JSONObject jobj = new JSONObject();
		
		try {
			List<BankingInfo> responseDeviceList = new ArrayList<>();
			UseRating maxDeviceRate = bankingService.getMaxDevice(year);
			
			BankingInfo bi = new BankingInfo();
			bi.setYear(maxDeviceRate.getYear());
				
			DeviceRate maxDr = maxDeviceRate.getMaxDeviceRate();
				
			bi.setRate(maxDr.getRate());
			bi.setDevice_name(maxDr.getDevice_name());
			responseDeviceList.add(bi);
			jobj.put("result", responseDeviceList);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<JSONObject>(jobj, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<JSONObject>(jobj, HttpStatus.OK);
	}
	
	//Get Year By DeviceId
	@GetMapping(value= "/year/{id}/max", produces= { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<JSONObject> getMaxYearByDevice(@PathVariable("id") String id) {
		
		JSONObject jobj = new JSONObject();
		
		try {
			List<BankingInfo> responseDeviceList = new ArrayList<>();
			UseRating maxDeviceRate = bankingService.getMaxDeviceRate(id);
				
			
			BankingInfo bi = new BankingInfo();
			bi.setYear(maxDeviceRate.getYear());
				
			DeviceRate maxDr = maxDeviceRate.getMaxDeviceRate();
				
			bi.setRate(maxDr.getRate());
			bi.setDevice_name(maxDr.getDevice_name());
			responseDeviceList.add(bi);
			
			jobj.put("result", responseDeviceList);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<JSONObject>(jobj, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<JSONObject>(jobj, HttpStatus.OK);
	
	}
} 