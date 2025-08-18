package com.breakfast.app.repository;

import com.breakfast.app.entity.BreakFastItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakFastItemRepository extends JpaRepository<BreakFastItemEntity, Long> {

    boolean existsByName(String item);
}
