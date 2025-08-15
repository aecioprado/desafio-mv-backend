package com.breakfast.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "breakfast")
public class BreakFastEntity implements Serializable {

	private static final long serialVersionUID = 7235967612451452483L;

	public BreakFastEntity() {
		this.items = new ArrayList<>();
	}

	public BreakFastEntity(Long id, String employeeName, String socialSecurityNumber, List<BreakFastItemEntity> items) {
		this.id = id;
		this.employeeName = employeeName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.items = items != null ? items : new ArrayList<>();
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "employee_name")
	private String employeeName;

	@Column(name = "social_security_number")
	private String socialSecurityNumber;

	// One-to-Many relationship with BreakFastItemEntity
	@OneToMany(mappedBy = "breakfast", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<BreakFastItemEntity> items = new ArrayList<>();

	// Helper method to add items and maintain bidirectional relationship
	public void addItem(BreakFastItemEntity item) {
		items.add(item);
		item.setBreakfast(this);
	}

	// Helper method to remove items and maintain bidirectional relationship
	public void removeItem(BreakFastItemEntity item) {
		items.remove(item);
		item.setBreakfast(null);
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
