package com.JavaSpring.jobsMS.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.JavaSpring.jobsMS.job.Job;
import com.JavaSpring.jobsMS.job.JobRepository;
import com.JavaSpring.jobsMS.job.JobService;
import com.JavaSpring.jobsMS.job.KafkaJobProducer;
import com.JavaSpring.jobsMS.job.clients.CompanyClient;
import com.JavaSpring.jobsMS.job.clients.ReviewClient;
import com.JavaSpring.jobsMS.job.dto.JobDTO;
import com.JavaSpring.jobsMS.job.external.Company;
import com.JavaSpring.jobsMS.job.external.Review;
import com.JavaSpring.jobsMS.job.mapper.JobMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobSercviceImplementation implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;
    private final KafkaJobProducer kafkaJobProducer;

    int attempt = 0;
    
    @Autowired
    public JobSercviceImplementation(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient, KafkaJobProducer kafkaJobProducer) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
        this.kafkaJobProducer = kafkaJobProducer;
    }

    @RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    private Company getCompany(Long companyId) {
        return companyClient.getCompany(companyId);
    }

    @RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    private List<Review> getReviews(Long companyId) {
        return reviewClient.getReviews(companyId);
    }

    private JobDTO convertToDto(Job job) {
        if (job == null) {
            return null;
        }

        Company company = getCompany(job.getCompanyId());
        List<Review> reviews = getReviews(job.getCompanyId());

        return JobMapper.maptoJobWithCompanyDTO(job, company, reviews);
    }

    @Override
    @Cacheable(value = "jobs", key = "'allJobs'")
    @RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt" + ++attempt);
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOs = new ArrayList<>();
        for (Job job : jobs) {
            JobDTO dto = convertToDto(job);
            if (dto != null) {
                jobDTOs.add(dto);
            }
        }
        return jobDTOs;
    }
    
    public List<JobDTO> companyBreakerFallback(Exception e) {
        List<JobDTO> fallbackList = new ArrayList<>();
        JobDTO fallbackJob = new JobDTO();
        fallbackJob.setTitle("Service Temporarily Unavailable");
        fallbackJob.setDescription("The service is currently experiencing high load. Please try again later.");
        fallbackJob.setLocation("N/A");
        fallbackList.add(fallbackJob);
        return fallbackList;
    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public void createJob(Job job) {
        jobRepository.save(job);
        kafkaJobProducer.sendJobEvent(job, "CREATED");
    }

    @Override
    @Cacheable(value = "jobs", key = "#id")
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public boolean deleteJobById(Long id) {
        try {
            Optional<Job> jobOptional = jobRepository.findById(id);
            if (jobOptional.isPresent()) {
                Job job = jobOptional.get();
                jobRepository.deleteById(id);
                kafkaJobProducer.sendJobEvent(job, "DELETED");
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            kafkaJobProducer.sendJobEvent(job, "UPDATED");
            return true;
        }
        return false;
    }
}
