package com.breakfast.app.dto;

import java.io.Serializable;
import java.util.List;


public class BreakFastDTO implements Serializable {

	private static final long serialVersionUID = 5224811368963241639L;

	private Long id;
	private String employeeName;
	private String socialSecurityNumber;
	private List<String> items;

	public BreakFastDTO(Long id, String employeeName, String socialSecurityNumber, List<String> items) {
		this.id = id;
		this.employeeName = employeeName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.items = items;
	}
	
	public BreakFastDTO() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}
}
