package clients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ExtractingResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;

import com.example.SpringRestSample1.domain.SampleDomain;


import clients.exceptions.SampleClientException;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class SampleClient {

	private static RestTemplate template;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// test1();
		//test2();
		//test3();
		test4();
	}

	public static void test1() {

		try {
			prepareTemplate();

			ResponseEntity<SampleDomain> response = template
					.getForEntity("http://localhost:8080/sayHello/testException/{value}", SampleDomain.class, "1");

			SampleDomain sampleDomain = response.getBody();
			System.out.println(ToStringBuilder.reflectionToString(sampleDomain));
		} catch (SampleClientException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	private static RestTemplate prepareTemplate() {

		if (Objects.isNull(template)) {
			template = new RestTemplate();
			//********[Start]
			Map<HttpStatus, Class<? extends RestClientException>> statusMapping = new HashMap<>();
			//Can think of using HttpStatus.Series (by using ExtractingResponseErrorHandler.setSeriesMapping) 
			//for mapping HttpStatus.Series error codes pertaining to client and server exceptions 
			//separately in a series manner instead of mapping for each of the HttpStatus codes, explicitly,
			//otherwise there will be many statusMapping.put lines of code as the below code-line.
			//Check the java doc of ExtractingResponseErrorHandler.
			statusMapping.put(HttpStatus.BAD_REQUEST, SampleClientException.class);
			//********[End]

			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			messageConverters.addAll(template.getMessageConverters());

			ExtractingResponseErrorHandler responseErrorHandler = new ExtractingResponseErrorHandler();
			responseErrorHandler.setStatusMapping(statusMapping);
			responseErrorHandler.setMessageConverters(messageConverters);

			template.setErrorHandler(responseErrorHandler);
		}
		return template;
	}

	/**
	 * This is not working as expected as there is no exception (SampleException)
	 * throw.
	 */
	@SuppressWarnings("unused")
	public static void test2() {
		try {
			prepareTemplate();

			ResponseEntity<?> response = template.getForEntity("http://localhost:8080/sayHello/testException/{value}",
					SampleDomain.class, "2");

		} catch (SampleClientException ex) {
			System.out.println("SampleException:" + ex);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex);
		}
	}
	
	@SuppressWarnings("unused")
	public static void test3() {
		try {
			prepareTemplate();

			ResponseEntity<?> response = template.getForEntity("http://localhost:8080/sayHello/throwException1",
					SampleDomain.class);

		} catch (SampleClientException ex) {
			System.out.println("SampleException:" + ex);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex);
		}
	}
	
	@SuppressWarnings("unused")
	public static void test4() {
		try {
			prepareTemplate();

			ResponseEntity<?> response = template.getForEntity("http://localhost:8080/sayHello/throwException2",
					SampleDomain.class);

		} catch (SampleClientException ex) {
			System.out.println("SampleException:" + ex);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex);
		}
	}

}
