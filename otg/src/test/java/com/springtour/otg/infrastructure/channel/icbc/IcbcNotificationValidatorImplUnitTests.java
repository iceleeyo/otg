/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.util.Configurations;

/**
 * @author 005073
 * 
 */
public class IcbcNotificationValidatorImplUnitTests {
	private IcbcNotificationValidatorImpl target;

	@Before
	public void prepare() {
		this.target = new IcbcNotificationValidatorImpl();
		Configurations configurations = new Configurations();
		configurations.setIcbcVerifySignPubFileName("ebb2cpublic.crt");
		this.target.setConfigurations(configurations);
	}

	@Test
	public void verifySign() {
		Map<String, String> originalNotification = new HashMap<String, String>();
		originalNotification
				.put("notifyData",
						"PD94bWwgIHZlcnNpb249IjEuMCIgZW5jb2Rpbmc9IkdCSyIgc3RhbmRhbG9uZT0ibm8iID8+PEIyQ1Jlcz48aW50ZXJmYWNlTmFtZT5JQ0JDX1BFUkJBTktfQjJDPC9pbnRlcmZhY2VOYW1lPjxpbnRlcmZhY2VWZXJzaW9uPjEuMC4wLjExPC9pbnRlcmZhY2VWZXJzaW9uPjxvcmRlckluZm8+PG9yZGVyRGF0ZT4yMDEzMDkzMDE2MTMwMzwvb3JkZXJEYXRlPjxjdXJUeXBlPjAwMTwvY3VyVHlwZT48bWVySUQ+MTAwMUVDMjM4Mjg3NjE8L21lcklEPjxzdWJPcmRlckluZm9MaXN0PjxzdWJPcmRlckluZm8+PG9yZGVyaWQ+MjAxMzA3MjIxMjM0MTIyMjwvb3JkZXJpZD48YW1vdW50PjEwMDwvYW1vdW50PjxpbnN0YWxsbWVudFRpbWVzPjE8L2luc3RhbGxtZW50VGltZXM+PG1lckFjY3Q+MTAwMTIyMzYwOTAwNDY0MTA2NjwvbWVyQWNjdD48dHJhblNlcmlhbE5vPkhGRzAwMDAwMzY4NzMyNDUzNzwvdHJhblNlcmlhbE5vPjwvc3ViT3JkZXJJbmZvPjwvc3ViT3JkZXJJbmZvTGlzdD48L29yZGVySW5mbz48Y3VzdG9tPjx2ZXJpZnlKb2luRmxhZz4wPC92ZXJpZnlKb2luRmxhZz48Sm9pbkZsYWc+PC9Kb2luRmxhZz48VXNlck51bT48L1VzZXJOdW0+PC9jdXN0b20+PGJhbms+PFRyYW5CYXRjaE5vPjwvVHJhbkJhdGNoTm8+PG5vdGlmeURhdGU+MjAxMzA5MzAxNjExMjk8L25vdGlmeURhdGU+PHRyYW5TdGF0PjE8L3RyYW5TdGF0Pjxjb21tZW50Pr270tezybmmo6E8L2NvbW1lbnQ+PC9iYW5rPjwvQjJDUmVzPg==");
		originalNotification
				.put("signMsg",
						"E1q/Mc4lQfMNHMJXGVKnrf/09JrMdfJMWeci4ZaWAvFmR/os6an6axNHr+ovEzMYWPVcjck15WxHDf+pG3zS1Ki05HdKumXWNLD/VeowFGJFGlzhkJZKxyHvNS85wQy87Zle799xgLMOUV7LnRC/XQAWNQ+RTeeVSQnJxK6xV4w=");
		assertTrue(this.target.validate(originalNotification));
	}
}
