package com.breakfast.app.service.impl;

import java.util.List;
import java.util.Set;

import com.breakfast.app.entity.BreakFastItemEntity;
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

		if(socialSecurityNumberExists(breakFastEntity.getSocialSecurityNumber())){
			throw new BreakFastException("You Can't Add new Breakfast.");
		};

		if(itemExists(breakFastEntity.getItems())){
			throw new BreakFastException("You Can't Add new Breakfast.0");
		}

		return this.breakFastRepository.save(breakFastEntity);
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
	public boolean itemExists(List<BreakFastItemEntity> items) {
		for (BreakFastItemEntity item : items)
			if (breakFastRepository.itemExists(item.getName())) {
				return true;
				}
		return false;
	}
}

