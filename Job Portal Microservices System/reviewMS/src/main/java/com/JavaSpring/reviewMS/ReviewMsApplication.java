package com.JavaSpring.reviewMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ReviewMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewMsApplication.class, args);
	}

}
