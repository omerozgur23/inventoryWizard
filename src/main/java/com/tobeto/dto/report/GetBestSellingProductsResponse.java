package com.tobeto.dto.report;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBestSellingProductsResponse {

	private UUID id;

	private String productName;

	private int quantity;

	private double purchasePrice;

	private double unitPrice;

	private Long count;
}
