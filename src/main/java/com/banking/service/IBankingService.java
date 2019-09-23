package com.banking.service;

import java.util.List;

import com.banking.entity.UseDevice;
import com.banking.entity.UseRating;

public interface IBankingService {
	
	List<UseDevice> getAllDevice();
	boolean addData();
	List<UseRating> getMaxDevicePerYear();
	UseRating getMaxDevice(String year);
	UseRating getMaxDeviceRate(String id);
}
