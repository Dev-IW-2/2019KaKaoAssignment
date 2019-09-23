package com.banking.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.banking.entity.UseDevice;
import com.banking.entity.UseRating;
import com.banking.repository.UseDeviceRepository;
import com.banking.repository.UseRatingRepository;
import com.banking.vo.DeviceRate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class BankingService implements IBankingService  {
	
	@Autowired
	private UseDeviceRepository useDeviceRepository;

	@Autowired
	private UseRatingRepository useRatingRepository;

	@Override
	public boolean addData() {
		// TODO Auto-generated method stub
		
		ClassPathResource cpr = new ClassPathResource("2019년하반기_서버개발자_데이터.csv");
		BufferedReader inFiles = null;
		try {
		
			inFiles = new BufferedReader(new InputStreamReader(cpr.getInputStream(), "UTF-8"));
	
			
			List<String> devIdList = new ArrayList<>();
			List<UseDevice> useDevList = new ArrayList<>();
			
			List<DeviceRate> useDevRateList = new ArrayList<>();		
			List<UseRating> useRateList = new ArrayList<>();
			
			String readStr = "";
			String devId = "";
			String devName = "";
			
			String year = null;
			String rate = "";
			String devRate = "";
			
			int line = 0;
			
			while((readStr = inFiles.readLine()) != null) {
				String[] readStrArr = readStr.split(",");
				
				useDevRateList.clear();
				if(line == 0) {
					
					for(int i = 0; i < readStrArr.length; i++) {
						if(i < 2) {
							continue;
						}
						devName = readStrArr[i];
						devId = "DIS" + numberGen();
						while (devIdList.contains(devId)) {
							devId = "DIS" + numberGen();
						}
						devIdList.add(devId);
						useDevList.add(new UseDevice(devId, devName));
					}
				}
				else {
					for(int i = 0; i < readStrArr.length; i++) {
						
						if(i == 0) {
							year = readStrArr[i];
						}
						else if(i == 1) {
							rate = readStrArr[i];
						}
						else {
							devRate = readStrArr[i];
							if(devRate.equals("-")) {
								devRate = "0";
							}
							devId = useDevList.get(i-2).getDevice_id();
							devName = useDevList.get(i-2).getDevice_name();
							useDevRateList.add(new DeviceRate(devId, devName, devRate));
						}
					}
					useRateList.add(new UseRating(year, rate, useDevRateList));
				}
				line++;
			}
			inFiles.close();
			
			for(UseDevice ud : useDevList) {
				useDeviceRepository.save(ud);
			}
			
			for(UseRating ur : useRateList) {
				useRatingRepository.save(ur);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				inFiles.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<UseDevice> getAllDevice() {
		// TODO Auto-generated method stub
		
		List<UseDevice> useDevList = new ArrayList<>();
		useDeviceRepository.findAll().forEach(e -> useDevList.add(e));
		return useDevList;
	}

	@Override
	public List<UseRating> getMaxDevicePerYear() {
		// TODO Auto-generated method stub
		
		List<UseRating> useRateList = new ArrayList<>();
		useRatingRepository.findAll().forEach(e -> useRateList.add(e));
		
		List<UseRating> maxDeviceList = new ArrayList<>();
		
		Gson gson = new Gson();
		
		for(UseRating ur : useRateList) {
			
			String year = ur.getYear();
			List<DeviceRate> devRateList = gson.fromJson(ur.getDevice_rate(), new TypeToken<List<DeviceRate>>() {}.getType());
			
			DeviceRate maxDr = getMaxRateDevice(devRateList);
			maxDeviceList.add(new UseRating(year, null, maxDr));
		}
		
		return maxDeviceList;
	}

	@Override
	public UseRating getMaxDevice(String year) {
		// TODO Auto-generated method stub
		UseRating ur = useRatingRepository.findByYear(year);
		
		Gson gson = new Gson();
		List<DeviceRate> devRateList = gson.fromJson(ur.getDevice_rate(), new TypeToken<List<DeviceRate>>() {}.getType());
		
		DeviceRate maxDr = getMaxRateDevice(devRateList);
		return new UseRating(ur.getYear(), null, maxDr);
	}
	
	public DeviceRate getMaxRateDevice(List<DeviceRate> useDeviceList) {
		
		DeviceRate maxDr = new DeviceRate();
		
		for(DeviceRate dr : useDeviceList) {
			if(maxDr.getDevice_id() == null) {
				maxDr = dr;
				continue;
			}
			if(Double.parseDouble(maxDr.getRate())< Double.parseDouble(dr.getRate())) {
				maxDr = dr;
			}
		}
		return maxDr;
	}

	@Override
	public UseRating getMaxDeviceRate(String id) {
		// TODO Auto-generated method stub
		List<UseRating> useRateList = new ArrayList<>();
		useRatingRepository.findAll().forEach(e -> useRateList.add(e));
		
		String maxYear = "";
		DeviceRate maxDr = new DeviceRate();
		
		Gson gson = new Gson();
		
		for(UseRating ur : useRateList) {			
			
			List<DeviceRate> devRateList = gson.fromJson(ur.getDevice_rate(), new TypeToken<List<DeviceRate>>() {}.getType());
	
			for(DeviceRate dr : devRateList) {
				
				if(dr.getDevice_id().equals(id)) {
					if(maxDr.getDevice_id() == null) {
						maxDr = dr;
						maxYear = ur.getYear();
						continue;
					}
					if(Double.parseDouble(maxDr.getRate()) < Double.parseDouble(dr.getRate())) {
						maxDr = dr;
						maxYear = ur.getYear();
					}
				}
				
			}
		}
		
		return new UseRating(maxYear, null, maxDr);
	}
	
	public String numberGen() {
		
		Random rand = new Random();
		StringBuilder randStr = new StringBuilder();
		
		for(int i = 0; i < 7; i++) {
			
			String ran = Integer.toString(rand.nextInt(10));
			randStr.append(ran);
		}
		
		return randStr.toString();
	}
}
