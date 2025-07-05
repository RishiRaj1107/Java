package com.JavaSpring.jobsMS.job.mapper;

import java.util.List;

import com.JavaSpring.jobsMS.job.Job;
import com.JavaSpring.jobsMS.job.dto.JobDTO;
import com.JavaSpring.jobsMS.job.external.Company;
import com.JavaSpring.jobsMS.job.external.Review;

public class JobMapper {
	
	public static JobDTO maptoJobWithCompanyDTO(Job job, Company company, List<Review> reviews)
	{
		JobDTO jobDTO = new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setCompany(company);
		jobDTO.setReviews(reviews);
		
		return jobDTO;
	}
}
