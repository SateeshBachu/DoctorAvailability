package com.example.demo.request;

public class TimeAvailabiltyRequest {

	private Long timeId;
	private String days;
	private String startTime;
	private String endTime;
	private Integer openStatus;
	
	public TimeAvailabiltyRequest() {
		super();
	}
	
	public TimeAvailabiltyRequest(Long timeId, String days, String startTime, String endTime, Integer openStatus) {
		super();
		this.timeId = timeId;
		this.days = days;
		this.startTime = startTime;
		this.endTime = endTime;
		this.openStatus = openStatus;
	}

	public Long getTimeId() {
		return timeId;
	}
	public void setTimeId(Long timeId) {
		this.timeId = timeId;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
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
	public Integer getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(Integer openStatus) {
		this.openStatus = openStatus;
	}
	
}
