package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.TimeAvailability;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.TimeAvailabilityRepository;
import com.example.demo.request.DoctorRequest;
import com.example.demo.request.TimeAvailabilityDoctorRequest;
import com.example.demo.request.TimeAvailabiltyRequest;
import com.example.demo.response.TimeAvailabilityDoctorResponse;
import com.example.demo.serviceImpl.DoctorServiceImpl;

public class ManageAvailabilityTimeServiceTests {

	private DoctorServiceImpl doctorService;
	private DoctorRepository mockARepository;
	private TimeAvailabilityRepository mockBRepository;

	@BeforeEach
	public void setUp() {
		mockARepository = mock(DoctorRepository.class);
		mockBRepository = mock(TimeAvailabilityRepository.class);
		doctorService = new DoctorServiceImpl(mockARepository, mockBRepository);
	}

	@Test
	void testDoctorSaveWhenDoctorExists() {
        DoctorRequest doctorRequest = new DoctorRequest(999L, "Dani", "11/247,TS");
        Doctor expectedDoctor = new Doctor();
        expectedDoctor.setDoctorId(doctorRequest.getDoctorId());
        expectedDoctor.setDoctorName(doctorRequest.getDoctorName());
        expectedDoctor.setAddress(doctorRequest.getAddress());
        when(mockARepository.findByDoctorId(doctorRequest.getDoctorId())).thenReturn(null);
        when(mockARepository.save(ArgumentMatchers.any(Doctor.class))).thenReturn(expectedDoctor);
        Doctor savedDoctor = doctorService.doctorSave(doctorRequest);
        assertNotNull(savedDoctor);
        assertEquals(expectedDoctor, savedDoctor);
        verify(mockARepository, times(1)).findByDoctorId(doctorRequest.getDoctorId());
        verify(mockARepository, times(1)).save(ArgumentMatchers.any(Doctor.class));
	}

	@Test
	void testDoctorSaveWhenDoctorDoesNotExist() {	
		//When doctor does not exist
        DoctorRequest doctorRequest = new DoctorRequest(999L, "Dani", "11/247,TS");
        Doctor existingDoctor = new Doctor(999L, "Dani", "11/247,TS");
        when(mockARepository.findByDoctorId(doctorRequest.getDoctorId())).thenReturn(existingDoctor);
        when(mockARepository.save(ArgumentMatchers.any(Doctor.class))).thenReturn(existingDoctor);

        Doctor savedDoctor = doctorService.doctorSave(doctorRequest);
        assertNotNull(savedDoctor);
        assertEquals(existingDoctor, savedDoctor);
        verify(mockARepository, times(1)).findByDoctorId(doctorRequest.getDoctorId());
        verify(mockARepository, times(1)).save(ArgumentMatchers.any(Doctor.class));
	}

	@Test
	void getDoctorsTest() {
		when(mockARepository.findAll()).thenReturn(Stream
				.of(new Doctor(376l, "Dani", "USA"), new Doctor(466l, "Fatma", "USA")).collect(Collectors.toList()));
		assertEquals(2, doctorService.getDoctors().size());
	}

	@Test
	void getDoctorByIdTest() {
		when(mockARepository.findByDoctorId(376l)).thenReturn(new Doctor(376l, "Dani", "USA"));
		Doctor doctor = doctorService.getDoctorById(376l);
		assertEquals("Dani", doctor.getDoctorName());
		assertEquals("USA", doctor.getAddress());
	}

	@Test
	void deleteDoctor() {
		doctorService.deleteDoctor(5l);
		verify(mockARepository, times(1)).deleteById(5l);
	}

	@Test
	void getDoctorTimeAvailabilityTest() {
		when(mockBRepository.findByDoctorId(5l)).thenReturn(
				Stream.of(new TimeAvailability(376l, 5l, 1, 0, "08:30 AM", "06:30 PM")).collect(Collectors.toList()));
		assertEquals(1, doctorService.getDoctorsAvailability(5l).size());
	}

	@Test
	void testJUnit() {
		System.out.println("This is the testcase in this class");
		String str1 = "This is the testcase in this class";
		assertEquals("This is the testcase in this class", str1);
	}

	@Test
	void testGetWeekDaysMap() {
		Map<String, Integer> expectedDaysMap = Map.of("Monday", 1, "Tuesday", 2, "Wednesday", 3, "Thursday", 4,
				"Friday", 5, "Saturday", 6, "Sunday", 7);
		Map<String, Integer> actualDaysMap = doctorService.getWeekDaysMap();
		assertNotNull(actualDaysMap);
		assertEquals(expectedDaysMap, actualDaysMap);
	}

