package com.tobeto.dto.customer;

import lombok.Data;

@Data
public class CreateCustomerRequest {

	private String companyName;

	private String contactName;

	private String contactEmail;

	private String contactPhone;

	private String taxNumber;

	private String address;
}
