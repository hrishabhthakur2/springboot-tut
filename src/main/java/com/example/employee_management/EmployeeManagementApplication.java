package com.example.employee_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		//IoC container will create the object of EmployeeManagementApplication class and call the main method, this is the entry point of the application
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}
