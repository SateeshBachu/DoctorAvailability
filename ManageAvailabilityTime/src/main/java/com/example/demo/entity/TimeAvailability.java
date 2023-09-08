package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "time_availability")
public class TimeAvailability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "time_id")
	private Long timeId;
	
	@Column(name = "doctor_id")
	private Long doctorId;
	
	@Column(name = "days")
	private Integer days;
	
	@Column(name = "open_status")
	private Integer openStatus;
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_time")
	private String endTime;

	public TimeAvailability() {
		super();
	}

	public TimeAvailability(Long timeId, Long doctorId, Integer days, Integer openStatus, String startTime,
			String endTime) {
		super();
		this.timeId = timeId;
		this.doctorId = doctorId;
		this.days = days;
		this.openStatus = openStatus;
		this.startTime = startTime;
		this.endTime = endTime;
	}



	public Long getTimeId() {
		return timeId;
	}

	public void setTimeId(Long timeId) {
		this.timeId = timeId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(Integer openStatus) {
		this.openStatus = openStatus;
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

}
