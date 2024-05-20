package com.tobeto.dto.shelf;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateShelfRequest {

	@Positive(message = Messages.CAPACITY_MUST_BE_POSITIVE)
	@Max(value = 10, message = Messages.CAPACITY_MUST_BE_LESS_THAN_OR_EQUAL_TO_10)
	private int capacity;

	@Positive(message = Messages.COUNT_MUST_BE_POSITIVE)
	@Max(value = 10, message = Messages.MAX_COUNT_AT_ONE_TIME)
	private int count;
}
