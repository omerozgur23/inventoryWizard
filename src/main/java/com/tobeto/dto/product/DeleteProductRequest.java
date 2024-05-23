package com.tobeto.dto.product;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteProductRequest {

	@NotNull(message = Messages.PRODUCT_ID_CANNOT_BE_NULL)
	private UUID id;
}
