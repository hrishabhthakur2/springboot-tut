package com.example.employee_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class GreetingController {

    @GetMapping("/hello")
    public String greet(){
        return "Hello, from Spring Boot!";
    }

    @GetMapping("/about")
    public String about() {
        return "Employee Management API";
    }
}