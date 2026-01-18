package com.silvercare.dto;

public class CaregiverDTO {
    private String name;
    private String qualifications;
    private String joinedDate;

	public CaregiverDTO(String name, String qualifications, String joinedDate) {
		this.name = name;
		this.qualifications = qualifications;
		this.joinedDate = joinedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public String getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(String joinedDate) {
		this.joinedDate = joinedDate;
	}
}