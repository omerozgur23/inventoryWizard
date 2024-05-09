package com.tobeto.dto.invoice;

import java.util.UUID;

import lombok.Data;

@Data
public class InvoiceCancellationRequest {

	private UUID invoiceId;

	private UUID orderId;
}
