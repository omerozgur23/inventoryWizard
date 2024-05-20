package com.tobeto.dto.product;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProductRequest {

	@NotNull(message = Messages.PRODUCT_NAME_CANNOT_BE_NULL)
	@NotBlank(message = Messages.PRODUCT_NAME_CANNOT_BE_BLANK)
	private String productName;

	@NotNull(message = Messages.CATEGORY_ID_CANNOT_BE_NULL)
	private UUID categoryId;

	@NotNull(message = Messages.SUPPLIER_ID_CANNOT_BE_NULL)
	private UUID supplierId;

	@Positive(message = Messages.CRITICAL_COUNT_MUST_BE_POSITIVE)
	private int criticalCount;

	@Positive(message = Messages.PURCHASE_PRICE_MUST_BE_POSITIVE)
	private double purchasePrice;

	@Positive(message = Messages.UNIT_PRICE_MUST_BE_POSITIVE)
	private double unitPrice;
}
