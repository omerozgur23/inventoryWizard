package com.tobeto.dto.invoice;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllInvoiceResponse {

	private UUID id;

	private UUID orderId;

	private String companyName;

	private double totalAmount;

	private String waybillDate;

	private boolean status;
}