	@Test
	void testDeleteDoctorAvailabilityWhenListNotEmpty() {
		Long doctorId = 123L;
		List<TimeAvailability> availabilityList = new ArrayList<>();
		availabilityList.add(new TimeAvailability(376l, 123L, 1, 0, "08:30 AM", "06:30 PM"));
		availabilityList.add(new TimeAvailability(377l, 123L, 3, 1, "09:30 AM", "01:30 PM"));
		when(mockBRepository.findByDoctorId(doctorId)).thenReturn(availabilityList);
		boolean result = doctorService.deleteDoctorAvailablity(doctorId);
		assertTrue(result);
		for (TimeAvailability timeAvailability : availabilityList) {
			assertEquals(0, timeAvailability.getOpenStatus());
			assertNull(timeAvailability.getStartTime());
			assertNull(timeAvailability.getEndTime());
		}
		verify(mockBRepository, times(1)).saveAllAndFlush(availabilityList);
	}

	@Test
	void testDeleteDoctorAvailabilityWhenListIsEmpty() {
		Long doctorId = 123L;
		List<TimeAvailability> emptyList = new ArrayList<>();
		when(mockBRepository.findByDoctorId(doctorId)).thenReturn(emptyList);
		boolean result = doctorService.deleteDoctorAvailablity(doctorId);
		assertFalse(result);
		verify(mockBRepository, times(0)).saveAllAndFlush(ArgumentMatchers.anyList());
	}

