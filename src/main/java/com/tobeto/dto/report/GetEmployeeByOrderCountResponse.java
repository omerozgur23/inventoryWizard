package com.tobeto.dto.report;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetEmployeeByOrderCountResponse {

	private UUID id;

	private String firstName;

	private String lastName;

	private long count;
}
