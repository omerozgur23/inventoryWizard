package com.tobeto.dto.report;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCustomerByOrderCountResponse {

	private UUID id;

	private String companyName;

	private Long count;
}
