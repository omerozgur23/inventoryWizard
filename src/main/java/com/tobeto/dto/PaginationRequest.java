package com.tobeto.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

	@Min(value = 1, message = "Page number must be greater than or equal to 1")
	private int pageNo = 1;

	@Min(value = 1, message = "Page size must be greater than or equal to 1")
	private int pageSize = 15;
}
