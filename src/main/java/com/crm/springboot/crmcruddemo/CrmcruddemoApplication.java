package com.crm.springboot.crmcruddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.crm.springboot"})
public class CrmcruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmcruddemoApplication.class, args);
	}

}
