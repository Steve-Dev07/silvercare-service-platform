package com.silvercare.controller;

import com.silvercare.dto.ServiceCategoryDTO;
import com.silvercare.dao.ServiceCategoryDAO;

import java.util.*;

public class ServiceCategoryController {
	public static List<ServiceCategoryDTO> getAllServiceCategories() {
		return ServiceCategoryDAO.selectAllServiceCategories();
	}
}
