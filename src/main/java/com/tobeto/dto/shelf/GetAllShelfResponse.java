package com.tobeto.dto.shelf;

import java.util.UUID;

import lombok.Data;

@Data
public class GetAllShelfResponse {

	private UUID id;

	private int count;

	private int capacity;

	private String productName;
}
