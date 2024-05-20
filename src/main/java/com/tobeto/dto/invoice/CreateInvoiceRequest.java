package com.tobeto.dto.invoice;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInvoiceRequest {

	@NotNull(message = Messages.ORDER_ID_CANNOR_BE_NULL)
	private UUID orderId;
}
