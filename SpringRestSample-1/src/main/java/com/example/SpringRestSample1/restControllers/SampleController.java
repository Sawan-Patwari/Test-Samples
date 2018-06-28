package com.example.SpringRestSample1.restControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringRestSample1.domain.SampleDomain;
import com.example.SpringRestSample1.exceptions.SampleServerException;
import com.example.SpringRestSample1.exceptions.SampleServerException1;

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
			SampleServerException sampleException = new SampleServerException("Throwing Exception Explicitly");
			sampleException.setExceptionName("FirstException");

			throw sampleException;
		}

	}

	//Test Method: SampleClient.test3()
	@GetMapping("/throwException1")
	public SampleDomain throwException1() {

		SampleServerException sampleException = new SampleServerException("Throwing Exception Explicitly from server.");
		sampleException.setExceptionName("SecondException");

		throw sampleException;

	}

	//Test Method: SampleClient.test4()
	@GetMapping("/throwException2")
	public SampleDomain throwException2() {

		SampleServerException1 sampleException = new SampleServerException1("SecondException: from throwException2() ");
		sampleException.setExceptionName("SecondException");

		throw sampleException;

	}

}
