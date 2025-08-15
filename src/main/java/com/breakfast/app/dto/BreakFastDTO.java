package com.breakfast.app.dto;

import com.breakfast.app.entity.BreakFastItemEntity;
import java.io.Serializable;
import java.util.List;


public class BreakFastDTO implements Serializable {

	private static final long serialVersionUID = 5224811368963241639L;

	private Long id;
	private String employeeName;
	private String socialSecurityNumber;
	private List<BreakFastItemEntity> items;

	public BreakFastDTO(Long id, String employeeName, String socialSecurityNumber, List<BreakFastItemEntity> items) {
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

	public List<BreakFastItemEntity> getItems() {
		return items;
	}

	public void setItems(List<BreakFastItemEntity> items) {
		this.items = items;
	}
}