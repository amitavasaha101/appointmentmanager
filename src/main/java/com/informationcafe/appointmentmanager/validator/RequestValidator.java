package com.informationcafe.appointmentmanager.validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.informationcafe.appointmentmanager.constant.AppointmentConstant;
import com.informationcafe.appointmentmanager.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

@Component
public class RequestValidator {
	private static final Logger LOG = LoggerFactory.getLogger(RequestValidator.class);
	private static final String USER_NAME_PATTERN = "^[a-z][0-9]+$";
	private static final String SUBJECT_PATTERN = "^[a-zA-Z]+( [a-zA-z]+)*$";
	private static final String DETAILS_PATTERN = "^[a-zA-Z]+( [a-zA-z]+)*$";
	
	public static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	
	public boolean validateUsername(String userName) {
		return Pattern.matches(USER_NAME_PATTERN, userName);
	}
	
	public boolean validateSubject(String subject) {
		LOG.debug("Subject is : {}", subject);
		return Pattern.matches(SUBJECT_PATTERN, subject);
	}
	
	public boolean validateDetails(String details) {
		return Pattern.matches(DETAILS_PATTERN, details);
	}
	
	public boolean validateTime(final String time){
  	  if (time.trim().length()!= 5) {
  		  return false;
  	  }
        //matcher = pattern.matcher(time);
        return Pattern.matches(TIME24HOURS_PATTERN, time);             
    }
	public boolean validateDate(String strDate) {
		/* Check if date is 'null' */
		if (strDate.trim().equals(""))
		{
		    return false;
		}
		else
		{
			/**
		     * Set preferred date format,
		     * For example dd-MM-yyyy etc.*/
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd-MM-yyyy");
		    sdfrmt.setLenient(false);
		    /* Create Date object
		     * parse the string into date 
	         */
		    try
		    {
		    	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		    	String now = df.format(new Date());
		        Date javaDate = sdfrmt.parse(strDate);
		        Date nowDate = sdfrmt.parse(now);
		        if (javaDate.before(nowDate)) {
		        	LOG.error("Date in the past is not allowed: {}, {}",strDate, nowDate);
		        	return false;
		        }
		        LOG.info("{} is valid date",strDate);
		    }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		    	LOG.debug("{} is Invalid Date format",strDate);
		        return false;
		    }
		    return true;
		}
	}
	public boolean compareTime(String startTime, String endTime) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("HH:mm");
		try {
		Date startDate = sdfrmt.parse(startTime);
		Date endDate = sdfrmt.parse(endTime);
		if(endDate.before(startDate)) {
			return false;
		}
		} catch(ParseException e) {
			LOG.debug("{} or {} is in Invalid format",startTime,endTime);
	        return false;
		}
		return true;
	}
	public boolean validateAppointmentType(String appointmentType) {
		if (appointmentType.equalsIgnoreCase(AppointmentConstant.TODO)
				|| appointmentType.equalsIgnoreCase(AppointmentConstant.FOLLOWUP)
				|| appointmentType.equalsIgnoreCase(AppointmentConstant.BOTH)) {
			return true;
		}
		return false;
	}
	public String validateRequest(final Appointment appointment) {
		StringBuilder sb = new StringBuilder();
		if (null == appointment) {
			sb.append("Request body is empty");
		}
		if (null != appointment) {
			if(null == appointment.getAppointmentDate()) {
				sb.append("appointment date cannot be empty ");
			} else if (null != appointment.getAppointmentDate()) {
				if(!validateDate(appointment.getAppointmentDate())) {
					sb.append("appointment date is invalid ");
				}
			}
			if(null == appointment.getStartTime()) {
				sb.append("appointment start time cannot be empty");
			} else if (null != appointment.getStartTime()) {
				if(!validateTime(appointment.getStartTime())) {
					sb.append("appointment start time is invalid");
				}
			}
			if(null == appointment.getEndTime()) {
				sb.append("appointment end time cannot be empty");
			} else if (null != appointment.getEndTime()) {
				if(!validateTime(appointment.getEndTime())) {
					sb.append("appointment end time is invalid");
				}
			}
			if(null != appointment.getStartTime()
					&& validateTime(appointment.getStartTime())
					&& null != appointment.getEndTime()
					&& validateTime(appointment.getEndTime())) {
				if(!compareTime(appointment.getStartTime(), appointment.getEndTime())) {
					sb.append("appointment end time ").append(appointment.getEndTime())
					.append(" is before start time ").append(appointment.getStartTime());
				}
			}
			if(null == appointment.getAppointmentType()) {
				sb.append("appointment type cannot be empty");
			} else if (!validateAppointmentType(appointment.getAppointmentType())) {
				sb.append("appointment type has to be ").append(AppointmentConstant.TODO).append(" or ");
				sb.append(AppointmentConstant.FOLLOWUP).append(" or ").append(AppointmentConstant.BOTH);
			}
			if (null == appointment.getSubject()) {
				sb.append("appointment subject cannot be empty");
			} else if(!validateSubject(appointment.getSubject())) {
				sb.append("appointment subject is in invalid format");
			}
		}
		return sb.toString();
	}
}
