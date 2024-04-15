package com.tobeto.dto.supplier;

import lombok.Data;

@Data
public class CreateSupplierRequest {

	private String companyName;

	private String contactName;

	private String contactEmail;

	private String contactPhone;

	private String taxNumber;

	private String address;
}
