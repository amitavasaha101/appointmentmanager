package com.informationcafe.appointmentmanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.informationcafe.appointmentmanager.model.Appointment;
import com.informationcafe.appointmentmanager.model.ErrorMessage;
import com.informationcafe.appointmentmanager.service.AppointmentService;
import com.informationcafe.appointmentmanager.validator.RequestValidator;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	public static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RequestValidator requestValidator;

	@Autowired
	AppointmentService appointmentService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/all/{username}", method = RequestMethod.GET,
	        produces = { "application/json", "application/xml" })
	public ResponseEntity<?> getAppointmentsForUser(@PathVariable("username") String username) {
		logger.info(" Fetching all appointments for user: {} ", username);
		List<Appointment> appointments = appointmentService.findAllByUser(username);
		if (null != appointments && !appointments.isEmpty()) {
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setMessage("There are no appointments for the user");
			return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/createAppointment", method=RequestMethod.POST,
			produces = {"application/json", "application/xml" })
    public ResponseEntity<?> addAppointment(@RequestBody Appointment appointment, UriComponentsBuilder ucBuilder) {
		logger.info("Add an appointment for username {} and appointmentDate {} ",
				appointment.getUsername(), appointment.getAppointmentDate());
		ResponseEntity<?> response = null;
		String validationResult = requestValidator.validateRequest(appointment);
		if (null != validationResult && validationResult.trim().length()>0) {
			logger.error(validationResult);
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setMessage(validationResult);
			response = new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {	
			logger.info("Add an appointment for username {} and appointmentDate {} ",
				appointment.getUsername(), appointment.getAppointmentDate());
			appointment = appointmentService.save(appointment);
			response = new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
		}
		return response;
	}
}