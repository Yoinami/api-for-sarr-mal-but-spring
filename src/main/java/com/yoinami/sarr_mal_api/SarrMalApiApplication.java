package com.yoinami.sarr_mal_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class SarrMalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SarrMalApiApplication.class, args);
	}

}