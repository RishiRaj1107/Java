package com.JavaSpring.companyMS.company;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.JavaSpring.companyMS.company.dto.JobMessage;

@Service
public class KafkaJobConsumer {

    @KafkaListener(topics = "job-topic", groupId = "company-service-group")
    public void consumeJobEvent(JobMessage jobMessage) {
        System.out.println("Received job event in Company Service: " + jobMessage.getTitle());
        System.out.println("Job Details:");
        System.out.println("ID: " + jobMessage.getId());
        System.out.println("Title: " + jobMessage.getTitle());
        System.out.println("Description: " + jobMessage.getDescription());
        System.out.println("Location: " + jobMessage.getLocation());
        System.out.println("Company ID: " + jobMessage.getCompanyId());
        
        // Here you can add business logic to handle the job event
        // For example:
        // - Update company statistics
        // - Send notifications
        // - Update cache
        // - etc.
    }
} 