package com.tobeto.dto.product;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductResponse {
	private UUID id;

	private String productName;

	private String categoryName;

	private String supplierCompanyName;

	private int quantity;

	private double purchasePrice;

	private double unitPrice;

}
