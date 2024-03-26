package com.tobeto.dto.shelf;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateShelfRequest {

	private UUID id;

	private int capacity;
}
