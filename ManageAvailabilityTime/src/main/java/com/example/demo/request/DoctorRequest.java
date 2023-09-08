package com.example.demo.request;

public class DoctorRequest {
	
	private Long doctorId;
	private String doctorName;
	private String address;

	public DoctorRequest() {
		super();
	}

	public DoctorRequest(Long doctorId, String doctorName, String address) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.address = address;
	}


	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
