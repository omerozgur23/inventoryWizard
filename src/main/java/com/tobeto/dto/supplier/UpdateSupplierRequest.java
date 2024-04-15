package com.tobeto.dto.supplier;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateSupplierRequest {

	private UUID id;

	private String companyName;

	private String contactName;

	private String contactEmail;

	private String contactPhone;

	private String address;
}
