package com.JavaSpring.jobsMS.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaJobProducer {

    private final KafkaTemplate<String, Job> kafkaTemplate;
    private static final String TOPIC = "job-topic";

    @Autowired
    public KafkaJobProducer(KafkaTemplate<String, Job> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, Job>> sendJobEvent(Job job, String eventType) {
        String key = job.getId() + "_" + eventType;
        return kafkaTemplate.send(TOPIC, key, job);
    }
} 