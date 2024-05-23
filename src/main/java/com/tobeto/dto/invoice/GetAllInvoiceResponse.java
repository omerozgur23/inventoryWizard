package com.tobeto.dto.invoice;

import java.util.UUID;

import com.tobeto.entities.enums.Status;

import lombok.Data;

@Data
public class GetAllInvoiceResponse {

	private UUID id;

	private UUID orderId;

	private String companyName;

	private double totalAmount;

	private String waybillDate;

	private Status status;
}
