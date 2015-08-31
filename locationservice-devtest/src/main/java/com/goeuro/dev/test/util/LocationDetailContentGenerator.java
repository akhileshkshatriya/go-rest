package com.goeuro.dev.test.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.goeuro.dev.test.api.dto.LocationDTO;

@Component
public class LocationDetailContentGenerator {
	
	@Value("${file.delimiter}")
	private String delimiter;
	@Value("${file.line.separator}")
	private String lineSeparator;
	
	private static final String HEADER = "ID,NAME,TYPE,LATITUDE,LONGITUDE";
	
	public String generate (List<LocationDTO> locations){
		
		StringBuffer content  = new StringBuffer();
		content.append(HEADER);
		content.append(lineSeparator);
		
		for (LocationDTO location : locations) {
			content.append(location.getId());
			content.append(delimiter);
			content.append(location.getName());
			content.append(delimiter);
			content.append(location.getType());
			content.append(delimiter);
			content.append(location.getGeoPosition().getLatitude());
			content.append(delimiter);
			content.append(location.getGeoPosition().getLongitude());
			content.append(lineSeparator);
		}
		return content.toString();
	}
}
