package com.goeuro.dev.test.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.goeuro.dev.test.api.GoEuroProxyService;
import com.goeuro.dev.test.api.dto.GeoPosition;
import com.goeuro.dev.test.api.dto.LocationDTO;
import com.goeuro.dev.test.configuration.CommonTestConfig;
import com.goeuro.dev.test.exception.ServiceException;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { CommonTestConfig.class })
public class GoEuroProxyServiceImplTest {

	@InjectMocks
	private GoEuroProxyService goEuroProxyService = new GoEuroProxyServiceImpl();

	@Mock
	private RestTemplate restTemplate;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private static final String VALID_GOEURO_SERVICE_ENDPOINT = "http://valid.com";
	private static final String INVALID_GOEURO_SERVICE_ENDPOINT = "http://invalid.com";

	private static final String LOCATION_SERVICE_URI = "/api/location/";

	private static final String VALID_LOCATION_NAME = "validlocationName";
	private static final String INVALID_LOCATION_NAME = "invalidLocationName";

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		mockRestTemplate();
	}

	@Test
	public void givenValidEndPointAndValidLocation_ShouldReturnLocationDetails() {
		ReflectionTestUtils.setField(goEuroProxyService, "goEuroServiceEndPoint", VALID_GOEURO_SERVICE_ENDPOINT);
		ReflectionTestUtils.setField(goEuroProxyService, "locationDetailsServiceURL", LOCATION_SERVICE_URI);
		List<LocationDTO> locations = goEuroProxyService.fetchLocationDetails(VALID_LOCATION_NAME);
		assertNotNull(locations);
	}

	@Test
	public void givenInValidServiceEndPoint_ShouldThrowServiceException() {
		ReflectionTestUtils.setField(goEuroProxyService, "goEuroServiceEndPoint", INVALID_GOEURO_SERVICE_ENDPOINT);
		ReflectionTestUtils.setField(goEuroProxyService, "locationDetailsServiceURL", LOCATION_SERVICE_URI);
		exception.expect(ServiceException.class);
		List<LocationDTO> locations = goEuroProxyService.fetchLocationDetails(VALID_LOCATION_NAME);
		assertNull(locations);
	}

	@Test
	public void givenInValidLocation_ShouldThrowIllegalArgumentException() {
		ReflectionTestUtils.setField(goEuroProxyService, "goEuroServiceEndPoint", VALID_GOEURO_SERVICE_ENDPOINT);
		ReflectionTestUtils.setField(goEuroProxyService, "locationDetailsServiceURL", LOCATION_SERVICE_URI);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("We did not find any detail for " + INVALID_LOCATION_NAME
				+ " location, Please Try again with other locations");
		List<LocationDTO> locations = goEuroProxyService.fetchLocationDetails(INVALID_LOCATION_NAME);
		assertNull(locations);
	}

	private void mockRestTemplate() {

		ParameterizedTypeReference<List<LocationDTO>> typeRef = new ParameterizedTypeReference<List<LocationDTO>>() {
		};

		mockForValidInputs(typeRef);
		mockForInvalidServiceEndPoint(typeRef);
		mockForInvalidLocationName(typeRef);
	}

	private void mockForInvalidLocationName(ParameterizedTypeReference<List<LocationDTO>> typeRef) {
		List<LocationDTO> emptyList = Collections.emptyList();
		ResponseEntity<List<LocationDTO>> inValidEntity = new ResponseEntity<List<LocationDTO>>(emptyList,
				HttpStatus.OK);
		URI inValidnLocation = getDummyURI(VALID_GOEURO_SERVICE_ENDPOINT, LOCATION_SERVICE_URI, INVALID_LOCATION_NAME);
		doReturn(inValidEntity).when(restTemplate).exchange(inValidnLocation, HttpMethod.GET, null, typeRef);
	}

	private void mockForInvalidServiceEndPoint(ParameterizedTypeReference<List<LocationDTO>> typeRef) {
		URI inValidnServiceEP = getDummyURI(INVALID_GOEURO_SERVICE_ENDPOINT, LOCATION_SERVICE_URI, VALID_LOCATION_NAME);
		doThrow(ServiceException.class).when(restTemplate).exchange(inValidnServiceEP, HttpMethod.GET, null, typeRef);
	}

	private void mockForValidInputs(ParameterizedTypeReference<List<LocationDTO>> typeRef) {
		ResponseEntity<List<LocationDTO>> validEntity = new ResponseEntity<List<LocationDTO>>(getDummyLocations(),
				HttpStatus.OK);

		URI validLocationUri = getDummyURI(VALID_GOEURO_SERVICE_ENDPOINT, LOCATION_SERVICE_URI, VALID_LOCATION_NAME);
		doReturn(validEntity).when(restTemplate).exchange(validLocationUri, HttpMethod.GET, null, typeRef);
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

	private URI getDummyURI(String serviceEndpoint, String locationServiceURL, String locationName) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceEndpoint + locationServiceURL);
		builder.pathSegment(locationName);
		URI uri = builder.build().encode().toUri();
		return uri;
	}

}
