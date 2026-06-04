package com.JavaSpring.jobsMS.job;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class NoOpJobEventPublisher implements JobEventPublisher {

	@Override
	public void sendJobEvent(Job job, String eventType) {
		// Kafka disabled in dev profile
	}
}
