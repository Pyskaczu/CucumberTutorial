package com.pyskaczu.hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
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

	static class IsItFriday {
		static String isItFriday(String today) {
			return "Friday".equals(today) ? "Yes" : "Nope" ;
		}
	}
}
