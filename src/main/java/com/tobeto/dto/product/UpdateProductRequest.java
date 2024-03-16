package com.tobeto.dto.product;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

	private UUID id;

	private String productName;

	private UUID categoryId;

	private UUID supplierId;

	private int quantity;

	private double purchasePrice;

	private double unitPrice;
}
