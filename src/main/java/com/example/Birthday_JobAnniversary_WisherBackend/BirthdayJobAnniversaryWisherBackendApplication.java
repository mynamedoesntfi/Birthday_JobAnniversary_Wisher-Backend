package com.example.Birthday_JobAnniversary_WisherBackend;

import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BirthdayJobAnniversaryWisherBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirthdayJobAnniversaryWisherBackendApplication.class, args);
	}

}
