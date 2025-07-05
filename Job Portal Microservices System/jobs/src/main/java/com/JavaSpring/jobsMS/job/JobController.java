package com.JavaSpring.jobsMS.job;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.JavaSpring.jobsMS.job.dto.JobDTO;

@RestController
@RequestMapping("/jobs")
public class JobController {

	private final JobService jobService;

	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	// Get all jobs
	@GetMapping
	public ResponseEntity<List<JobDTO>> findAll() {
		List<JobDTO> jobs = jobService.findAll();
		return ResponseEntity.ok(jobs);
	}

	// Create new job
	@PostMapping
	@CacheEvict(value = "jobs", allEntries = true)
	public ResponseEntity<String> createJob(@RequestBody Job job) {
		jobService.createJob(job);
		return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
	}

	// Get job by ID
	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
		JobDTO jobDTO = jobService.getJobById(id);
		if (jobDTO != null) {
			return new ResponseEntity<>(jobDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Delete job by ID
	@DeleteMapping("/{id}")
	@CacheEvict(value = "jobs", allEntries = true)
	public ResponseEntity<String> deleteJob(@PathVariable Long id) {
		boolean deleted = jobService.deleteJobById(id);
		if (deleted) {
			return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
	}

	// Update job by ID
	@PutMapping("/{id}")
	@CacheEvict(value = "jobs", allEntries = true)
	public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
		boolean updated = jobService.updateJob(id, updatedJob);
		if (updated) {
			return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
	}
}
