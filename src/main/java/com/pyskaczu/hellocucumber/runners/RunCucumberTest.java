package com.pyskaczu.hellocucumber.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/main/resources/features", glue = "com.pyskaczu.hellocucumber")
public class RunCucumberTest
{
}