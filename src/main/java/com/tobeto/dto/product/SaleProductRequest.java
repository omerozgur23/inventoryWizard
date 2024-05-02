package com.tobeto.dto.product;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class SaleProductRequest {

	private List<ProductItemDTO> productItems;

	private UUID customerId;

	private UUID userId;

}
