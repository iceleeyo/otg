package com.springtour.test;

import java.util.*;

import org.apache.commons.lang.*;
import org.springframework.context.*;
import org.springframework.context.support.*;

public final class ApplicationContextGateway {

	public static ApplicationContextGateway newInstance() {
		return new ApplicationContextGateway();
	}

	public static ApplicationContextGateway newInstanceAdding(String fileName) {
		return new ApplicationContextGateway(fileName);
	}

	private ApplicationContext ac;

	private ApplicationContextGateway() {
		init(null);
	}

	private ApplicationContextGateway(String fileName) {
		init(fileName);
	}

	public void init(String fileName) {
		final List<String> configLocations = new ArrayList<String>();
		configLocations.add("com/springtour/otg/applicationContext-cxf.xml");
		configLocations.add("com/springtour/otg/applicationContext.xml");
		configLocations.add("com/springtour/otg/messaging.xml");
		configLocations.add("com/springtour/otg/otg.xml");
		configLocations.add("test-double.xml");
		configLocations.add("com/springtour/otg/stubs.xml");
		if (StringUtils.isNotBlank(fileName)) {
			configLocations.add(fileName);
		}

		ac = new ClassPathXmlApplicationContext(
				configLocations.toArray(new String[] {}));
	}

	public Object get(String beanName) {
		return ac.getBean(beanName);
	}
}
