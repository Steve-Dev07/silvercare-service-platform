package com.silvercare.controller;

import com.silvercare.dao.ServiceDAO;
import com.silvercare.dto.ServiceDTO;

import java.util.*;

public class ServiceController {
	public static List<ServiceDTO> getServicesByCategory(String category) {
		return ServiceDAO.selectServicesByCategory(category);
	}
}