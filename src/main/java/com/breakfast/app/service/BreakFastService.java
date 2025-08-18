package com.breakfast.app.service;

import com.breakfast.app.entity.BreakFastEntity;

import java.util.List;

public interface BreakFastService {

    BreakFastEntity save(BreakFastEntity breakFastEntity);
    List<BreakFastEntity> findAll();
}
