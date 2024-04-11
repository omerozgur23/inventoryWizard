package com.tobeto.dto.supplier;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSupplierRequest {

	private UUID id;

	private String companyName;

	private String contactName;

	private String contactPhone;

	private String address;
}
