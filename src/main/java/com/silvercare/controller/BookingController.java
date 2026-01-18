package com.silvercare.controller;

import java.util.*;

import com.silvercare.dao.BookingDAO;
import com.silvercare.dto.BookingDTO;
import com.silvercare.util.TimeUtil;

public class BookingController {
	public static List<BookingDTO> getAllBookingsByUserId(int userId) {
		List<BookingDTO> bookingsList = BookingDAO.selectBookingsByUserId(userId);
		
		for(BookingDTO booking : bookingsList) {
			booking.setAppointmentTime(TimeUtil.convertDateTime(booking.getAppointmentTime()));
		}
		return bookingsList;
	}
}