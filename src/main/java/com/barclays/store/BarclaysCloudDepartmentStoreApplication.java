package com.barclays.store;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BarclaysCloudDepartmentStoreApplication implements CommandLineRunner{
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(BarclaysCloudDepartmentStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
