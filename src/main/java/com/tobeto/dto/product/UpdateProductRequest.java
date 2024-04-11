package com.tobeto.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateProductRequest {

	private UUID id;

	private String productName;

//	private UUID categoryId;
//
//	private UUID supplierId;

//	private int quantity;

	private double purchasePrice;

	private double unitPrice;
}
