package com.tobeto.dto.product;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductItemDTO {

	private UUID productId;

	private int count;
}
