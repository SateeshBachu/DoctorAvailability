package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.TimeAvailability;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.TimeAvailabilityRepository;
import com.example.demo.request.DoctorRequest;
import com.example.demo.request.TimeAvailabilityDoctorRequest;
import com.example.demo.request.TimeAvailabiltyRequest;
import com.example.demo.response.TimeAvailabilityDoctorResponse;
import com.example.demo.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private TimeAvailabilityRepository timeAvailabilityRepository;

	public DoctorServiceImpl() {
		super();
	}


	public DoctorServiceImpl(DoctorRepository doctorRepository, TimeAvailabilityRepository timeAvailabilityRepository) {
		super();
		this.doctorRepository = doctorRepository;
		this.timeAvailabilityRepository = timeAvailabilityRepository;
	}


	@Override
	public Doctor doctorSave(DoctorRequest doctorRequest) {
		Doctor doctor = doctorRepository.findByDoctorId(doctorRequest.getDoctorId());
		if (doctor == null) {
			doctor = new Doctor();
		}
		doctor.setDoctorId(doctorRequest.getDoctorId());
		doctor.setDoctorName(doctorRequest.getDoctorName());
		doctor.setAddress(doctorRequest.getAddress());
		return doctorRepository.save(doctor);
	}
	
	@Override
	public Doctor getDoctorById(Long doctorId) {
		return doctorRepository.findByDoctorId(doctorId);
	}
	

	@Override
	public void deleteDoctor(Long doctorId) {
		doctorRepository.deleteById(doctorId);
		
	}
	
	@Override
	public List<Doctor> getDoctors() {
		return doctorRepository.findAll();
	}
	
	@Override
	public List<TimeAvailability> getDoctorsAvailability(Long doctorId) {
		return timeAvailabilityRepository.findByDoctorId(doctorId);
	}
		

	@Override
	public ResponseObject saveDoctorData(TimeAvailabilityDoctorRequest request) {
		TimeAvailabilityDoctorResponse response = new TimeAvailabilityDoctorResponse();
		Map<String, Integer> weekDaysMap = getWeekDaysMap();
		List<TimeAvailability> timeAvailabilityList = new ArrayList<>();
		for (TimeAvailabiltyRequest availabiltyRequest : request.getAvailabilityList()) {
			TimeAvailability timeAvailability = timeAvailabilityRepository.findByTimeId(availabiltyRequest.getTimeId());
			if (timeAvailability == null) {
				timeAvailability = new TimeAvailability();
				timeAvailability.setDoctorId(request.getDoctorId());
				timeAvailability.setDays(weekDaysMap.get(availabiltyRequest.getDays()));
			}
			BeanUtils.copyProperties(availabiltyRequest, timeAvailability);
			timeAvailabilityList.add(timeAvailability);
		}
		List<TimeAvailability> list = timeAvailabilityRepository.saveAll(timeAvailabilityList);
		response.setDoctorId(request.getDoctorId());
		response.setAvailabilityList(list);
		return new ResponseObject(response, null, HttpStatus.OK);
	}


	@Override
	public boolean deleteDoctorAvailablity(Long doctorId) {
		boolean flag = false;
		List<TimeAvailability> list = timeAvailabilityRepository.findByDoctorId(doctorId);
		if(!list.isEmpty()) {
			for (TimeAvailability timeAvailability : list) {
				timeAvailability.setOpenStatus(0);
				timeAvailability.setStartTime(null);
				timeAvailability.setEndTime(null);
			}
			timeAvailabilityRepository.saveAllAndFlush(list);
			flag =true;
		}	
		return flag;
	}
	
	
	public Map<String, Integer> getWeekDaysMap(){
		Map<String, Integer> daysMap = new HashMap<>();
		daysMap.put("Monday", 1);
		daysMap.put("Tuesday", 2);
		daysMap.put("Wednesday", 3);
		daysMap.put("Thursday", 4);
		daysMap.put("Friday", 5);
		daysMap.put("Saturday", 6);
		daysMap.put("Sunday", 7);
		return daysMap;
	}
	
	
//	private List<String> validateRequest(TimeAvailabilityDoctorRequest request){
//		List<String> errorMesage = new ArrayList<>();
//		if(request.getDoctorId() == null || request.getDoctorId() == 0) {
//			errorMesage.add("DoctorId cannot be null or 0");
//		}
//		List<TimeAvailabiltyRequest> availabilityList = request.getAvailabilityList();
//		if(availabilityList == null || availabilityList.isEmpty()) {
//			errorMesage.add("Atleast select one day time availablity");
//		}
//		int count = 0;
//		for (TimeAvailabiltyRequest timeAvailabiltyRequest : availabilityList) {
//			if(timeAvailabiltyRequest.getOpenStatus() == 1) {
//				int result = timeAvailabiltyRequest.getStartTime().compareTo(timeAvailabiltyRequest.getEndTime());
//				if(result >= 0) {
//					errorMesage.add("Please select start time is less than the end time");
//				}
//				count ++;
//			}
//		}
//		if(count == 0) {
//			errorMesage.add("Atleast select one time availablity");
//		}
//		return errorMesage;
//		
//	}


}
