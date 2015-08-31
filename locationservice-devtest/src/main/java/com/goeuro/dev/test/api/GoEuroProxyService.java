package com.goeuro.dev.test.api;

import java.util.List;

import com.goeuro.dev.test.api.dto.LocationDTO;

public interface GoEuroProxyService {

	List<LocationDTO> fetchLocationDetails(String locationName);
}
