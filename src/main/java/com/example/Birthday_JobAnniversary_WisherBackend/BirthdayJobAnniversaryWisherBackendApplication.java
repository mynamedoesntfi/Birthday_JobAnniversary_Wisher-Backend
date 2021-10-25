package com.example.Birthday_JobAnniversary_WisherBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class BirthdayJobAnniversaryWisherBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirthdayJobAnniversaryWisherBackendApplication.class, args);
	}

}
