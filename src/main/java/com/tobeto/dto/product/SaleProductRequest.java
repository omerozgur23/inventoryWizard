package com.tobeto.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class SaleProductRequest {

	private UUID productId;

	private int count;

	private UUID customerId;

	private UUID userId;

}
