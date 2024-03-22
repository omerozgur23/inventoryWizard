package com.tobeto.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllProductResponse {

	private UUID id;

	private String productName;

	private String categoryName;

	private String supplierCompanyName;

	private int quantity;

	private double purchasePrice;

	private double unitPrice;
}
