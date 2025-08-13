package com.breakfast.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.breakfast.app.entity.BreakFastEntity;

@Repository
public interface BreakFastRepository extends JpaRepository<BreakFastEntity, Long> {

	//@Query(value = "SELECT EXISTS(SELECT 1 FROM breakfast WHERE socialsecuritynumber = ?1)",
	//		nativeQuery = true)
	@Query(value = "SELECT EXISTS(SELECT 1 FROM breakfast WHERE UPPER(socialsecuritynumber) = UPPER(?1))",
			nativeQuery = true)
	public boolean socialSecurityNumberExists (String ssn);

	@Query(value = "SELECT EXISTS(SELECT 1 FROM items WHERE UPPER(name) = UPPER(?1))",
			nativeQuery = true)
	public boolean itemExists(String item);

}
