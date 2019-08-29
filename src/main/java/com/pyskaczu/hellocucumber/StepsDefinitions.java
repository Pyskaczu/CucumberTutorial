package com.pyskaczu.hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class StepsDefinitions {
	private String today;
	private String actualAnswer;

	@Given("^today is \"([^\"]*)\"$")
	public void today_is(String today) {
		this.today = today;
	}

	@When("^I ask whether it's Friday yet$")
	public void i_ask_whether_it_s_Friday_yet() {
		actualAnswer = IsItFriday.isItFriday(today);
	}

	@Then("^I should be told \"([^\"]*)\"$")
	public void i_should_be_told(String expectedAnswer) {
		assertEquals(expectedAnswer, actualAnswer);
	}

	@When("^GET HTTP to \"([^\"]*)\" is called$")
	public void get_http_to_is_called(String url) {
		RestTemplate rt = new RestTemplate();
		rt.setInterceptors(Stream.of(new Tracer()).collect(Collectors.toList()));
		rt.getForObject(URI.create("http://" + url), String.class);
	}


	static class IsItFriday {
		static String isItFriday(String today) {
			return "Friday".equals(today) ? "Yes" : "Nope" ;
		}
	}

	private static class Tracer implements ClientHttpRequestInterceptor {

		@Override public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			log("Sending " + request.getMethodValue() + " to " + request.getURI().toString());
			ClientHttpResponse response = execution.execute(request, body);
			log("Status code " + response.getStatusCode().toString());

			return response;
		}
	}

	public static void log(String message) {
		System.out.println(message);
	}
}
