package com.tobeto.dto.product;

import java.util.List;
import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleProductRequest {

	private List<ProductItemDTO> productItems;

	@NotNull(message = Messages.CUSTOMER_ID_CANNOT_BE_NULL)
	private UUID customerId;

	@NotNull(message = Messages.USER_ID_CANNOT_BE_NULL)
	private UUID userId;

}
