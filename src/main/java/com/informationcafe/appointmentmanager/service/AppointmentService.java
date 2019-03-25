package com.informationcafe.appointmentmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informationcafe.appointmentmanager.model.Appointment;
import com.informationcafe.appointmentmanager.repository.AppointmentRepository;

/**
 * 
 * @author Amitava
 * Purpose: The service class to perform CRUD activities on appointment
 */
@Service("appointmentService")
public class AppointmentService {
	public static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
	@Autowired
    private AppointmentRepository appointmentRepository;
	
	/**
	 * 
	 * @param username
	 * @return appointments
	 */
	public List<Appointment> findAllByUser(final String username) {
		return this.appointmentRepository.findByUsername(username);
	}
	
	/**
	 * 
	 * @param appointment
	 * @return appointment
	 */
	public Appointment save(final Appointment appointment) {
		return this.appointmentRepository.save(appointment);
	}
}
