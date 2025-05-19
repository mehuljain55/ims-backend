package com.cis.investicationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cis.investicationsystem")
public class InvesticationsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvesticationsystemApplication.class, args);
	}

}
