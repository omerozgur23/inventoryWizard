package com.tobeto.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class SaleRequest {

	private List<SaleProductDTO> productItems;

	private UUID customerId;

	private UUID userId;
}
