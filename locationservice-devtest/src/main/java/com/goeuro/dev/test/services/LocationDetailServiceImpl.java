package com.goeuro.dev.test.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.goeuro.dev.test.api.GoEuroProxyService;
import com.goeuro.dev.test.api.LocationDetailService;
import com.goeuro.dev.test.api.dto.LocationDTO;
import com.goeuro.dev.test.util.LocationDetailContentGenerator;
import com.goeuro.dev.test.writer.SimpleFileWriter;

@Service
public class LocationDetailServiceImpl implements LocationDetailService {

	static Logger LOGGER = LoggerFactory.getLogger(LocationDetailServiceImpl.class);

	@Autowired
	private GoEuroProxyService goEuroProxyService;

	@Autowired
	private LocationDetailContentGenerator contentGenerator;

	@Autowired
	private SimpleFileWriter simpleWriter;

	@Override
	public void findLocationDetailsAndGenerateFile(String locationName) throws IOException {
		Assert.hasLength(locationName,"Location Name not found !!");
		List<LocationDTO> locations = null;
		locations = goEuroProxyService.fetchLocationDetails(locationName);
		simpleWriter.write(contentGenerator.generate(locations), locationName);
	}
}
