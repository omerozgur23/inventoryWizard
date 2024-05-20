package com.tobeto.dto.shelf;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateShelfRequest {

	@NotNull(message = Messages.SHELF_ID_CANNOT_BE_NULL)
	private UUID id;

	@Positive(message = Messages.CAPACITY_MUST_BE_POSITIVE)
	@Max(value = 10, message = Messages.CAPACITY_MUST_BE_LESS_THAN_OR_EQUAL_TO_10)
	private int capacity;
}
