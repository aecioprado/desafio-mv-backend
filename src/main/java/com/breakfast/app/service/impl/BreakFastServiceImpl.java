package com.breakfast.app.service.impl;

import com.breakfast.app.entity.BreakFastEntity;
import com.breakfast.app.entity.BreakFastItemEntity;
import com.breakfast.app.exception.BreakFastException;
import com.breakfast.app.repository.BreakFastItemRepository;
import com.breakfast.app.repository.BreakFastRepository;
import com.breakfast.app.service.BreakFastService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BreakFastServiceImpl implements BreakFastService {

	private final BreakFastRepository breakFastRepository;
	private final BreakFastItemRepository breakFastItemRepository;

	public BreakFastServiceImpl(BreakFastRepository breakFastRepository, BreakFastItemRepository breakFastItemRepository) {
		this.breakFastRepository = breakFastRepository;
		this.breakFastItemRepository = breakFastItemRepository;
	}

	@Override
	public BreakFastEntity save(BreakFastEntity breakFastEntity) {

		// Check if SS alreay exists
		if(breakFastRepository.existsBySocialSecurityNumber(breakFastEntity.getSocialSecurityNumber())){
			throw new BreakFastException("Social Security Number "+breakFastEntity.getSocialSecurityNumber()+" already exists");
		};

		// Check if Any BreakFast Item already exists
		if(checkIfAnyItemAlreayExists(breakFastEntity.getItems())){
			throw new BreakFastException("Item + "+breakFastEntity.getItems()+" already exists");
		}

		return this.breakFastRepository.save(breakFastEntity);
	}

	@Override
	public List<BreakFastEntity> findAll(){
		return breakFastRepository.findAll();
	}

	private boolean checkIfAnyItemAlreayExists(List<BreakFastItemEntity> items) {
		for (BreakFastItemEntity item : items) {
			if (breakFastItemRepository.existsByName(item.getName())) {
				return true;
			}
		}
        return false;
    }

}

