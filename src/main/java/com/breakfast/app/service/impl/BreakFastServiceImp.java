package com.breakfast.app.service.impl;

import java.util.Set;

import com.breakfast.app.entity.ItemEntity;
import com.breakfast.app.service.BreakFastService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.breakfast.app.entity.BreakFastEntity;
import com.breakfast.app.exception.BreakFastException;
import com.breakfast.app.repository.BreakFastRepository;

@Service
public class BreakFastServiceImp implements BreakFastService {

	private final BreakFastRepository breakFastRepository;

	public BreakFastServiceImp(BreakFastRepository breakFastRepository) {
		this.breakFastRepository = breakFastRepository;
	}

	@Override
	public BreakFastEntity save(BreakFastEntity breakFastEntity) {
		socialSecurityNumberExists(breakFastEntity.getSocialSecurityNumber());
		itemExists(breakFastEntity.getItems());
		return this.breakFastRepository.save(breakFastEntity);
	}

	@Override
	public ResponseEntity<?> listAll() {
		return this.breakFastRepository.findAll();
	}

	@Override
	public boolean socialSecurityNumberExists(String ssn) {
		boolean exists = breakFastRepository.socialSecurityNumberExists(ssn);
		if (exists) {
			throw new BreakFastException("You Can't Add new Breakfast.");
		}
		return false;
	}

	@Override
	public boolean itemExists(Set<ItemEntity> items) {
		for (ItemEntity item : items)
			if (breakFastRepository.itemExists(item.getName())) {
				throw new BreakFastException("Item Already Exists.");
			}
	}
}

}
