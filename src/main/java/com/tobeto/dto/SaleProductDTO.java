package com.tobeto.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class SaleProductDTO {

	private UUID productId;

	private int count;
}
