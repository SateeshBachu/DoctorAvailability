package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TimeAvailability;

@Repository
public interface TimeAvailabilityRepository extends JpaRepository<TimeAvailability, Long> {

	TimeAvailability findByTimeId(Long timeId);

	@Query(name = "select * from time_availability where open_status = 1 ", nativeQuery = true)
	List<TimeAvailability> findByDoctorId(Long doctorId);

}
