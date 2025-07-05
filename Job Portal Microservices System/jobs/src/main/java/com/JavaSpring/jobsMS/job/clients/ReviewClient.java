package com.JavaSpring.jobsMS.job.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.JavaSpring.jobsMS.job.external.Review;

@FeignClient(name = "reviewMS")
public interface ReviewClient {

	@GetMapping("/reviews")
	List<Review> getReviews(@RequestParam("companyId") Long companyId);
}
