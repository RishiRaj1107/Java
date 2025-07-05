package com.JavaSpring.jobsMS.job.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.JavaSpring.jobsMS.job.external.Company;

@FeignClient(name = "CompanyMS")
public interface CompanyClient {
	
	@GetMapping("/companies/{id}")
	Company getCompany(@PathVariable("id") Long id);
}
