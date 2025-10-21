package vn.edu.fpt.elios_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EliosUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EliosUserServiceApplication.class, args);
	}

}
