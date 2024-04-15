package com.tobeto.dto.category;

import java.util.List;
import java.util.UUID;

import com.tobeto.dto.product.GetAllProductResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCategoryResponse {

	private UUID id;

	private String name;

	private List<GetAllProductResponse> products;

}
