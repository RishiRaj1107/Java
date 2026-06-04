package com.JavaSpring.jobsMS.job.clients;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.JavaSpring.jobsMS.job.external.Company;
import com.JavaSpring.jobsMS.job.external.Review;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ExternalJobDataService {

	private final CompanyClient companyClient;
	private final ReviewClient reviewClient;

	public ExternalJobDataService(CompanyClient companyClient, ReviewClient reviewClient) {
		this.companyClient = companyClient;
		this.reviewClient = reviewClient;
	}

	@CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyFallback")
	@Retry(name = "companyBreaker")
	public Company getCompany(Long companyId) {
		return companyClient.getCompany(companyId);
	}

	@CircuitBreaker(name = "companyBreaker", fallbackMethod = "reviewsFallback")
	@Retry(name = "companyBreaker")
	public List<Review> getReviews(Long companyId) {
		return reviewClient.getReviews(companyId);
	}

	@SuppressWarnings("unused")
	private Company companyFallback(Long companyId, Throwable t) {
		return null;
	}

	@SuppressWarnings("unused")
	private List<Review> reviewsFallback(Long companyId, Throwable t) {
		return Collections.emptyList();
	}
}
