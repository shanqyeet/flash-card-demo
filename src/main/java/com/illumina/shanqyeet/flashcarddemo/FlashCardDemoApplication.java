package com.illumina.shanqyeet.flashcarddemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.illumina.shanqyeet.flashcarddemo.repositories", "com.illumina.shanqyeet.flashcarddemo.controllers", "com.illumina.shanqyeet.flashcarddemo.services", "com.illumina.shanqyeet.flashcarddemo.security"})
//@EntityScan(basePackages = {"com.illumina.shanqyeet.flashcarddemo.models"})
//@SpringBootApplication
public class FlashCardDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlashCardDemoApplication.class, args);
	}
}
