package com.goeuro.dev.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {
	
	static Logger LOGGER = LoggerFactory.getLogger(Message.class);
	
	public static void display(String message, Object...arguments){
		String fomatedMessage = getFormatedMessage(message, arguments);
		LOGGER.info(fomatedMessage);
		System.out.println(fomatedMessage);
	}
	
	public static String getFormatedMessage(String message, Object...arguments){
		return String.format(message, arguments);
	}

}
