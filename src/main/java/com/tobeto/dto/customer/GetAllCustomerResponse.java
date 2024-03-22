package com.tobeto.dto.customer;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllCustomerResponse {

	private UUID id;

	private String companyName;

	private String contactName;

	private String email;

	private String contactPhone;

	private String taxNumber;

	private String address;
}
