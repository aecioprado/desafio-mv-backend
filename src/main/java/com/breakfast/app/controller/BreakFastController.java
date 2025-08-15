package com.breakfast.app.controller;

import com.breakfast.app.entity.BreakFastItemEntity;
import com.breakfast.app.service.BreakFastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breakfast.app.dto.BreakFastDTO;
import com.breakfast.app.entity.BreakFastEntity;
import com.breakfast.app.exception.BreakFastException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/breakfast")
public class BreakFastController {

	private final BreakFastService breakFastService;

    public BreakFastController(BreakFastService breakFastService) {
        this.breakFastService = breakFastService;
    }

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody BreakFastDTO dto) {

		try {
			// Convert DTO to Entity
			BreakFastEntity entity = convertDtoToEntity(dto);

			// Save entity
			BreakFastEntity savedEntity = breakFastService.save(entity);

			// Convert back to DTO for response
			BreakFastDTO responseDto = convertEntityToDto(savedEntity);

			return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

		} catch (BreakFastException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("BREAKFAST_ERROR", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"));
		}
	}


	/*@GetMapping("/list")
	public ResponseEntity<?> list() {

		try {
			return breakFastService.listAll();
		} catch (BreakFastException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}
*/


	private BreakFastEntity convertDtoToEntity(BreakFastDTO dto) {

		// Parent Object
		BreakFastEntity entity = new BreakFastEntity();

		// Don't set ID for new entities (let the database generate it)
		if (dto.getId() != null && dto.getId() > 0) {
			entity.setId(dto.getId());
		}

		entity.setEmployeeName(dto.getEmployeeName());
		entity.setSocialSecurityNumber(dto.getSocialSecurityNumber()); // Note: DTO has socialSecurityNumber, Entity has ssn

		// Convert List<BreakFastItemEntity> directly - no conversion needed
		if (dto.getItems() != null && !dto.getItems().isEmpty()) {
			for (BreakFastItemEntity itemEntity : dto.getItems()) {
				if (itemEntity != null && itemEntity.getName() != null && !itemEntity.getName().trim().isEmpty()) {
					// Create a new instance to avoid potential persistence issues
					// Child Object
					BreakFastItemEntity newItemEntity = new BreakFastItemEntity();
					newItemEntity.setId(itemEntity.getId());
					newItemEntity.setName(itemEntity.getName().trim());
					entity.addItem(newItemEntity); // Cria relacionamento do obj filho com o obj pai
				}
			}
		}

		return entity;
	}

	/**
	 * Convert BreakFastEntity to BreakFastDTO
	 */
	private BreakFastDTO convertEntityToDto(BreakFastEntity entity) {
		// Convert List<BreakFastItemEntity> directly - no conversion needed
		List<BreakFastItemEntity> items = new ArrayList<>();
		if (entity.getItems() != null) {
			items = entity.getItems().stream()
					.map(item -> {
						BreakFastItemEntity dtoItem = new BreakFastItemEntity();
						dtoItem.setId(item.getId());
						dtoItem.setName(item.getName());
						// Copy other properties if BreakFastItemEntity has more fields
						return dtoItem;
					})
					.toList();
		}

		return new BreakFastDTO(
				entity.getId(),
				entity.getEmployeeName(),
				entity.getSocialSecurityNumber(), // Note: Entity has ssn, DTO expects socialSecurityNumber
				items
		);
	}
}
