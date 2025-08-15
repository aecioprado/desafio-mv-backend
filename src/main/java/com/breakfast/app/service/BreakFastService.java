package com.breakfast.app.service;

import com.breakfast.app.entity.BreakFastEntity;
import com.breakfast.app.entity.BreakFastItemEntity;

import java.util.List;
import java.util.Set;

public interface BreakFastService {

	public BreakFastEntity save(BreakFastEntity breakFastEntity);
	//public List<BreakFastEntity> findAll();
	public boolean socialSecurityNumberExists(String socialSecurityNumber);
	public boolean itemExists(List<BreakFastItemEntity> items);

}
