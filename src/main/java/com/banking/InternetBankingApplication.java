package com.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class InternetBankingApplication {

	public static void main(String[] args) {
		System.out.println("ddddddd");
		SpringApplication.run(InternetBankingApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(InternetBankingApplication.class);
    }

}
