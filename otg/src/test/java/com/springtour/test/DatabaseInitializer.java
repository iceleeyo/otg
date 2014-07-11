package com.springtour.test;

import java.io.*;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;

import org.springframework.beans.*;
import org.springframework.context.*;
import org.springframework.core.io.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.test.jdbc.*;

@Slf4j
public class DatabaseInitializer implements ApplicationContextAware {

	private SimpleJdbcTemplate jdbcTemplate;

	private ApplicationContext applicationContext;
	@Setter
	private TestConfigurations configurations;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 有时ApplicationContext会初始化多次，导致initializer也会多次对同一个数据库进行初始化，会造成object
	 * already exists的错误
	 * 
	 * 所以这里暂时使用静态变量来控制是否已经完成过初始化，并在{@link DatabaseInitializer#destroy()}中重置该标志位
	 * 
	 * @see DatabaseInitializer#init()
	 * @see DatabaseInitializer#destroy()
	 */
	private static boolean DONE = false;

	public void init() throws IOException {
		log.info("DONE=" + DONE);
		if (!DONE) {
			if (configurations.isEmbeddedDatabase()) {
				intialize();
			} else {
				skip();
			}
		}
		DONE = true;
	}

	public void destroy() throws IOException {
		log.info("destroy initializer");
		// DONE = false;
	}

	private void skip() {
		log.info("skip initializing database");
	}

	private void intialize() throws IOException {
		log.info("start initializing database");
		Resource[] resources = applicationContext
				.getResources("classpath*:*.sql");

		final List<Resource> scripts = new ArrayList<Resource>();

		for (Resource resource : resources) {
			scripts.add(resource);
		}

		Collections.sort(scripts, new Comparator<Resource>() {

			@Override
			public int compare(Resource o1, Resource o2) {
				String fileName1 = o1.getFilename().replace(".sql", "");
				String fileName2 = o2.getFilename().replace(".sql", "");
				return Integer.valueOf(fileName1).compareTo(
						Integer.valueOf(fileName2));
			}

		});
		for (Resource script : scripts) {
			log.info("start initializing database using "
					+ script.getFilename());
			SimpleJdbcTestUtils.executeSqlScript(jdbcTemplate, script, false);
			log.info("end initializing database using " + script.getFilename());
		}
		log.info("end initializing database");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
