package com.goeuro.dev.test.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class LocationDTO {

	@JsonProperty("_id")
	private Integer id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("type")
	private String type;
	@JsonProperty("geo_position")
	private GeoPosition geoPosition;
	
	@JsonProperty("_id")
	public Integer getId() {
		return id;
	}

	
	@JsonProperty("_id")
	public void setId(Integer id) {
		this.id = id;
	}

	
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("geo_position")
	public GeoPosition getGeoPosition() {
		return geoPosition;
	}

	@JsonProperty("geo_position")
	public void setGeoPosition(GeoPosition geoPosition) {
		this.geoPosition = geoPosition;
	}

	public String toString() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("_id: ");
		buffer.append(id);
		buffer.append("\n");

		buffer.append("name: ");
		buffer.append(name);
		buffer.append("\n");

		buffer.append("type: ");
		buffer.append(type);
		buffer.append("\n");

		buffer.append("latitude: ");
		buffer.append(geoPosition.getLatitude());
		buffer.append("\n");

		buffer.append("longitude : ");
		buffer.append(geoPosition.getLongitude());
		buffer.append("\n");

		return buffer.toString();
	}

}
