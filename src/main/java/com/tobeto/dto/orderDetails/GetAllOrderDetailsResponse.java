package com.tobeto.dto.orderDetails;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllOrderDetailsResponse {

	private UUID id;

	private UUID orderId;

	private String productName;

	private UUID productId;

	private int quantity;

	private double unitPrice;

	private double totalPrice;
}
