package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.TimeAvailability;
import com.example.demo.request.DoctorRequest;
import com.example.demo.request.TimeAvailabilityDoctorRequest;

public interface DoctorService {
	
	Doctor doctorSave(DoctorRequest doctorRequest);
	
	Doctor getDoctorById(Long doctorId);
	
	void deleteDoctor(Long doctorId);
	
	ResponseObject saveDoctorData(TimeAvailabilityDoctorRequest request);

	List<Doctor> getDoctors();
	
	boolean deleteDoctorAvailablity(Long doctorId);
	
	List<TimeAvailability> getDoctorsAvailability(Long doctorId);
}
