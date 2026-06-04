package com.JavaSpring.jobsMS.job.dto;

import com.JavaSpring.jobsMS.job.Job;

public class JobMessage {
	private Long id;
	private String title;
	private String description;
	private String minSalary;
	private String maxSalary;
	private String location;
	private Long companyId;
	private String eventType;

	public JobMessage() {
	}

	public static JobMessage fromJob(Job job, String eventType) {
		JobMessage message = new JobMessage();
		message.setId(job.getId());
		message.setTitle(job.getTitle());
		message.setDescription(job.getDescription());
		message.setMinSalary(job.getMinSalary());
		message.setMaxSalary(job.getMaxSalary());
		message.setLocation(job.getLocation());
		message.setCompanyId(job.getCompanyId());
		message.setEventType(eventType);
		return message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(String minSalary) {
		this.minSalary = minSalary;
	}

	public String getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(String maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
}
