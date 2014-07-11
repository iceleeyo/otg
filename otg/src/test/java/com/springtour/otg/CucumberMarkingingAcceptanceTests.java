package com.springtour.otg;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "classpath:", format = { "pretty",
		"html:target/cucumber" }, name = "将交易掉单恢复或状态置为DEAD")
public class CucumberMarkingingAcceptanceTests {

}
