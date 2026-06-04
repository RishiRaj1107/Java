package com.JavaSpring.jobs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.JavaSpring.jobsMS.JobsApplication;
import com.JavaSpring.jobsMS.job.clients.CompanyClient;
import com.JavaSpring.jobsMS.job.clients.ReviewClient;

@SpringBootTest(classes = JobsApplication.class)
@ActiveProfiles("dev")
class JobsApplicationTests {

	@MockBean
	private CompanyClient companyClient;

	@MockBean
	private ReviewClient reviewClient;

	@Test
	void contextLoads() {
	}

}
