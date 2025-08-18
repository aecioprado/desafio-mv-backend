package com.breakfast.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breakfast.app.entity.BreakFastEntity;

@Repository
public interface BreakFastRepository extends JpaRepository<BreakFastEntity, Long> {

	boolean existsBySocialSecurityNumber(String socialSecurityNumber);

}
