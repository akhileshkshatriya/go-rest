package com.goeuro.dev.test.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.goeuro.dev.test.api.GoEuroProxyService;
import com.goeuro.dev.test.api.LocationDetailService;
import com.goeuro.dev.test.api.dto.GeoPosition;
import com.goeuro.dev.test.api.dto.LocationDTO;
import com.goeuro.dev.test.util.LocationDetailContentGenerator;
import com.goeuro.dev.test.writer.SimpleFileWriter;

@RunWith(MockitoJUnitRunner.class)
public class LocationDetailServiceImplTest {

	@InjectMocks
	private LocationDetailService locationDetailService = new LocationDetailServiceImpl();

	@Mock
	private GoEuroProxyService goEuroProxyService;

	@Mock
	private LocationDetailContentGenerator contentGenerator;

	@Mock
	private SimpleFileWriter simpleWriter;
	
	@Rule
    public ExpectedException exception = ExpectedException.none();
	
	private static final String VALID_LOCATION_NAME = "VALID_LOCATION_NAME";
	private static final String NULL_LOCATION_NAME = null;
	private static final String EMPTY_LOCATION_NAME = "";
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		setupMockedObjectsl();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenValidLocationName_ShouldVerifyProxyAndFile() throws IOException {
		locationDetailService.findLocationDetailsAndGenerateFile(VALID_LOCATION_NAME);
		verify(goEuroProxyService).fetchLocationDetails(Mockito.anyString());
		verify(contentGenerator).generate(Mockito.anyList());
		verify(simpleWriter).write(Mockito.anyString(), Mockito.anyString());
	}

	@Test
	public void givenNullLocation_ShouldFailAssertion() throws IOException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Location Name not found !!");
		locationDetailService.findLocationDetailsAndGenerateFile(NULL_LOCATION_NAME);
	}

	@Test
	public void givenEmptyLocation_ShouldFailAssertion() throws IOException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Location Name not found !!");
		locationDetailService.findLocationDetailsAndGenerateFile(EMPTY_LOCATION_NAME);
	}

	private void setupMockedObjectsl() {

		mockGoEuroProxyService();
		mockContentGenerator();
	}

	private void mockContentGenerator() {
		Mockito.when(contentGenerator.generate(getDummyLocations())).thenReturn("some-text");
	}

	private void mockGoEuroProxyService() {
		when(goEuroProxyService.fetchLocationDetails(VALID_LOCATION_NAME)).thenReturn(getDummyLocations());
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
