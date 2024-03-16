package com.tobeto.dto.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSupplierRequest {

	private String companyName;

	private String contactName;

	private String contactPhone;

	private String taxNumber;

	private String address;
}
