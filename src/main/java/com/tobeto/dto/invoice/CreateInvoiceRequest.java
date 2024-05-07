package com.tobeto.dto.invoice;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateInvoiceRequest {

	private UUID orderId;
}
