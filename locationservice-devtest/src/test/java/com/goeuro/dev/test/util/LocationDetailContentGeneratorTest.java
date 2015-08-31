package com.goeuro.dev.test.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.goeuro.dev.test.api.dto.GeoPosition;
import com.goeuro.dev.test.api.dto.LocationDTO;

@RunWith(MockitoJUnitRunner.class)
public class LocationDetailContentGeneratorTest {

	@InjectMocks
	private LocationDetailContentGenerator contentGenerator = new LocationDetailContentGenerator();

	private static final String DELIMITER = ",";
	private static final String LINE_SEPARATOR = "\n";

	private static final String HEADER = "ID,NAME,TYPE,LATITUDE,LONGITUD";
	private static final String DATA = "1234,test-name,test-type,1234.56,6543.21";

	@Test
	public void test() {
		ReflectionTestUtils.setField(contentGenerator, "delimiter", DELIMITER);
		ReflectionTestUtils.setField(contentGenerator, "lineSeparator", LINE_SEPARATOR);
		String content = contentGenerator.generate(getDummyLocations());
		assertNotNull(content);
		assertTrue(content, content.contains(HEADER));
		assertTrue(content, content.contains(DATA));
	}

	private List<LocationDTO> getDummyLocations() {
		List<LocationDTO> locations = new ArrayList<LocationDTO>();
		LocationDTO location = new LocationDTO();
		location.setId(1234);
		location.setName("test-name");
		location.setType("test-type");
		GeoPosition geoPosition = new GeoPosition();
		geoPosition.setLatitude(1234.56);
		geoPosition.setLongitude(6543.21);
		location.setGeoPosition(geoPosition);
		locations.add(location);
		return locations;
	}

}
