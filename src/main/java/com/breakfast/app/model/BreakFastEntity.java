package com.breakfast.app.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "breakfast")
public class BreakFastEntity implements Serializable {

	private static final long serialVersionUID = 7235967612451452483L;

	public BreakFastEntity(Long id, String employeeName, String socialSecurityNumber, Set<ItemEntity> items) {
		this.id = id;
		this.employeeName = employeeName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.items = items;
	}

	public BreakFastEntity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "employeeName")
	private String employeeName;

	@Column(name = "socialSecurityNumber")
	private String socialSecurityNumber;

	@Column(name = "items")
	private Set<ItemEntity> items;

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

	public Set<ItemEntity> getItems() {
		return items;
	}

	public void setItems(Set<ItemEntity> items) {
		this.items = items;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		BreakFastEntity that = (BreakFastEntity) o;
		return Objects.equals(id, that.id) && Objects.equals(employeeName, that.employeeName) && Objects.equals(socialSecurityNumber, that.socialSecurityNumber) && Objects.equals(items, that.items);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, employeeName, socialSecurityNumber, items);
	}
}
