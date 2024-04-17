package com.tobeto.dto.category;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateCategoryRequest {

	private UUID id;

	private String categoryName;
}
