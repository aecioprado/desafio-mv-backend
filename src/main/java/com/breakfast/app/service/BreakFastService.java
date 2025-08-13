package com.breakfast.app.service;

import com.breakfast.app.entity.BreakFastEntity;
import com.breakfast.app.entity.ItemEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface BreakFastService {

	public BreakFastEntity save(BreakFastEntity breakFastEntity);
	public ResponseEntity<?> listAll();
	public boolean socialSecurityNumberExists(String socialSecurityNumber);
	public boolean itemExists(Set<ItemEntity> items);

}
