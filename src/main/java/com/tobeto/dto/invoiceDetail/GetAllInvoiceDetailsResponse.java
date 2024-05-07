package com.tobeto.dto.invoiceDetail;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllInvoiceDetailsResponse {

	private UUID id;

	private UUID invoiceId;

	private String productName;

	private int quantity;

	private double unitPrice;

	private double totalPrice;
}
