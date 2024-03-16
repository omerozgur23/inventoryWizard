package com.tobeto.dto.product;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
	private String productName;
	private UUID categoryId;
	private UUID supplierId;
	private int quantity;
	private double unitPrice;
	private double purchasePrice;

}
