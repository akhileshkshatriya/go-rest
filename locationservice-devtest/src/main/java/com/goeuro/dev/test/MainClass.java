package com.goeuro.dev.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.goeuro.dev.test.api.LocationDetailService;
import com.goeuro.dev.test.configuration.AppConfiguration;
import com.goeuro.dev.test.exception.ServiceException;
import com.goeuro.dev.test.util.Validator;

@Component
public class MainClass {

	@Autowired
	private LocationDetailService service;

	static Logger LOGGER = LoggerFactory.getLogger(MainClass.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		MainClass mainClass = context.getBean(MainClass.class);
		mainClass.process(args);
	}

	private void process(String[] args) {
		try {
			Validator.validate(args);
			service.findLocationDetailsAndGenerateFile(args[0].trim());
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("Error while writing file,Please check permissions or contact Administrator");
		} catch (ServiceException e) {
			LOGGER.error("GoEuro Location service is not available just now, Please re-try after some time !!");
		} catch (Exception e) {
			LOGGER.error("Internal Error, Please contact system administrator !!");
		}
	}

}
