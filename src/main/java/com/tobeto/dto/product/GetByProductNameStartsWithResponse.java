package com.tobeto.dto.product;

import lombok.Data;

@Data
public class GetByProductNameStartsWithResponse {

	private String productName;

	private String categoryName;

	private String supplierCompanyName;

	private int criticalCount;

	private int quantity;

	private int purchasePrice;

	private int unitPrice;

}
