package com.tobeto.dto.product;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AcceptProductRequest {

	@NotNull(message = Messages.PRODUCT_ID_CANNOT_BE_NULL)
	private UUID productId;

	@Positive(message = Messages.COUNT_MUST_BE_POSITIVE)
	private int count;
}
