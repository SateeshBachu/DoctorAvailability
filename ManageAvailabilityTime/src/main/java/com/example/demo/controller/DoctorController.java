package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.commonconstants.CommonConstants;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.TimeAvailability;
import com.example.demo.request.DoctorRequest;
import com.example.demo.request.TimeAvailabilityDoctorRequest;
import com.example.demo.service.DoctorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/doctor/")
@Api("Doctor Manage Availabilty Time")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	public DoctorController(DoctorService doctorService) {
		super();
		this.doctorService = doctorService;
	}

	@ApiOperation("Save doctor details")
	@PostMapping("save")
	public ResponseEntity<String> saveDoctor(@RequestBody DoctorRequest doctorRequest) {
		 Doctor doctor = doctorService.doctorSave(doctorRequest);
		 if(doctor != null) {
			 return new ResponseEntity<>(CommonConstants.DOCTOR_DATA_SAVED_SUCCESSFULLY, HttpStatus.OK);
		 }else {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		 
	}
	
	@ApiOperation("Save doctor availability details")
	@PostMapping("save/timeavailability")
	public ResponseObject saveDoctorAvailability(@RequestBody TimeAvailabilityDoctorRequest doctorRequest) {
		return doctorService.saveDoctorData(doctorRequest);
	}
	
	@ApiOperation("Get doctors list")
	@GetMapping("get/{doctorId}")
	public ResponseObject getDoctor(@PathVariable Long doctorId) {
		ResponseObject responseObject = null;
		Doctor response = doctorService.getDoctorById(doctorId);
		if (response != null) {
			responseObject = new ResponseObject(response, null, HttpStatus.OK);
		} else {
			responseObject = new ResponseObject(null, CommonConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
		}
		return responseObject;
	}
	
	@ApiOperation("Get doctors list")
	@DeleteMapping("delete/{doctorId}")
	public ResponseObject deleteDoctor(@PathVariable Long doctorId) {
		doctorService.deleteDoctor(doctorId);
		return new ResponseObject(null, CommonConstants.DELETED_SUCCESSFULLY, HttpStatus.OK);
	}
	
	@ApiOperation("Get doctors list")
	@GetMapping("get/doctors")
	public ResponseObject getDoctors() {
		ResponseObject responseObject = null;
		List<Doctor> response = doctorService.getDoctors();
		if (response != null) {
			responseObject = new ResponseObject(response, null, HttpStatus.OK);
		} else {
			responseObject = new ResponseObject(null, CommonConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
		}
		return responseObject;
	}
	
	@ApiOperation("Delete doctor availability details")
	@PostMapping("delete/{doctorId}")
	public ResponseObject deleteDoctorAvailability(@PathVariable Long doctorId) {
		ResponseObject responseObject = null;
		boolean flag = doctorService.deleteDoctorAvailablity(doctorId);
		if (flag) {
			responseObject = new ResponseObject(null, CommonConstants.DELETED_SUCCESSFULLY, HttpStatus.OK);
		} else {
			responseObject = new ResponseObject(null, CommonConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
		}
		return responseObject;
	}
	
	@ApiOperation("Save doctor availability details")
	@PostMapping("get/{doctorId}")
	public ResponseObject getDoctorAvailability(@PathVariable Long doctorId) {
		ResponseObject responseObject = null;
		List<TimeAvailability> response = doctorService.getDoctorsAvailability(doctorId);
		if (!response.isEmpty()) {
			responseObject = new ResponseObject(response, null, HttpStatus.OK);
		} else {
			responseObject = new ResponseObject(null, CommonConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
		}
		return responseObject;
	}
	
}
