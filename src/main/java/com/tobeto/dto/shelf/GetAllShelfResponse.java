package com.tobeto.dto.shelf;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllShelfResponse {
	private UUID id;
	private int count;
	private int capacity;
	private String productName;
}
