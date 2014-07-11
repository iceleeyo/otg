package com.springtour.otg;

import org.junit.runner.*;

import cucumber.api.junit.*;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "classpath:", format = { "pretty",
		"html:target/cucumber" }, name = "查找准备核对的交易")
public class CucumberDevelopingAcceptanceTests {

}