	@Test
	void testSaveDoctorDataWithValidRequest() {
//		TimeAvailabilityDoctorRequest request = new TimeAvailabilityDoctorRequest();
//		testGetWeekDaysMap();
//		request.setDoctorId(123L);
//		List<TimeAvailabiltyRequest> availabilityList = new ArrayList<>();
//		availabilityList.add(new TimeAvailabiltyRequest(376l, "1", "08:30 AM", "06:30 PM", 1));
//		availabilityList.add(new TimeAvailabiltyRequest(386l, "2", "09:30 AM", "02:30 PM", 0));
//		request.setAvailabilityList(availabilityList);
//
//		TimeAvailabilityDoctorResponse expectedResponse = new TimeAvailabilityDoctorResponse();
//		List<TimeAvailability> savedAvailabilityList = new ArrayList<>();
//		savedAvailabilityList.add(new TimeAvailability(376l, 123L, 1, 1, "08:30 AM", "06:30 PM"));
//		savedAvailabilityList.add(new TimeAvailability(386l, 123L, 2, 0, "09:30 AM", "02:30 PM"));
//		expectedResponse.setDoctorId(123L);
//		expectedResponse.setAvailabilityList(savedAvailabilityList);
//
//		when(mockBRepository.findByTimeId(ArgumentMatchers.any())).thenReturn(new TimeAvailability());
//		when(mockBRepository.saveAll(ArgumentMatchers.anyList())).thenReturn(savedAvailabilityList);
//		ResponseObject response = doctorService.saveDoctorData(request);
//
//		assertNotNull(response);
//		assertEquals(200, response.getStatus());
//		assertEquals(expectedResponse, response.getResponse());
//		assertNull(response.getMessage());
//		verify(mockBRepository, times(1)).findByTimeId(ArgumentMatchers.any());
//		verify(mockBRepository, times(1)).saveAll(ArgumentMatchers.anyList());
		
		
		 // Arrange
        TimeAvailabilityDoctorRequest request = new TimeAvailabilityDoctorRequest();
        request.setDoctorId(123L);
		List<TimeAvailabiltyRequest> availabilityRequestList = new ArrayList<>();
		availabilityRequestList.add(new TimeAvailabiltyRequest(376l, "1", "08:30 AM", "06:30 PM", 1));
		availabilityRequestList.add(new TimeAvailabiltyRequest(386l, "2", "09:30 AM", "02:30 PM", 0));
		request.setAvailabilityList(availabilityRequestList);
		
        TimeAvailabilityDoctorResponse expectedResponse = new TimeAvailabilityDoctorResponse();
        List<TimeAvailability> savedAvailabilityList = new ArrayList<>();
		savedAvailabilityList.add(new TimeAvailability(376l, 123L, 1, 1, "08:30 AM", "06:30 PM"));
		savedAvailabilityList.add(new TimeAvailability(386l, 123L, 2, 0, "09:30 AM", "02:30 PM"));
		expectedResponse.setDoctorId(123L);
		expectedResponse.setAvailabilityList(savedAvailabilityList);

        Map<String, Integer> weekDaysMap = new HashMap<>();
        weekDaysMap.put("Monday", 1);
        weekDaysMap.put("Tuesday", 2);
        weekDaysMap.put("Wednesday", 3);
        weekDaysMap.put("Thursday", 4);
        weekDaysMap.put("Friday", 5);
        weekDaysMap.put("Saturday", 6);
        weekDaysMap.put("Sunday", 7);

        List<TimeAvailability> availabilityList = new ArrayList<>();
        availabilityList.add(new TimeAvailability(376l, 123L, 1, 1, "08:30 AM", "06:30 PM"));
        availabilityList.add(new TimeAvailability(386l, 123L, 2, 0, "09:30 AM", "02:30 PM"));

        when(mockBRepository.findByTimeId(ArgumentMatchers.anyLong())).thenReturn(null);

        when(mockBRepository.saveAll(ArgumentMatchers.anyList())).thenReturn(availabilityList);
        ResponseObject response = doctorService.saveDoctorData(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
//        assertEquals(expectedResponse, response.getResponse());
        verify(mockBRepository, times(availabilityList.size())).findByTimeId(ArgumentMatchers.anyLong());
        verify(mockBRepository, times(1)).saveAll(ArgumentMatchers.anyList());
	}

	 @Test
	 void doctorSetGetMethodTest() {
		 Long doctorId = 5l;
		 Doctor doctor = new Doctor();
		 doctor.setDoctorId(doctorId);
		 assertEquals(doctorId, doctor.getDoctorId());
	 }
	 
	 @Test
	 void timeAvailabilitySetGetMethodTest() {
		 Long timeId = 5l;
		 TimeAvailability timeAvailability = new TimeAvailability();
		 timeAvailability.setTimeId(timeId);
		 assertEquals(timeId, timeAvailability.getTimeId());
		 
		 Long doctorId = 5l;
		 timeAvailability.setDoctorId(doctorId);
		 assertEquals(timeId, timeAvailability.getDoctorId());
		 
		 int days = 2;
		 timeAvailability.setDays(days);
		 assertEquals(days, timeAvailability.getDays());
	 }
	 
	 @Test
	 void doctorRequestSetGetMethodTest() {
		 DoctorRequest request = new DoctorRequest();
		 Long doctorId = 5l;
		 request.setDoctorId(doctorId);
		 assertEquals(doctorId, request.getDoctorId());
		 
		 String doctorName = "Dani";
		 request.setDoctorName(doctorName);
		 assertEquals(doctorName, request.getDoctorName());
		 
		 String doctorAddress = "USA";
		 request.setAddress(doctorAddress);
		 assertEquals(doctorAddress, request.getAddress()); 
	 }
	 
	 @Test
	 void timeAvailabilityRequestSetGetMethodTest() {
		 TimeAvailabiltyRequest request = new TimeAvailabiltyRequest();
		 Long timeId = 5l;
		 request.setTimeId(timeId);
		 assertEquals(timeId, request.getTimeId()); 
		 
		 String days = "2";
		 request.setDays(days);
		 assertEquals(days, request.getDays());
		 
		 String startTime = "5.30 AM";
		 request.setStartTime(startTime);
		 assertEquals(startTime, request.getStartTime());
		 
		 String endtime = "4.15 PM";
		 request.setEndTime(endtime);
		 assertEquals(endtime, request.getEndTime());
		 
		 int openStatus = 1;
		 request.setOpenStatus(openStatus);
		 assertEquals(openStatus, request.getOpenStatus());
	 }
	 
	 @Test
	 void timeAvailabilityDoctorResponseSetGetMethodTest(){
		 TimeAvailabilityDoctorResponse response = new TimeAvailabilityDoctorResponse();	 
		 Long doctorId = 5l;
		 response.setDoctorId(doctorId);
		 assertEquals(doctorId, response.getDoctorId());	 
		 
		 List<TimeAvailability> list = new ArrayList<>();
		 response.setAvailabilityList(list);
		 assertEquals(0, response.getAvailabilityList().size());
	 }

}
