package com.silvercare.dto;

public class BookingDTO {
	private String service;
	private String category;
	private String appointmentTime;
	private String status;
	private double price;

	public BookingDTO(String service, String category, String appointmentTime, String status, double price) {
		this.service = service;
		this.category = category;
		this.appointmentTime = appointmentTime;
		this.status = status;
		this.price = price;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}