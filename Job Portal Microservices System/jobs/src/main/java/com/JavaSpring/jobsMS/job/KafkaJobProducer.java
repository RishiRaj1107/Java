package com.JavaSpring.jobsMS.job;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.JavaSpring.jobsMS.job.dto.JobMessage;

@Service
@Profile("!dev")
public class KafkaJobProducer implements JobEventPublisher {

	private final KafkaTemplate<String, JobMessage> kafkaTemplate;
	private static final String TOPIC = "job-topic";

	public KafkaJobProducer(KafkaTemplate<String, JobMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void sendJobEvent(Job job, String eventType) {
		JobMessage message = JobMessage.fromJob(job, eventType);
		String key = message.getId() + "_" + eventType;
		kafkaTemplate.send(TOPIC, key, message);
	}
}
