package com.breakfast.app.service.impl;

import com.breakfast.app.repository.BreakFastItemRepository;
import com.breakfast.app.service.BreakFastItemService;
import org.springframework.stereotype.Service;


@Service
public class BreakFastItemServiceImpl implements BreakFastItemService {


    private BreakFastItemRepository itemRepository;

    public BreakFastItemServiceImpl(BreakFastItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean existsByName(String name) {
        return itemRepository.existsByName(name);
    }
}
