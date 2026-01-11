package com.silvercare.service;

import com.silvercare.dto.ServiceCategoryDTO;
import com.silvercare.dao.ServiceCategoryDAO;

import java.util.*;

public class ServiceCategoryManager {
	public static List<ServiceCategoryDTO> getAllServiceCategories() {
		return ServiceCategoryDAO.selectAllServiceCategories();
	}
}
