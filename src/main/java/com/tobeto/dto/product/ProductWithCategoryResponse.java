package com.tobeto.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductWithCategoryResponse {

	private UUID id;

	private String productName;

	private String categoryName;
}
