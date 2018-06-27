package com.example.SpringRestSample1.restControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sayHello")
public class SampleController {

	 @GetMapping
	    public String findAll() {
	        return "Hello";
	    }
}
