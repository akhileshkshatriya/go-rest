package com.goeuro.dev.test.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.goeuro.dev.test.api.GoEuroProxyService;
import com.goeuro.dev.test.api.dto.LocationDTO;
import com.goeuro.dev.test.exception.ServiceException;
import com.goeuro.dev.test.util.Message;

@Service
public class GoEuroProxyServiceImpl implements GoEuroProxyService {

	@Value("${goeuro.service.url}")
	private String goEuroServiceEndPoint;

	@Value("${location.details.service.path}")
	private String locationDetailsServiceURL;

	@Autowired
	private RestTemplate restTemplate;

	public List<LocationDTO> fetchLocationDetails(String locationName) {

		List<LocationDTO> locations = new ArrayList<>();

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(goEuroServiceEndPoint + locationDetailsServiceURL);
			builder.pathSegment(locationName);
			URI uri = builder.build().encode().toUri();

			ResponseEntity<List<LocationDTO>> entity = restTemplate.exchange(uri, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<LocationDTO>>() {
					});
			locations = entity.getBody();
			Assert.notEmpty(locations, Message.getFormatedMessage(
					"We did not find any detail for %s location, Please Try again with other locations", locationName));

		} catch (RestClientException exception) {
			throw new ServiceException(exception);
		}

		return locations;
	}
}
