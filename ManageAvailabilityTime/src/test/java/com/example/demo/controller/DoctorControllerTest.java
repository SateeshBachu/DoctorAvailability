package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.commonconstants.CommonConstants;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.TimeAvailability;
import com.example.demo.request.DoctorRequest;
import com.example.demo.request.TimeAvailabilityDoctorRequest;
import com.example.demo.request.TimeAvailabiltyRequest;
import com.example.demo.response.TimeAvailabilityDoctorResponse;
import com.example.demo.service.DoctorService;

public class DoctorControllerTest {

	private DoctorService doctorService;
	private DoctorController doctorController;

	@BeforeEach
	public void setUp() {
		doctorService = mock(DoctorService.class);
		doctorController = new DoctorController(doctorService);
	}

	@Test
	void testSaveDoctor() {
		DoctorRequest doctorRequest = new DoctorRequest(999L, "Dani", "11/247,TS");
		Doctor savedDoctor = new Doctor(999L, "Dani", "11/247,TS");
		when(doctorService.doctorSave(doctorRequest)).thenReturn(savedDoctor);
		ResponseEntity<String> responseEntity = doctorController.saveDoctor(doctorRequest);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(CommonConstants.DOCTOR_DATA_SAVED_SUCCESSFULLY, responseEntity.getBody());
		verify(doctorService, times(1)).doctorSave(doctorRequest);
	}

	@Test
	void testSaveDoctorFailure() {
		DoctorRequest doctorRequest = new DoctorRequest(999L, "Dani", "11/247,TS");
		when(doctorService.doctorSave(doctorRequest)).thenReturn(null);
		ResponseEntity<String> responseEntity = doctorController.saveDoctor(doctorRequest);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
		verify(doctorService, times(1)).doctorSave(doctorRequest);
	}

	@Test
	void testGetDoctorWhenDoctorExists() {
		Long doctorId = 123L;
		Doctor expectedDoctor = new Doctor(123L, "Dark", "NZ");
		when(doctorService.getDoctorById(doctorId)).thenReturn(expectedDoctor);
		ResponseObject response = doctorController.getDoctor(doctorId);
		assertNotNull(response);
		assertEquals(200, response.getStatus());

	}

	@Test
	void testGetDoctorWhenDoctorNotFound() {
		Long doctorId = 123L;
		when(doctorService.getDoctorById(doctorId)).thenReturn(null);
		ResponseObject response = doctorController.getDoctor(doctorId);

		assertNotNull(response);
		assertEquals(400, response.getStatus());
		assertEquals(CommonConstants.SOMETHING_WENT_WRONG, response.getMessage());
	}

	@Test
	void testDeleteDoctor() {
		Long doctorId = 123L;
		ResponseObject response = doctorController.deleteDoctor(doctorId);
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(CommonConstants.DELETED_SUCCESSFULLY, response.getMessage());
		verify(doctorService, times(1)).deleteDoctor(doctorId);
	}

	@Test
	void testGetDoctors() {
		List<Doctor> expectedDoctors = new ArrayList<>();
		expectedDoctors.add(new Doctor(998L, "Dani", "11/247,TS"));
		expectedDoctors.add(new Doctor(999L, "Brook", "NS"));
		when(doctorService.getDoctors()).thenReturn(expectedDoctors);
		ResponseObject response = doctorController.getDoctors();
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(expectedDoctors, response.getResponse());

		// when doctors not found
		when(doctorService.getDoctors()).thenReturn(null);
		ResponseObject responseOb = doctorController.getDoctors();
		assertNotNull(responseOb);
		assertEquals(400, responseOb.getStatus());
		assertEquals(CommonConstants.SOMETHING_WENT_WRONG, responseOb.getMessage());
	}

	@Test
	void testDeleteDoctorAvailability() {
		Long doctorId = 123L;
		when(doctorService.deleteDoctorAvailablity(doctorId)).thenReturn(true);
		ResponseObject response = doctorController.deleteDoctorAvailability(doctorId);
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(CommonConstants.DELETED_SUCCESSFULLY, response.getMessage());

		// Delete doctor availablity failure
		when(doctorService.deleteDoctorAvailablity(doctorId)).thenReturn(false);
		ResponseObject responseFailure = doctorController.deleteDoctorAvailability(doctorId);
		assertNotNull(responseFailure);
		assertEquals(400, responseFailure.getStatus());
		assertEquals(CommonConstants.SOMETHING_WENT_WRONG, responseFailure.getMessage());
	}

	@Test
	void testGetDoctorAvailability() {
		Long doctorId = 123L;
		List<TimeAvailability> expectedAvailability = new ArrayList<>();
		expectedAvailability.add(new TimeAvailability(376l, 5l, 1, 0, "08:30 AM", "06:30 PM"));
		expectedAvailability.add(new TimeAvailability(377l, 5l, 2, 1, "10:30 AM", "04:30 PM"));
		when(doctorService.getDoctorsAvailability(doctorId)).thenReturn(expectedAvailability);
		ResponseObject response = doctorController.getDoctorAvailability(doctorId);
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(expectedAvailability, response.getResponse());

		// Doctor availability not available
		List<TimeAvailability> emptyAvailability = new ArrayList<>();
		when(doctorService.getDoctorsAvailability(doctorId)).thenReturn(emptyAvailability);
		ResponseObject responseFailure = doctorController.getDoctorAvailability(doctorId);
		assertNotNull(responseFailure);
		assertEquals(400, responseFailure.getStatus());
		assertNull(responseFailure.getResponse());
		assertEquals(CommonConstants.SOMETHING_WENT_WRONG, responseFailure.getMessage());
	}

	@Test
	public void testSaveDoctorAvailability() {
		// Arrange
		TimeAvailabilityDoctorRequest doctorRequest = new TimeAvailabilityDoctorRequest();
		doctorRequest.setDoctorId(123L);
		List<TimeAvailabiltyRequest> timeAvailabilityRequestList = new ArrayList<>();
		timeAvailabilityRequestList.add(new TimeAvailabiltyRequest(376l, "1", "08:30 AM", "06:30 PM", 1));
		timeAvailabilityRequestList.add(new TimeAvailabiltyRequest(386l, "2", "09:30 AM", "02:30 PM", 0));
		doctorRequest.setAvailabilityList(timeAvailabilityRequestList);

		TimeAvailabilityDoctorResponse expResponse = new TimeAvailabilityDoctorResponse();
		List<TimeAvailability> savedAvailabilityList = new ArrayList<>();
		savedAvailabilityList.add(new TimeAvailability(376l, 123L, 1, 1, "08:30 AM", "06:30 PM"));
		savedAvailabilityList.add(new TimeAvailability(386l, 123L, 2, 0, "09:30 AM", "02:30 PM"));
		expResponse.setDoctorId(123L);
		expResponse.setAvailabilityList(savedAvailabilityList);

		ResponseObject expectedResponse = new ResponseObject(expResponse, null, HttpStatus.OK);
		when(doctorService.saveDoctorData(doctorRequest)).thenReturn(expectedResponse);
		ResponseObject response = doctorController.saveDoctorAvailability(doctorRequest);
		assertNotNull(response);
		assertEquals(expectedResponse, response);
		verify(doctorService, times(1)).saveDoctorData(doctorRequest);
	}
}
