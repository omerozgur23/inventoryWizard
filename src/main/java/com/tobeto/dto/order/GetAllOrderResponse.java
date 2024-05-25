package com.tobeto.dto.order;

import java.util.UUID;

import com.tobeto.entities.enums.Status;

import lombok.Data;

@Data
public class GetAllOrderResponse {

	private UUID id;

	private String customerCompanyName;

	private String employeeFirstName;

	private String employeeLastName;

	private String orderDate;

	private double orderPrice;

	private Status status;

	private boolean invoiceGenerated;
}
