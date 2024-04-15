package com.tobeto.dto.supplier;

import java.util.List;
import java.util.UUID;

import com.tobeto.dto.product.GetAllProductResponse;

import lombok.Data;

@Data
public class GetAllSupplierResponse {
	private UUID id;

	private String companyName;

	private String contactName;

	private String contactEmail;

	private String contactPhone;

	private String taxNumber;

	private String address;

	private List<GetAllProductResponse> products;
}
