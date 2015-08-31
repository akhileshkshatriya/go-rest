package com.goeuro.dev.test.util;

import org.springframework.util.Assert;

public class Validator {
	
	public static boolean validate(String args[]){
		Assert.notEmpty(args,"Location Name not found, Please re run program with location name");
		
		if(args.length > 1){
			throw new IllegalArgumentException("Found more than one location names, Currently we support only one location at a time");
		}
		return true;
	}

}
