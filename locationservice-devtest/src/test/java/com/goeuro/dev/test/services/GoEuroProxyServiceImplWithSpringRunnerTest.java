package com.goeuro.dev.test.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.goeuro.dev.test.api.GoEuroProxyService;
import com.goeuro.dev.test.api.dto.GeoPosition;
import com.goeuro.dev.test.api.dto.LocationDTO;
import com.goeuro.dev.test.configuration.CommonTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GoEuroProxyServiceImplWithSpringRunnerTest.TestConfig.class, CommonTestConfig.class })
public class GoEuroProxyServiceImplWithSpringRunnerTest {

	@Autowired
	private GoEuroProxyService goEuroProxyService;

	private static final String GOEURO_SERVICE_ENDPOINT = "http://dev.test.com";
	
	private static final String LOCATION_SERVICE_URI = "/api/location/";

	private static final String VALID_LOCATION_NAME = "validlocationName";
	private static final String INVALID_LOCATION_NAME = "invalidLocationName";
	
	private static ParameterizedTypeReference<List<LocationDTO>> typeRef = new ParameterizedTypeReference<List<LocationDTO>>() {
	};

	@Test
	public void givenValidLocation_ShouldReturnLocationDetails() {
		List<LocationDTO> locations =  goEuroProxyService.fetchLocationDetails(VALID_LOCATION_NAME);
		assertNotNull(locations);
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenInValidLocation_ShouldThrowExceptionWithMessage() {
		List<LocationDTO> locations = goEuroProxyService.fetchLocationDetails(INVALID_LOCATION_NAME);
		assertNull(locations);
	}

	@Configuration
	public static class TestConfig {

		@Bean
		public RestTemplate restTemplate() {

			RestTemplate restTemplate = mock(RestTemplate.class);

			ResponseEntity<List<LocationDTO>> validEntity = new ResponseEntity<List<LocationDTO>>(getDummyLocation(),
					HttpStatus.OK);

			URI validLocationUri = getDummyURI(GOEURO_SERVICE_ENDPOINT,LOCATION_SERVICE_URI, VALID_LOCATION_NAME);
			doReturn(validEntity).when(restTemplate).exchange(validLocationUri, HttpMethod.GET, null, typeRef);

			List<LocationDTO> emptyList = Collections.emptyList();
			ResponseEntity<List<LocationDTO>> inValidEntity = new ResponseEntity<List<LocationDTO>>(emptyList,
					HttpStatus.OK);
			URI inValidLocationUri = getDummyURI(GOEURO_SERVICE_ENDPOINT,LOCATION_SERVICE_URI, INVALID_LOCATION_NAME);
			doReturn(inValidEntity).when(restTemplate).exchange(inValidLocationUri, HttpMethod.GET, null, typeRef);

			return restTemplate;
		}

		@Bean
		public GoEuroProxyService goEuroProxyService() {
			return new GoEuroProxyServiceImpl();
		}

		private URI getDummyURI(String goEuroServiceEP, String locationServiceURI, String locationName) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(goEuroServiceEP+locationServiceURI);
			builder.pathSegment(locationName);
			URI uri = builder.build().encode().toUri();
			return uri;
		}

		private List<LocationDTO> getDummyLocation() {
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
}
