package com.springtour.test;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.legacy.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.config.*;

public class TestDoubleBeanPostProcessor implements BeanPostProcessor {
	public static Mockery context;
	private static Map<String, Class<?>> MOCK_OBJECT_NAMES = new HashMap<String, Class<?>>();

	private static Map<String, Object> STUB_OBJECTS = new HashMap<String, Object>();

	private static Map<String, Object> MOCK_OBJECTS = new HashMap<String, Object>();

	public static void resetContext() {
		clearMocks();
		context = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
	}

	public static void addMock(String name, Class<?> clazz) {
		MOCK_OBJECT_NAMES.put(name, clazz);
	}

	public static void addStub(String name, Object stub) {
		STUB_OBJECTS.put(name, stub);
	}

	public static void clearMocks() {
		MOCK_OBJECT_NAMES.clear();
		MOCK_OBJECTS.clear();
		STUB_OBJECTS.clear();
	}

	public TestDoubleBeanPostProcessor() {
		final Iterator<String> iterator = MOCK_OBJECT_NAMES.keySet().iterator();
		while (iterator.hasNext()) {
			final String key = iterator.next();
			MOCK_OBJECTS
					.put(key, context.mock(MOCK_OBJECT_NAMES.get(key), key));
		}
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		if (MOCK_OBJECTS.containsKey(name)) {
			return MOCK_OBJECTS.get(name);
		} else if (STUB_OBJECTS.containsKey(name)) {
			return STUB_OBJECTS.get(name);
		} else {
			return bean;
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		return bean;
	}

}