package com.randstad.parkingsystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class ParkingSystemApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ParkingSystemApplication.class, args);
		System.out.println("HelloWorld!!!");
//		LocalDateTime now = LocalDateTime.now();
//		System.out.println("Before Formatting: " + now);
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//		String formatDateTime = now.format(format);
//		System.out.println("After Formatting: " + formatDateTime);
	}

}
