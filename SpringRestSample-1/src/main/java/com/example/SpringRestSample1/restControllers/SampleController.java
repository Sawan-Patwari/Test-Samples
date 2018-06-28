package com.example.SpringRestSample1.restControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringRestSample1.domain.SampleDomain;
import com.example.SpringRestSample1.exceptions.SampleException;

/**
 * 
 * @author Sawan.Patwari
 *
 */
@RestController
@RequestMapping("/sayHello")
public class SampleController {

	@GetMapping
	public String findAll() {
		return "Hello";
	}

	@GetMapping("/testException/{value}")
	public SampleDomain throwException(@PathVariable String value) {
		SampleDomain sampleDomain = new SampleDomain();
		sampleDomain.setDomainName("FirstSample");
		if (value.equals("1")) {
			return sampleDomain;
		} else {
			SampleException sampleException = new SampleException("Throwing Exception Explicitly");
			sampleException.setExceptionName("FirstException");

			throw sampleException;
		}

	}

	@GetMapping("/throwException1")
	public SampleDomain throwException1() {

		SampleException sampleException = new SampleException("Throwing Exception Explicitly");
		sampleException.setExceptionName("SecondException");

		throw sampleException;

	}

}
