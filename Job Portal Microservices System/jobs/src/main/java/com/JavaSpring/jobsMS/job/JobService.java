package com.JavaSpring.jobsMS.job;

import java.util.List;

import com.JavaSpring.jobsMS.job.dto.JobDTO;

public interface JobService {

	List<JobDTO> findAll();

	void createJob(Job job);

	JobDTO getJobById(Long id);

	boolean deleteJobById(Long id);

	boolean updateJob(Long id, Job updatedJob);

}
