package com.silvercare.controller;

import com.silvercare.dao.CaregiverDAO;
import com.silvercare.dto.CaregiverDTO;
import com.silvercare.util.TimeUtil;

import java.util.*;

public class CaregiverController {
    public static List<CaregiverDTO> getCaregiversByServiceName(String service) {
    	List<CaregiverDTO> caregiversList = CaregiverDAO.selectCaregiversByService(service);
    	
    	for(CaregiverDTO caregiver : caregiversList) {
    		caregiver.setJoinedDate(TimeUtil.convertDate(caregiver.getJoinedDate()));
    	}
    	
    	return caregiversList;
    }
}