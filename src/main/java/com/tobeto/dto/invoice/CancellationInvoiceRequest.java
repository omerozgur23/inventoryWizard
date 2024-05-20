package com.tobeto.dto.invoice;

import java.util.UUID;

import com.tobeto.core.utilities.exceptions.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancellationInvoiceRequest {

	@NotNull(message = Messages.INVOICE_ID_CANNOR_BE_NULL)
	private UUID invoiceId;
}
