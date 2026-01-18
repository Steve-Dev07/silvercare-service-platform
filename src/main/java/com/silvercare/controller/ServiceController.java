package com.silvercare.controller;

import com.silvercare.dao.ServiceDAO;
import com.silvercare.dto.ServiceDTO;
import com.silvercare.util.TimeUtil;

import java.util.*;

public class ServiceController {
	public static List<ServiceDTO> getServicesByCategory(String category) {
		List<ServiceDTO> servicesList = ServiceDAO.selectServicesByCategory(category);
		List<ServiceDTO> servicesWithTimestampsList = new ArrayList<>();
		
		for(ServiceDTO service : servicesList) {
			servicesWithTimestampsList.add(parseTimestamps(service));
		}
		
		return servicesWithTimestampsList;
	}
	
	public static ServiceDTO getServiceDetailsByName(String serviceName) {
		ServiceDTO selectedService = ServiceDAO.selectServiceByName(serviceName);
		return parseTimestamps(selectedService);
	}
	
	private static ServiceDTO parseTimestamps(ServiceDTO service) {
		service.setDurationStr(TimeUtil.convertDuration(service.getDuration()));
		service.setCreatedTime(TimeUtil.convertDateTime(service.getCreatedTime()));
		service.setLastUpdatedTime(TimeUtil.convertDateTime(service.getLastUpdatedTime()));
		
		return service;
	}
}