package com.tobeto.dto.customer;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateCustomerRequest {

	private UUID id;

	private String contactName;

	private String email;

	private String contactPhone;

	private String address;
}
