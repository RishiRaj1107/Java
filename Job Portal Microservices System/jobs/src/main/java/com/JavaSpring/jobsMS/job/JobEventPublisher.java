package com.JavaSpring.jobsMS.job;

public interface JobEventPublisher {

	void sendJobEvent(Job job, String eventType);
}
