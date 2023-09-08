package com.example.demo.response;

import java.util.List;

import com.example.demo.entity.TimeAvailability;

public class TimeAvailabilityDoctorResponse {

	private Long doctorId;
	
	private List<TimeAvailability> availabilityList;

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public List<TimeAvailability> getAvailabilityList() {
		return availabilityList;
	}

	public void setAvailabilityList(List<TimeAvailability> availabilityList) {
		this.availabilityList = availabilityList;
	}
}
