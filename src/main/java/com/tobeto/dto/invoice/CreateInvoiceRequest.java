package com.tobeto.dto.invoice;

import java.util.List;
import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dto.product.ProductItemDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInvoiceRequest {

	@NotNull(message = Messages.ORDER_ID_CANNOR_BE_NULL)
	private UUID orderId;

	private List<ProductItemDTO> productItems;
}
