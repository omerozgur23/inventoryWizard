package com.tobeto.dto.order;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllOrderResponse {

	private UUID id;

	private String customerCompanyName;

	private String employeeFirstName;

	private String orderDate;

	private double orderPrice;
}
