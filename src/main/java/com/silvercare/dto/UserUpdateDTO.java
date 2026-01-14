package com.silvercare.dto;

public class UserUpdateDTO {
	private Integer id;
	private String username;
	private String displayName;
	private String email;

	public UserUpdateDTO(Integer id, String username, String displayName, String email) {
		this.id = id;
		this.username = username;
		this.displayName = displayName;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}