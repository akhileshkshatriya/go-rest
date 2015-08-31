package com.goeuro.dev.test.api.dto;

public class TodoDTO {

	private String id;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Id=");
		buffer.append(id);
		buffer.append(" :::Description= ");
		buffer.append(description);
		return buffer.toString();
	}
	
}
