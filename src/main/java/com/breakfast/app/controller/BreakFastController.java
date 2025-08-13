package com.breakfast.app.controller;

import com.breakfast.app.service.BreakFastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.breakfast.app.dto.BreakFastDTO;
import com.breakfast.app.entity.BreakFastEntity;
import com.breakfast.app.exception.BreakFastException;

@RestController
public class BreakFastController {

	private final BreakFastService breakFastService;

    public BreakFastController(BreakFastService breakFastService) {
        this.breakFastService = breakFastService;
    }

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody BreakFastDTO dto) {

		BreakFastEntity entity = new BreakFastEntity();
		entity.setId(dto.getId());
		entity.setEmployeeName(dto.getEmployeeName());
		entity.setSocialSecurityNumber(dto.getSocialSecurityNumber());
		entity.setItems(dto.getItems());

		try {
			breakFastService.save(entity);
		} catch (BreakFastException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(entity);
	}


	@GetMapping("/list")
	public ResponseEntity<?> list() {

		try {
			return breakFastService.listAll();
		} catch (BreakFastException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}

}
