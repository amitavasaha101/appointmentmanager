package com.informationcafe.appointmentmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="APPOINTMENT")
public class Appointment {

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name="APPOINTMENTID", unique=true, updatable=false, nullable=false)
	 private Long appointmentId;
	 @Column(name="APPOINTMENTDATE")
	 private String appointmentDate;
	 @Column(name="STARTTIME")
	 private String startTime;
	 @Column(name="ENDTIME")
	 private String endTime;
	 @Column(name="SUBJECT")
	 private String subject;
	 @Column(name="DETAILS")
	 private String details;
	 @Column(name="APPOINTMENTTYPE")
	 private String appointmentType;
	 @Column(name="USERNAME", updatable=false, nullable=false)
	 private String username;
	public Appointment() {
		super();
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", appointmentDate=" + appointmentDate + ", startTime="
				+ startTime + ", endTime=" + endTime + ", subject=" + subject + ", details=" + details
				+ ", appointmentType=" + appointmentType + ", username=" + username + "]";
	} 
}
