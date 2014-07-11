package com.springtour.otg;

import org.junit.runner.*;

import cucumber.api.junit.*;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "classpath:", format = { "pretty",
		"html:target/cucumber", "json:target/cucumber.json" })
public class CucumberAcceptanceTests {

}