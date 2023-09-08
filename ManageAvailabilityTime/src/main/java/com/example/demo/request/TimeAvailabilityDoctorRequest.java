package com.example.demo.request;

import java.util.List;

public class TimeAvailabilityDoctorRequest {

	private Long doctorId;
	
	private List<TimeAvailabiltyRequest> availabilityList;

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public List<TimeAvailabiltyRequest> getAvailabilityList() {
		return availabilityList;
	}

	public void setAvailabilityList(List<TimeAvailabiltyRequest> availabilityList) {
		this.availabilityList = availabilityList;
	}
}
